import { PluginConfig } from "ftc-panels"

export const config: PluginConfig = {
  id: "com.bylazar.fullpanels",
  name: "Full Panels",
  letterName: "FP",
  description: "Full Panels Installation",
  websiteURL: "",
  version: "0.0.25",
  pluginsCoreVersion: "0.0.1",
  author: "Lazar",
  widgets: [],
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
  templates: [
    {
      name: "Default",
      navlets: [{ navletID: "Battery", pluginID: "com.bylazar.battery" }],
      widgets: [
        {
          x: 0,
          y: 0,
          w: 4,
          h: 4,
          widgets: [
            {
              pluginID: "com.bylazar.opmodecontrol",
              widgetID: "OpModes Control",
            },
          ],
        },
        {
          x: 4,
          y: 0,
          w: 7,
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
          w: 4,
          h: 8,
          widgets: [
            { pluginID: "com.bylazar.gamepad", widgetID: "Gamepad" },
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
}
