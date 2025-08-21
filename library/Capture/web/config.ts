import type { PluginConfig } from "ftc-panels"

export const config: PluginConfig = {
  id: "com.bylazar.capture",
  name: "Capture",
  letterName: "C",
  description: "Capture Plugin for Panels",
  websiteURL: "https://panels.bylazar.com/docs/com.bylazar.capture/",
  mavenURL: "https://mymaven.bylazar.com/dev",
  packageString: "com.bylazar:capture:<VERSION>",
  version: "0.0.15",
  pluginsCoreVersion: "1.1.34",
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
  changelog: [],
}
