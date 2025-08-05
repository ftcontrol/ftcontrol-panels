import path from "path"
import { buildAllPlugins } from "ftc-panels/cli"
import {
    type PluginConfig,
} from "ftc-panels"

const libraryPath = path.resolve(process.cwd(), "../library")
const rawModules: { config: PluginConfig; name: string }[] = await buildAllPlugins(libraryPath, false)

export const modules = rawModules.map(it => it.config)