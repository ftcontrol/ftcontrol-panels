import type { PluginConfig } from "ftc-panels"

export const config: PluginConfig = {
  id: "com.bylazar.gamepad",
  name: "Gamepad",
  letterName: "G",
  description: "Gamepad Plugin for Panels",
  websiteURL: "",
  version: "0.0.11",
  pluginsCoreVersion: "0.0.1",
  author: "Lazar",
  widgets: [
    {
      name: "First Gamepad",
      filepath: "src/widgets/FirstGamepad.svelte",
    },
    {
      name: "Second Gamepad",
      filepath: "src/widgets/SecondGamepad.svelte",
    },
    {
      name: "Combined Gamepad",
      filepath: "src/widgets/CombinedGamepad.svelte",
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
