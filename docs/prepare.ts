import path from "path"
import fs from "fs"
import { fileURLToPath } from "url"
import { buildAllPlugins } from "ftc-panels/cli"
import { type PluginConfig } from "ftc-panels"
import zlib from "zlib"
import { panelsConfig } from "../library/Panels/web/src/lib/manager"
import { pluginsCoreConfig } from "ftc-panels"

const __filename = fileURLToPath(import.meta.url)
const __dirname = path.dirname(__filename)

const libraryPath = path.resolve(__dirname, "../library")
const rawModules: { config: PluginConfig; name: string; svelte: string }[] =
  await buildAllPlugins(libraryPath, false, null)

function gunzipToUtf8(buf: Buffer): string {
  return zlib.gunzipSync(buf).toString("utf8")
}

const modulesWithDecompressed = rawModules
  .filter((it) => it.config.id != "com.bylazar.exampleplugin")
  .map((m) => ({
    config: m.config,
    svelte: gunzipToUtf8(m.svelte),
  }))

modulesWithDecompressed.push({
  config: panelsConfig,
  svelte: "",
})

modulesWithDecompressed.push({
  config: pluginsCoreConfig,
  svelte: "",
})

const simpleModulesWithDecompressed = modulesWithDecompressed.map(
  (it) => it.config
)

const simpleOutFile = path.resolve("./src/lib/simpleData.ts")
const outFile = path.resolve("./src/lib/data.ts")

const fileContents = `// Auto-generated file — do not edit directly
import { type PluginConfig } from "ftc-panels"

export const modules: {config: PluginConfig, svelte: string}[] = ${JSON.stringify(
  modulesWithDecompressed,
  null,
  2
)} as const
`

const simpleFileContents = `// Auto-generated file — do not edit directly
import { type PluginConfig } from "ftc-panels"

export const simpleModules: PluginConfig[] = ${JSON.stringify(
  simpleModulesWithDecompressed,
  null,
  2
)} as const
`

fs.mkdirSync(path.dirname(outFile), { recursive: true })

fs.writeFileSync(outFile, fileContents, "utf8")
fs.writeFileSync(simpleOutFile, simpleFileContents, "utf8")

console.log(`Wrote ${modulesWithDecompressed.length} modules to ${outFile}`)
