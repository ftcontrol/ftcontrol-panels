import type { PluginConfig } from "ftc-panels"

export const config: PluginConfig = {
  id: "com.bylazar.configurables",
  name: "Configurables",
  letterName: "C",
  description: "Configurable variables for Panels",
  websiteURL: "https://panels.bylazar.com/docs/com.bylazar.configurables/",
  version: "0.0.17",
  pluginsCoreVersion: "1.1.29",
  author: "Lazar",
  components: [
    {
      type: "widget",
      id: "Configurables",
      filepath: "src/widgets/configurables/Configurables.svelte",
    },
    {
      type: "widget",
      id: "ChangedConfigurables",
      filepath: "src/widgets/changed/ChangedConfigurables.svelte",
    },
    {
      type: "docs",
      id: "Homepage",
      filepath: "src/docs/Homepage.svelte",
    },
    {
      type: "widget",
      id: "DocsPage1",
      filepath: "src/docs/DocsPage1.svelte",
    },
  ],
  manager: "src/manager.ts",
  templates: [],
  includedPluginsIDs: [],
}
