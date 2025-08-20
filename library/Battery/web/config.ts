import type { PluginConfig } from "ftc-panels"

export const config: PluginConfig = {
  id: "com.bylazar.battery",
  name: "Battery",
  letterName: "B",
  description: "Battery Utils for Panels",
  websiteURL: "https://panels.bylazar.com/docs/com.bylazar.battery/",
  version: "0.0.14",
  pluginsCoreVersion: "1.1.29",
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
}
