import type { PluginConfig } from "ftc-panels"

export const config: PluginConfig = {
  id: "com.bylazar.capture",
  name: "Capture",
  letterName: "C",
  description: "Capture Plugin for Panels",
  websiteURL: "https://panels.bylazar.com/docs/com.bylazar.capture/",
  version: "0.0.9",
  pluginsCoreVersion: "1.1.23",
  author: "Lazar",
  widgets: [
    {
      name: "Capture",
      filepath: "src/widgets/Capture.svelte",
    },
  ],
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
  includedPluginsIDs: [],
}
