import type { PluginConfig } from "ftc-panels"

export const config: PluginConfig = {
  id: "com.bylazar.opmodecontrol",
  name: "OpMode Control",
  letterName: "OC",
  description: "OpMode Control for FTC Robots",
  websiteURL: "https://panels.bylazar.com/docs/com.bylazar.opmodecontrol/",
  version: "0.0.10",
  pluginsCoreVersion: "1.1.24",
  author: "Lazar",
  widgets: [
    {
      name: "OpModes Control",
      filepath: "src/control/ControlWidget.svelte",
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
    chapters: [],
  },
  templates: [],
  includedPluginsIDs: [],
}
