import path from "path"
import fs from "fs"
import { buildAllPlugins } from "ftc-panels/cli"
import {
    type PluginConfig,
} from "ftc-panels"

const libraryPath = path.resolve(process.cwd(), "../library")
const rawModules: { config: PluginConfig; name: string }[] = await buildAllPlugins(libraryPath, false)

const modules = rawModules.map(it => it.config)

const processedModules = modules.map((it)=>{
    const m = it
    m.widgets = m.widgets.map((it)=>{
        const w = it
        it.textContent = ""
        return w
    })
    m.navlets = m.navlets.map((it)=>{
        const n = it
        it.textContent = ""
        return n
    })
    m.manager.textContent = ""
    return m
})

const outFile = path.resolve("./src/lib/data.ts")

const fileContents = `// Auto-generated file — do not edit directly
import { type PluginConfig } from "ftc-panels"

export const modules: PluginConfig[] = ${JSON.stringify(processedModules, null, 2)} as const
`

fs.mkdirSync(path.dirname(outFile), { recursive: true })

fs.writeFileSync(outFile, fileContents, "utf8")

console.log(`Wrote ${processedModules.length} modules to ${outFile}`)