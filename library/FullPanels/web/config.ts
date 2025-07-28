import { PluginConfig } from "ftc-panels"

export const config: PluginConfig = {
  id: "com.bylazar.fullpanels",
  name: "Full Panels",
  letterName: "FP",
  description: "Full Panels Installation",
  websiteURL: "",
  devURL: "",
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
      name: "Overview",
      filepath: "src/docs/Overview.svelte",
    },
    chapters: [],
  },
}
