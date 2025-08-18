import type { PluginConfig } from "ftc-panels"

export const config: PluginConfig = {
  id: "com.bylazar.opmodecontrol",
  name: "OpMode Control",
  letterName: "OC",
  description: "OpMode Control for FTC Robots",
  websiteURL: "https://panels.bylazar.com/docs/com.bylazar.opmodecontrol/",
  version: "0.0.10",
  pluginsCoreVersion: "1.1.25",
  author: "Lazar",
  manager: "src/manager.ts",
  components: [
    {
      type: "widget",
      id: "OpModes Control",
      filepath: "src/control/ControlWidget.svelte",
    },
    {
      type: "docs",
      id: "Homepage",
      filepath: "src/docs/Homepage.svelte",
    },
  ],
  templates: [],
  includedPluginsIDs: [],
}
