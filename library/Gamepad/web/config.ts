import type { PluginConfig } from "ftc-panels"

export const config: PluginConfig = {
  id: "com.bylazar.gamepad",
  name: "Gamepad",
  letterName: "G",
  description: "Gamepad Plugin for Panels",
  websiteURL: "https://panels.bylazar.com/docs/com.bylazar.gamepad/",
  version: "0.0.13",
  pluginsCoreVersion: "1.1.28",
  author: "Lazar",
  manager: "src/manager.ts",
  components: [
    {
      type: "widget",
      id: "First Gamepad",
      filepath: "src/widgets/FirstGamepad.svelte",
    },
    {
      type: "widget",
      id: "Second Gamepad",
      filepath: "src/widgets/SecondGamepad.svelte",
    },
    {
      type: "widget",
      id: "Combined Gamepad",
      filepath: "src/widgets/CombinedGamepad.svelte",
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
