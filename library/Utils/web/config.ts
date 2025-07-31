import { PluginConfig } from "ftc-panels"

export const config: PluginConfig = {
  id: "com.bylazar.utils",
  name: "Utils",
  letterName: "U",
  description: "Utils for Panels",
  websiteURL: "",
  version: "0.0.3",
  pluginsCoreVersion: "0.0.1",
  author: "Lazar",
  widgets: [],
  navlets: [],
  manager: {
    name: "Manager",
    filepath: "src/manager.ts",
  },
  docs: {
    homepage: {
      name: "Homepage",
      filepath: "src/docs/Homepage.svelte",
    },
    chapters: [],
  },
  templates: [],
}
