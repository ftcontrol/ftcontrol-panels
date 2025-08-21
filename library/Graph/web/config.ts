import type { PluginConfig } from "ftc-panels"

export const config: PluginConfig = {
  id: "com.bylazar.graph",
  name: "Graph",
  letterName: "G",
  description: "Graphs for Panels",
  websiteURL: "https://panels.bylazar.com/docs/com.bylazar.graph",
  mavenURL: "https://mymaven.bylazar.com/dev",
  packageString: "com.bylazar:graph:<VERSION>",
  version: "0.0.3",
  pluginsCoreVersion: "1.1.34",
  author: "Lazar",
  manager: "src/manager.ts",
  components: [
    {
      type: "widget",
      id: "Graph",
      filepath: "src/widgets/Graph.svelte",
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
