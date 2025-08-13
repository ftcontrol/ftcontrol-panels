import type { PluginConfig } from "ftc-panels"

export const config: PluginConfig = {
  id: "com.bylazar.configurables",
  name: "Configurables",
  letterName: "C",
  description: "Configurable variables for Panels",
  websiteURL: "",
  version: "0.0.10",
  pluginsCoreVersion: "1.1.14",
  author: "Lazar",
  widgets: [
    {
      name: "Configurables",
      filepath: "src/widgets/configurables/Configurables.svelte",
    },
    {
      name: "ChangedConfigurables",
      filepath: "src/widgets/changed/ChangedConfigurables.svelte",
    },
  ],
  navlets: [],
  manager: {
    name: "Manager",
    filepath: "src/manager.ts",
  },
  docs: {
    homepage: {
      name: "Homepage",
      filepath: "src/docs/Homepage.svelte",
    },
    chapters: [
      {
        name: "DocsPage1",
        filepath: "src/docs/DocsPage1.svelte",
      },
    ],
  },
  templates: [],
}
