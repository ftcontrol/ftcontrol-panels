import type { PluginConfig } from "ftc-panels"

export const config: PluginConfig = {
  id: "com.bylazar.lights",
  name: "Lights",
  letterName: "L",
  description: "Gobilda Light-Based Telemetry",
  websiteURL: "https://panels.bylazar.com/docs/com.bylazar.lights/",
  mavenURL: "https://mymaven.bylazar.com/releases",
  packageString: "com.bylazar:lights:<VERSION>",
  version: "1.0.0",
  pluginsCoreVersion: "1.1.38",
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
  changelog: [
    {
      version: "1.0.0",
      release_date: "26.08.2025",
      changes: [
        {
          type: "other",
          description: "First release",
          upgrading: "",
        },
      ],
    },
  ],
}
