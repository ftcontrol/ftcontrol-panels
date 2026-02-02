package com.bylazar

import com.android.build.api.variant.LibraryAndroidComponentsExtension
import com.android.build.gradle.internal.tasks.factory.dependsOn
import com.bylazar.tasks.BunTask
import com.bylazar.tasks.InstallBunLocally
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.Copy
import org.gradle.api.tasks.Delete
import org.gradle.kotlin.dsl.register
import java.io.File

@Suppress("LocalVariableName", "unused")
class SvelteAssetsPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        val extension =
            project.extensions.create("svelteAssets", SvelteAssetsPluginExtension::class.java)

        val BUN_INSTALL = project.rootProject.file(".bun")

        val generatedAssetsDir = File(
            project.layout.buildDirectory.get().asFile,
            "/generated/sources/frontend/assets",
        )

        val installBunLocally = project.tasks.register<InstallBunLocally>("installBunLocally")

        project.tasks.register("uninstallBun", Delete::class.java) {
            group = "frontend"
            description = "Removes the project's local bun installation."
            delete(BUN_INSTALL)
        }

        val clearFrontendAssets =
            project.tasks.register("clearFrontendAssets", Delete::class.java) {
                group = "frontend"
                description = "Clears frontend assets."

                delete(generatedAssetsDir)
            }

        val bunBuild = project.tasks.register<BunTask>("bunBuild") {
            val webDir = project.file(extension.webAppPath)

            group = "frontend"
            description = "Installs frontend dependencies, then builds the frontend."

            dependsOn(installBunLocally)

            val inputFiles = project.fileTree(webDir) {
                exclude(extension.buildDirPath)
                exclude("node_modules")
                exclude(".panels")
                exclude(".svelte-kit")
            }

            inputs.dir(inputFiles)

            outputs.dir(File(webDir, extension.buildDirPath))
            outputs.dir(File(webDir, "node_modules"))
            outputs.dir(File(webDir, ".panels"))
            outputs.dir(File(webDir, ".svelte-kit"))

            doLast {
                bunExec("bun i && bun run build") {
                    workingDir = webDir
                }
            }
        }

        val copyFrontendToAssets =
            project.tasks.register("copyFrontendToAssets", Copy::class.java) {
                val assetsPath = requireNotNull(extension.assetsPath) { "assets path must be set" }
                val outputDir = File(
                    generatedAssetsDir,
                    assetsPath,
                )
                val webDir = project.file(extension.webAppPath)

                group = "frontend"
                description =
                    "Copies the built frontend assets into ${outputDir.relativeTo(project.projectDir)}."

                dependsOn(clearFrontendAssets)
                dependsOn(bunBuild)

                from(File(webDir, extension.buildDirPath))
                into(outputDir)
            }

        val buildFrontend = project.tasks.register("buildFrontend") {
            group = "frontend"
            description = "Builds frontend assets for bundling into the library."

            dependsOn(copyFrontendToAssets)

            outputs.dir(generatedAssetsDir)
        }

        project.extensions.getByType(LibraryAndroidComponentsExtension::class.java).finalizeDsl {
            it.sourceSets.getByName("main") {
                assets.srcDir(generatedAssetsDir)
            }
        }

        project.tasks.named("preBuild").dependsOn(buildFrontend)
    }
}
