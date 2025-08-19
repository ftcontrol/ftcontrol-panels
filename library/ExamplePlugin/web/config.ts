import type { PluginConfig } from "ftc-panels"

export const config: PluginConfig = {
  id: "com.bylazar.exampleplugin",
  name: "Example Plugin",
  letterName: "EP",
  description: "Example Plugin for Panels",
  websiteURL: "",
  version: "0.0.10",
  pluginsCoreVersion: "1.1.28",
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
}
