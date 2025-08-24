import type { PluginConfig } from "ftc-panels"

export const config: PluginConfig = {
  id: "com.bylazar.battery",
  name: "Battery",
  letterName: "B",
  description: "Battery Utils for Panels",
  websiteURL: "https://panels.bylazar.com/docs/com.bylazar.battery/",
  mavenURL: "https://mymaven.bylazar.com/dev",
  packageString: "com.bylazar:battery:<VERSION>",
  version: "0.0.16",
  pluginsCoreVersion: "1.1.35",
  author: "Lazar",
  manager: "src/manager.ts",
  components: [
    {
      type: "navlet",
      id: "Battery",
      filepath: "src/navlets/Battery.svelte",
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
