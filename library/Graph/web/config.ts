import type { PluginConfig } from "ftc-panels"

export const config: PluginConfig = {
  id: "com.bylazar.graph",
  name: "Graph",
  letterName: "G",
  description: "Graphs for Panels",
  websiteURL: "https://panels.bylazar.com/docs/com.bylazar.graph",
  mavenURL: "https://mymaven.bylazar.com/releases",
  packageString: "com.bylazar:graph:<VERSION>",
  version: "1.0.0",
  pluginsCoreVersion: "1.1.38",
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
