import type { PluginConfig } from "ftc-panels"

export const config: PluginConfig = {
  id: "com.bylazar.field",
  name: "Field",
  letterName: "F",
  description: "Field Drawing for Panels",
  websiteURL: "https://panels.bylazar.com/docs/com.bylazar.field/",
  version: "0.0.16",
  pluginsCoreVersion: "1.1.33",
  author: "Lazar",
  manager: "src/manager.ts",
  components: [
    {
      type: "widget",
      id: "Field",
      filepath: "src/widgets/Field.svelte",
    },
    {
      type: "docs",
      id: "Homepage",
      filepath: "src/docs/Homepage.svelte",
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
