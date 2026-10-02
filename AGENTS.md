# Repository and publishing guide

## Project layout

- `library/`: Android/Gradle project containing the Panels core and plugin modules.
- `library/<Module>/src/main/java/`: Kotlin/Java backend code.
- `library/<Module>/web/`: Svelte frontend, plugin config, build scripts, and Bun lockfile.
- `library/plugin-svelte-assets/`: Included Gradle build that generates and packages frontend assets.
- `library/FullPanels/`: Aggregate artifact exposing the core and plugins through `api(project(...))` dependencies.
- `docs/`: Documentation site and generated plugin data.
- `examples/`: Example applications and OpModes.

Related repositories are normally siblings of this checkout:

- `../ftcontrol-maven`: Published Maven artifacts under `releases/com/bylazar/`.
- `../Panels-Quickstart-Java`: Java quickstart; FullPanels dependency in `build.dependencies.gradle`.
- `../Panels-Quickstart-Kotlin`: Kotlin quickstart; FullPanels dependency in `build.dependencies.gradle`.

Inspect each repository's working tree before changing it. Preserve unrelated changes and check for repository-specific instructions.

## Coding patterns

- Follow the surrounding Kotlin, Gradle Kotlin DSL, TypeScript, and Svelte conventions.
- Plugin IDs/namespaces use `com.bylazar.<plugin>`; Maven coordinates use `com.bylazar:<artifact>:<version>`.
- Backend plugins expose a Kotlin singleton named `Plugin` in their namespace.
- Frontend `web/config.ts` declares the plugin ID, version, core compatibility version, manager, components, templates, and changelog.
- Register new Gradle modules in `library/settings.gradle.kts`. If included in FullPanels, update both its Gradle dependencies and `web/config.ts` inclusion list.
- Prefer targeted changes and targeted build/publish tasks over rebuilding or republishing every module.

## Versions and compatibility

For a plugin release, update these together:

1. `library/<Module>/build.gradle.kts`: `pluginVersion`.
2. `library/<Module>/web/config.ts`: `version` and a new changelog entry with the release date, changes, and upgrade instructions when relevant.
3. `library/<Module>/web/package.json` and `bun.lock` if frontend dependencies change.

When an included plugin changes, also bump FullPanels' Gradle and frontend versions and describe the dependency update in its changelog.

**The frontend compatibility version must match the core actually shipped to users.** `PluginsManager` compares each plugin's `pluginsCoreVersion` with `GlobalStats.pluginsCoreVersion` and skips incompatible plugins. Check the published core artifact as well as the source tree: they can differ if source changes were never released.

- Build the plugin with the corresponding `ftc-panels` package version. Pin it when a patch must remain compatible with an existing published core.
- Do not merely change the compatibility string while building against a different frontend core.
- A frontend core upgrade requires coordinated core/plugin releases and verification of the resulting dependency graph.
- Check generated configs after frontend builds; frontend tooling may update version declarations.
- Use new artifact versions for fixes. Avoid overwriting an existing released version.

## Frontend asset packaging

Panels discovers plugins by listing Android assets under `web/plugins`. Kotlin classes alone do not make a plugin discoverable.

Every plugin AAR must contain nonempty:

```text
assets/web/plugins/<plugin-id>/config.json
assets/web/plugins/<plugin-id>/svelte.js
```

`svelte.js` is gzip-compressed despite its filename. Validate it as gzip when inspecting its contents.

The core dashboard has its own site assets under `assets/web/`, including `index.html`.

Preserve the dependency chain in `SvelteAssetsPlugin`:

```text
clear outputs -> install dependencies -> build frontend -> verify outputs
              -> copy assets -> Android package/merge assets -> AAR/APK
```

- Both library `package<Variant>Assets` and application `merge<Variant>Assets` tasks must wait for asset generation.
- Depending only on a root `publish` task is insufficient: module publishing and ordinary assembly must also generate assets before packaging.
- Validation must run even when the build output directory is absent; a `Copy` task can otherwise skip as `NO-SOURCE`.
- Remove stale generated outputs before building so previous files cannot conceal a failed or incomplete frontend build.
- Build/install failures must fail Gradle. Logs are under `library/<Module>/build/svelte-logs/<Module>/`.

## Local build environment

Use Bun, a suitable JDK for the project's Android Gradle Plugin, and an installed Android SDK. Check the Gradle wrapper and SDK configuration before changing tool versions.

On this Windows development machine, Android Studio's bundled JDK can be selected for the current PowerShell session:

```powershell
$env:JAVA_HOME = 'C:\Program Files\Android\Android Studio\jbr'
$env:Path = "$env:JAVA_HOME\bin;$env:Path"
```

Run Gradle commands from `library/`. Use `./gradlew` on Unix or `./gradlew.bat` on Windows. When using a command tool, set its working directory rather than changing directories inside the command.

Example targeted release build for Field and FullPanels:

```powershell
.\gradlew.bat :Field:clean :FullPanels:clean :Field:assembleRelease :FullPanels:assembleRelease --console=plain
```

## Documentation

- `docs/src/lib/data.ts` and `docs/src/lib/simpleData.ts` are generated data; regenerate them rather than manually editing serialized configs or bundles.
- `docs/prepare.ts` builds plugin documentation data from the library. Run `bun install --frozen-lockfile`, `bun run ./prepare.ts`, then `bun run build` from `docs/` for the full workflow.
- Review regenerated changes for unintended dependency/version changes, especially compatibility declarations.
- For a FullPanels release, update the displayed version in `docs/src/lib/ui/sections/HeroCopy.svelte`.
- `.github/workflows/pages.yml` deploys documentation on pushes to `main`. Its Docker build runs documentation preparation and the site build.

## Release verification

Before publishing:

1. Build affected release modules from clean module outputs.
2. Inspect the resulting AARs under `library/<Module>/build/outputs/aar/` for plugin configs and frontend bundles.
3. Check packaged IDs, versions, component definitions, and core compatibility versions.
4. Check bundled resources. Field's built-in PNGs are Java resources inside the AAR's `classes.jar`; verify new images and retained presets.
5. Inspect generated POM and Gradle module metadata under `build/publications/release/`. FullPanels must resolve to the new plugin version and the intended core/dependency versions.
6. Build documentation if documentation/config data changed.
7. Verify relevant quickstarts against the release and inspect the final APK assets. For runtime behavior, smoke-test on a phone/emulator when available.

For packaging changes, also verify the failure case: a frontend command that exits successfully without producing required outputs must fail before Android packaging.

## Publishing and commit sequence

Use this order for a release spanning these repositories:

1. Implement and verify the source/version/documentation changes in `ftcontrol-panels`.
2. Commit the source changes when requested.
3. Publish only the affected modules to the local Maven repository.
4. Verify and commit the generated artifacts and metadata in `ftcontrol-maven`.
5. Update both quickstarts' FullPanels dependency to the released version, build/verify them, and commit when requested.
6. Push the requested repositories to their correct remotes/branches.

The `localDevRepo` URL is configured in module Gradle files and currently points to the sibling Maven checkout through an absolute Windows path. Verify it before publishing, particularly on another machine.

Example targeted publication, run from `library/`:

```powershell
.\gradlew.bat :Field:publishReleasePublicationToLocalDevRepoRepository :FullPanels:publishReleasePublicationToLocalDevRepoRepository --console=plain
```

Publication generates AARs, POMs, Gradle `.module` files, Maven metadata, and checksum sidecars. Include the complete new version directories and changed artifact metadata/checksums in the Maven commit. Confirm that published AARs match the verified build outputs and preserve artifact bytes so checksums remain valid.

To test quickstarts before the Maven commit is pushed/deployed, use a temporary Gradle init script that adds the local `ftcontrol-maven/releases` repository for the affected coordinates. Do not commit machine-specific repository overrides to the quickstarts. Build `:TeamCode:assembleDebug` in each quickstart.

Use Conventional Commits, for example:

```text
fix(field): restore packaged widget assets
fix(release): publish Field <version> and FullPanels <version>
fix(deps): update FullPanels to <version>
```

Before committing, inspect status, diff, and recent history; stage only intended files. Commit and push only when requested. Verify current branch and upstream before pushing: this repository and Maven normally use `origin/main`, while both quickstarts normally use `origin/master`. Quickstart `upstream` remotes point to other source repositories; release updates go to `origin`.

Report commit hashes, released versions, verification performed, and whether changes are only committed locally or also pushed. Distinguish local Maven publication from availability on the hosted Maven server.

## Browser smoke testing over ADB

Panels serves HTTP on port `8001` and WebSocket updates on `8002`. Forward both ports to the device running the Robot Controller app:

```powershell
$adb = "$env:LOCALAPPDATA\Android\Sdk\platform-tools\adb.exe"
& $adb devices
& $adb -s DEVICE_SERIAL forward tcp:8001 tcp:8001
& $adb -s DEVICE_SERIAL forward tcp:8002 tcp:8002
& $adb forward --list
```

Open `http://localhost:8001`, force-refresh if necessary, and verify that the affected widget is selectable and receives live data. For Field, check the default background and expected drawing behavior. Select the actual connected device serial rather than assuming a phone or emulator.
