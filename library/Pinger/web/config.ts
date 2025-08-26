import type { PluginConfig } from "ftc-panels"

export const config: PluginConfig = {
  id: "com.bylazar.pinger",
  name: "Pinger",
  letterName: "P",
  description: "Latency Test for Panels",
  websiteURL: "https://panels.bylazar.com/docs/com.bylazar.pinger/",
  mavenURL: "https://mymaven.bylazar.com/releases",
  packageString: "com.bylazar:pinger:<VERSION>",
  version: "1.0.0",
  pluginsCoreVersion: "1.1.38",
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
