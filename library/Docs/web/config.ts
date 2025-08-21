import type { PluginConfig } from "ftc-panels"

export const config: PluginConfig = {
  id: "com.bylazar.docs",
  name: "Docs",
  letterName: "D",
  description: "Docs Plugin for Panels",
  websiteURL: "https://panels.bylazar.com/docs/com.bylazar.docs/",
  version: "0.0.12",
  pluginsCoreVersion: "1.1.33",
  author: "Lazar",
  manager: "src/manager.ts",
  components: [
    {
      type: "docs",
      id: "Overview",
      filepath: "src/docs/Overview.svelte",
    },
    {
      type: "docs",
      id: "Prerequisites",
      filepath: "src/docs/Prerequisites.svelte",
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
