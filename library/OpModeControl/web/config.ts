import type { PluginConfig } from "ftc-panels"

export const config: PluginConfig = {
  id: "com.bylazar.opmodecontrol",
  name: "OpMode Control",
  letterName: "OC",
  description: "OpMode Control for FTC Robots",
  websiteURL: "https://panels.bylazar.com/docs/com.bylazar.opmodecontrol/",
  mavenURL: "https://mymaven.bylazar.com/releases",
  packageString: "com.bylazar:opmodecontrol:<VERSION>",
  version: "1.0.0",
  pluginsCoreVersion: "1.1.38",
  author: "Lazar",
  manager: "src/manager.ts",
  components: [
    {
      type: "widget",
      id: "OpModes Control",
      filepath: "src/control/ControlWidget.svelte",
    },
    {
      type: "widget",
      id: "OpModes Timer",
      filepath: "src/control/TimerWidget.svelte",
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
