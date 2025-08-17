import type { PluginConfig } from "ftc-panels"

export const config: PluginConfig = {
  id: "com.bylazar.docs",
  name: "Docs",
  letterName: "D",
  description: "Docs Plugin for Panels",
  websiteURL: "https://panels.bylazar.com/docs/com.bylazar.docs/",
  version: "0.0.7",
  pluginsCoreVersion: "1.1.23",
  author: "Lazar",
  widgets: [],
  navlets: [],
  manager: {
    name: "Manager",
    filepath: "src/manager.ts",
  },
  docs: {
    homepage: {
      name: "Overview",
      filepath: "src/docs/Overview.svelte",
    },
    chapters: [
      {
        name: "Prerequisites",
        filepath: "src/docs/Prerequisites.svelte",
      },
      {
        name: "DocsPage1",
        filepath: "src/docs/DocsPage1.svelte",
      },
    ],
  },
  templates: [],
  includedPluginsIDs: [],
}
