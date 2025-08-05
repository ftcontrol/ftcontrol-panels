import { PluginConfig } from "ftc-panels"

export const config: PluginConfig = {
  id: "com.bylazar.field",
  name: "Field",
  letterName: "F",
  description: "Field Drawing for Panels",
  websiteURL: "",
  version: "0.0.4",
  pluginsCoreVersion: "0.0.1",
  author: "Lazar",
  widgets: [
    {
      name: "Field",
      filepath: "src/widgets/Field.svelte",
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
