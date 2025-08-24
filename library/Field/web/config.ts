import type { PluginConfig } from "ftc-panels"

export const config: PluginConfig = {
  id: "com.bylazar.field",
  name: "Field",
  letterName: "F",
  description: "Field Drawing for Panels",
  websiteURL: "https://panels.bylazar.com/docs/com.bylazar.field/",
  mavenURL: "https://mymaven.bylazar.com/dev",
  packageString: "com.bylazar:field:<VERSION>",
  version: "0.0.17",
  pluginsCoreVersion: "1.1.35",
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
  changelog: [],
}
