import type { PluginConfig } from "ftc-panels"

export const config: PluginConfig = {
  id: "com.bylazar.telemetry",
  name: "Telemetry",
  letterName: "T",
  description: "Text-Based Telemetry",
  websiteURL: "https://panels.bylazar.com/docs/com.bylazar.telemetry/",
  mavenURL: "https://mymaven.bylazar.com/dev",
  packageString: "com.bylazar:telemetry:<VERSION>",
  version: "0.0.14",
  pluginsCoreVersion: "1.1.34",
  author: "Lazar",
  manager: "src/manager.ts",
  components: [
    {
      type: "widget",
      id: "Telemetry",
      filepath: "src/widgets/Telemetry.svelte",
    },
    {
      type: "docs",
      id: "Homepage",
      filepath: "src/docs/Homepage.svelte",
    },
    {
      type: "docs",
      id: "DocsPage1",
      filepath: "src/docs/DocsPage1.svelte",
    },
  ],
  templates: [],
  includedPluginsIDs: [],
  changelog: [],
}
