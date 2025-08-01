import { PluginConfig } from "ftc-panels"

export const config: PluginConfig = {
  id: "com.bylazar.fullpanels",
  name: "Full Panels",
  letterName: "FP",
  description: "Full Panels Installation",
  websiteURL: "",
  version: "0.0.14",
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
      name: "Overview",
      filepath: "src/docs/Overview.svelte",
    },
    chapters: [],
  },
  templates: [
    {
      name: "Default Panels",
      widgets: [
        {
          widgets: [
            {
              pluginID: "com.bylazar.opmodecontrol",
              widgetID: "OpModes Control",
            },
          ],
          x: 0,
          y: 0,
          w: 4,
          h: 4,
        },
      ],
      navlets: [
        {
          pluginID: "com.bylazar.battery",
          navletID: "Battery",
        },
      ],
    },
    {
      name: "Test",
      navlets: [
        { pluginID: "com.bylazar.battery", navletID: "Battery" },
        { pluginID: "com.bylazar.battery", navletID: "Battery" },
        { pluginID: "com.bylazar.battery", navletID: "Battery" },
        { pluginID: "com.bylazar.battery", navletID: "Battery" },
      ],
      widgets: [
        { x: 4, y: 0, w: 7, h: 5, widgets: [] },
        { x: 4, y: 5, w: 7, h: 7, widgets: [] },
        {
          x: 0,
          y: 0,
          w: 4,
          h: 11,
          widgets: [
            {
              pluginID: "com.bylazar.configurables",
              widgetID: "Configurables",
            },
          ],
        },
      ],
    },
    {
      name: "Empty",
      navlets: [],
      widgets: [],
    },
  ],
}
