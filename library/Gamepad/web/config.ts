import { PluginConfig } from "ftc-panels"

export const config: PluginConfig = {
  id: "com.bylazar.gamepad",
  name: "Gamepad",
  letterName: "G",
  description: "Gamepad Plugin for Panels",
  websiteURL: "",
  version: "0.0.2",
  pluginsCoreVersion: "0.0.1",
  author: "Lazar",
  widgets: [
    {
      name: "Gamepad",
      filepath: "src/widgets/Gamepad.svelte",
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
