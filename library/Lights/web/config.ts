import type { PluginConfig } from "ftc-panels"

export const config: PluginConfig = {
  id: "com.bylazar.lights",
  name: "Lights",
  letterName: "L",
  description: "Gobilda Light-Based Telemetry",
  websiteURL: "https://panels.bylazar.com/docs/com.bylazar.lights/",
  version: "0.0.1",
  pluginsCoreVersion: "1.1.33",
  author: "Lazar",
  manager: "src/manager.ts",
  components: [
    {
      type: "widget",
      id: "Lights",
      filepath: "src/widgets/Lights.svelte",
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
