import type { PluginConfig } from "ftc-panels"

export const config: PluginConfig = {
  id: "com.bylazar.pinger",
  name: "Pinger",
  letterName: "P",
  description: "Latency Test for Panels",
  websiteURL: "https://panels.bylazar.com/docs/com.bylazar.pinger/",
  mavenURL: "https://mymaven.bylazar.com/dev",
  packageString: "com.bylazar:pinger:<VERSION>",
  version: "0.0.11",
  pluginsCoreVersion: "1.1.34",
  author: "Lazar",
  manager: "src/manager.ts",
  components: [
    {
      type: "navlet",
      id: "Ping",
      filepath: "src/navlets/Ping.svelte",
    },
    {
      type: "docs",
      id: "Overview",
      filepath: "src/docs/Overview.svelte",
    },
  ],
  templates: [],
  includedPluginsIDs: [],
  changelog: [],
}
