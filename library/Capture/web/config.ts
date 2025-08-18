import type { PluginConfig } from "ftc-panels"

export const config: PluginConfig = {
  id: "com.bylazar.capture",
  name: "Capture",
  letterName: "C",
  description: "Capture Plugin for Panels",
  websiteURL: "https://panels.bylazar.com/docs/com.bylazar.capture/",
  version: "0.0.9",
  pluginsCoreVersion: "1.1.25",
  author: "Lazar",
  manager: "src/manager.ts",
  components: [
    {
      type: "widget",
      id: "Capture",
      filepath: "src/widgets/Capture.svelte",
    },
    {
      type: "docs",
      id: "Homepage",
      filepath: "src/docs/Homepage.svelte",
    },
  ],
  templates: [],
  includedPluginsIDs: [],
}
