import type { PluginConfig } from "ftc-panels"

export const config: PluginConfig = {
  id: "com.bylazar.opmodecontrol",
  name: "OpMode Control",
  letterName: "OC",
  description: "OpMode Control for FTC Robots",
  websiteURL: "https://panels.bylazar.com/docs/com.bylazar.opmodecontrol/",
  mavenURL: "https://mymaven.bylazar.com/releases",
  packageString: "com.bylazar:opmodecontrol:<VERSION>",
  version: "1.0.5",
  pluginsCoreVersion: "1.1.44",
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
      version: "1.0.5",
      release_date: "3.10.2026",
      changes: [{ type: "added", description: "Sloth-aware OpMode discovery and list updates after class loading and unloading, contributed by Oscar Chevalier (@Froze-N-Milk) via PR #15", upgrading: "Add the Dairy releases repository to resolve Sloth dependencies." }],
    },
    {
      version: "1.0.4",
      release_date: "3.10.2026",
      changes: [{ type: "other", description: "Built against FTC SDK 12.0.0 with pinned frontend core 1.1.44", upgrading: "Update the FTC SDK to 12.0.0 and Panels to 1.0.6 or FullPanels to 1.0.15." }],
    },
    {
      version: "1.0.3",
      release_date: "1.11.2025",
      changes: [
        {
          type: "other",
          description: "Updated Panels Core to 1.0.4",
          upgrading: "",
        },
      ],
    },
    {
      version: "1.0.2",
      release_date: "9.09.2025",
      changes: [
        {
          type: "other",
          description: "Updated SDK to 11.0.0",
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
          description: "Updated ftc-panels to 1.1.44",
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
