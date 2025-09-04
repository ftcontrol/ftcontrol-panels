import type { PluginConfig } from "ftc-panels"

export const config: PluginConfig = {
  id: "com.bylazar.fullpanels",
  name: "Full Panels",
  letterName: "FP",
  description: "Full Panels Installation",
  websiteURL: "https://panels.bylazar.com/docs/com.bylazar.fullpanels/",
  mavenURL: "https://mymaven.bylazar.com/releases",
  packageString: "com.bylazar:fullpanels:<VERSION>",
  version: "1.0.5",
  pluginsCoreVersion: "1.1.43",
  author: "Lazar",
  manager: "src/manager.ts",
  components: [
    {
      type: "docs",
      id: "Homepage",
      filepath: "src/docs/Homepage.svelte",
    },
  ],
  templates: [
    {
      name: "Default",
      navlets: [
        { navletID: "Battery", pluginID: "com.bylazar.battery" },
        { navletID: "Ping", pluginID: "com.bylazar.pinger" },
      ],
      widgets: [
        {
          x: 0,
          y: 0,
          w: 5,
          h: 4,
          widgets: [
            {
              pluginID: "com.bylazar.opmodecontrol",
              widgetID: "OpModes Control",
            },
          ],
        },
        {
          x: 5,
          y: 0,
          w: 6,
          h: 12,
          widgets: [
            {
              pluginID: "com.bylazar.configurables",
              widgetID: "Configurables",
            },
            {
              pluginID: "com.bylazar.configurables",
              widgetID: "ChangedConfigurables",
            },
          ],
        },
        {
          x: 0,
          y: 4,
          w: 5,
          h: 8,
          widgets: [
            { pluginID: "com.bylazar.gamepad", widgetID: "Combined Gamepad" },
            { pluginID: "com.bylazar.capture", widgetID: "Capture" },
          ],
        },
        {
          x: 11,
          y: 0,
          w: 5,
          h: 9,
          widgets: [{ pluginID: "com.bylazar.field", widgetID: "Field" }],
        },
        {
          x: 11,
          y: 9,
          w: 5,
          h: 3,
          widgets: [
            { pluginID: "com.bylazar.telemetry", widgetID: "Telemetry" },
          ],
        },
      ],
    },
  ],
  includedPluginsIDs: [
    "com.bylazar.panels",
    "com.bylazar.battery",
    "com.bylazar.capture",
    "com.bylazar.configurables",
    "com.bylazar.docs",
    "com.bylazar.field",
    "com.bylazar.gamepad",
    "com.bylazar.limelightproxy",
    "com.bylazar.opmodecontrol",
    "com.bylazar.telemetry",
    "com.bylazar.themes",
    "com.bylazar.utils",
    "com.bylazar.pinger",
    "com.bylazar.graph",
    "com.bylazar.lights",
  ],
  changelog: [
    {
      version: "1.0.5",
      release_date: "04.09.2025",
      changes: [
        {
          type: "added",
          description: "Updated Telemetry to 1.0.2",
          upgrading: "",
        },
      ],
    },
    {
      version: "1.0.4",
      release_date: "31.08.2025",
      changes: [
        {
          type: "docs",
          description: "One page docs for updated plugins",
          upgrading: "",
        },
      ],
    },
    {
      version: "1.0.3",
      release_date: "27.08.2025",
      changes: [
        {
          type: "docs",
          description: "Removed old version fetcher from docs",
          upgrading: "",
        },
        {
          type: "added",
          description: "Added graph & lights to full-panels",
          upgrading: "",
        },
        {
          type: "other",
          description: "Updated ftc-panels to 1.1.43 && all plugins",
          upgrading: "",
        },
      ],
    },
    {
      version: "1.0.2",
      release_date: "26.08.2025",
      changes: [
        {
          type: "added",
          description: "Updated Docs to 1.0.2",
          upgrading: "",
        },
      ],
    },
    {
      version: "1.0.1",
      release_date: "26.08.2025",
      changes: [
        {
          type: "added",
          description: "Updated Docs to 1.0.1",
          upgrading: "",
        },
        {
          type: "added",
          description: "Updated Field to 1.0.1",
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
