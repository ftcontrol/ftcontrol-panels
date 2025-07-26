import { PluginConfig } from "ftc-panels"

export const config: PluginConfig = {
  id: "com.bylazar.themes",
  name: "Themes",
  letterName: "T",
  description: "Themes for Panels",
  version: "0.1.0",
  panelsVersion: "0.0.1",
  pluginsCoreVersion: "0.0.1",
  author: "Lazar",
  widgets: [
    {
      name: "Theme Manager",
      filepath: "src/widgets/Color.svelte",
    },
  ],
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
}
