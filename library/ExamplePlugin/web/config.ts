import type { PluginConfig } from "ftc-panels"

export const config: PluginConfig = {
  id: "com.bylazar.exampleplugin",
  name: "Example Plugin",
  letterName: "EP",
  description: "Example Plugin for Panels",
  websiteURL: "https://panels.bylazar.com/docs/com.bylazar/exampleplugin",
  mavenURL: "https://mymaven.bylazar.com/dev",
  packageString: "com.bylazar:exampleplugin:<VERSION>",
  version: "0.0.14",
  pluginsCoreVersion: "1.1.37",
  author: "Lazar",
  manager: "src/manager.ts",
  components: [
    {
      type: "widget",
      id: "Counter",
      filepath: "src/widgets/Counter.svelte",
    },
    {
      type: "docs",
      id: "Overview",
      filepath: "src/docs/Overview.svelte",
    },
    {
      type: "docs",
      id: "DocsPage1",
      filepath: "src/docs/DocsPage1.svelte",
    },
  ],
  templates: [],
  includedPluginsIDs: [],
  changelog: [
    {
      version: "0.0.1",
      release_date: "28.10.2000",
      changes: [
        {
          type: "added",
          description: "New telemetry implementation.",
          upgrading: "",
        },
        {
          type: "changed",
          description: "Renames config class",
          upgrading: "",
        },
        {
          type: "deprecated",
          description: "Deprecated old functions",
          upgrading: "",
        },
        {
          type: "removed",
          description: "Removed Telemetry class",
          upgrading: "",
        },
        {
          type: "fixed",
          description: "Bug with Field",
          upgrading: "Reset cookies",
        },
        {
          type: "docs",
          description: "Finished Docs",
          upgrading: "",
        },
        {
          type: "other",
          description: "Changed Colors",
          upgrading: "",
        },
      ],
    },
  ],
}
