import type { PluginConfig } from "ftc-panels"

export const config: PluginConfig = {
  id: "com.bylazar.lights",
  name: "Lights",
  letterName: "L",
  description: "Gobilda Light-Based Telemetry",
  websiteURL: "https://panels.bylazar.com/docs/com.bylazar.lights/",
  mavenURL: "https://mymaven.bylazar.com/dev",
  packageString: "com.bylazar:lights:<VERSION>",
  version: "0.0.2",
  pluginsCoreVersion: "1.1.34",
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
  changelog: [],
}
