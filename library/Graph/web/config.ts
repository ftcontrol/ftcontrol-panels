import type { PluginConfig } from "ftc-panels"

export const config: PluginConfig = {
  id: "com.bylazar.graph",
  name: "Graph",
  letterName: "G",
  description: "Graphs for Panels",
  websiteURL: "https://panels.bylazar.com/docs/com.bylazar.graph",
  mavenURL: "https://mymaven.bylazar.com/releases",
  packageString: "com.bylazar:graph:<VERSION>",
  version: "1.0.2",
  pluginsCoreVersion: "1.1.43",
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
      version: "1.0.2",
      release_date: "9.09.2025",
      changes: [
        {
          type: "other",
          description: "Updated SDK to 11.0.0",
          upgrading: "",
        },
      ],
    },
    {
      version: "1.0.1",
      release_date: "27.08.2025",
      changes: [
        {
          type: "docs",
          description: "Basic docs",
          upgrading: "",
        },
        {
          type: "other",
          description: "Updated ftc-panels to 1.1.43",
          upgrading: "",
        },
      ],
    },
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
