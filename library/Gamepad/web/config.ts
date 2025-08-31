import type { PluginConfig } from "ftc-panels"

export const config: PluginConfig = {
  id: "com.bylazar.gamepad",
  name: "Gamepad",
  letterName: "G",
  description: "Gamepad Plugin for Panels",
  websiteURL: "https://panels.bylazar.com/docs/com.bylazar.gamepad/",
  mavenURL: "https://mymaven.bylazar.com/releases",
  packageString: "com.bylazar:gamepad:<VERSION>",
  version: "1.0.2",
  pluginsCoreVersion: "1.1.43",
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
  ],
  templates: [],
  includedPluginsIDs: [],
  changelog: [
    {
      version: "1.0.2",
      release_date: "31.08.2025",
      changes: [
        {
          type: "fixed",
          description: "Fixed gamepad front asset",
          upgrading: "",
        },
      ],
    },
    {
      version: "1.0.1",
      release_date: "27.08.2025",
      changes: [
        {
          type: "other",
          description: "Updated ftc-panels to 1.1.43",
          upgrading: "",
        },
      ],
    },
    {
      version: "1.0.0",
      release_date: "26.08.2025",
      changes: [
        {
          type: "other",
          description: "First release",
          upgrading: "",
        },
      ],
    },
  ],
}
