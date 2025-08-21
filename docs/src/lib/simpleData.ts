// Auto-generated file — do not edit directly
import { type PluginConfig } from "ftc-panels"

export const simpleModules: PluginConfig[] = [
  {
    "id": "com.bylazar.battery",
    "name": "Battery",
    "letterName": "B",
    "description": "Battery Utils for Panels",
    "websiteURL": "https://panels.bylazar.com/docs/com.bylazar.battery/",
    "version": "0.0.15",
    "pluginsCoreVersion": "1.1.33",
    "author": "Lazar",
    "manager": "src/manager.ts",
    "components": [
      {
        "type": "navlet",
        "id": "Battery",
        "filepath": "src/navlets/Battery.svelte"
      },
      {
        "type": "docs",
        "id": "Homepage",
        "filepath": "src/docs/Homepage.svelte"
      }
    ],
    "templates": [],
    "includedPluginsIDs": []
  },
  {
    "id": "com.bylazar.capture",
    "name": "Capture",
    "letterName": "C",
    "description": "Capture Plugin for Panels",
    "websiteURL": "https://panels.bylazar.com/docs/com.bylazar.capture/",
    "version": "0.0.14",
    "pluginsCoreVersion": "1.1.33",
    "author": "Lazar",
    "manager": "src/manager.ts",
    "components": [
      {
        "type": "widget",
        "id": "Capture",
        "filepath": "src/widgets/Capture.svelte"
      },
      {
        "type": "docs",
        "id": "Homepage",
        "filepath": "src/docs/Homepage.svelte"
      }
    ],
    "templates": [],
    "includedPluginsIDs": []
  },
  {
    "id": "com.bylazar.configurables",
    "name": "Configurables",
    "letterName": "C",
    "description": "Configurable variables for Panels",
    "websiteURL": "https://panels.bylazar.com/docs/com.bylazar.configurables/",
    "version": "0.0.21",
    "pluginsCoreVersion": "1.1.33",
    "author": "Lazar",
    "components": [
      {
        "type": "widget",
        "id": "Configurables",
        "filepath": "src/widgets/configurables/Configurables.svelte"
      },
      {
        "type": "widget",
        "id": "ChangedConfigurables",
        "filepath": "src/widgets/changed/ChangedConfigurables.svelte"
      },
      {
        "type": "docs",
        "id": "Homepage",
        "filepath": "src/docs/Homepage.svelte"
      },
      {
        "type": "docs",
        "id": "DocsPage1",
        "filepath": "src/docs/DocsPage1.svelte"
      }
    ],
    "manager": "src/manager.ts",
    "templates": [],
    "includedPluginsIDs": []
  },
  {
    "id": "com.bylazar.docs",
    "name": "Docs",
    "letterName": "D",
    "description": "Docs Plugin for Panels",
    "websiteURL": "https://panels.bylazar.com/docs/com.bylazar.docs/",
    "version": "0.0.12",
    "pluginsCoreVersion": "1.1.33",
    "author": "Lazar",
    "manager": "src/manager.ts",
    "components": [
      {
        "type": "docs",
        "id": "Overview",
        "filepath": "src/docs/Overview.svelte"
      },
      {
        "type": "docs",
        "id": "Prerequisites",
        "filepath": "src/docs/Prerequisites.svelte"
      },
      {
        "type": "docs",
        "id": "DocsPage1",
        "filepath": "src/docs/DocsPage1.svelte"
      }
    ],
    "templates": [],
    "includedPluginsIDs": []
  },
  {
    "id": "com.bylazar.exampleplugin",
    "name": "Example Plugin",
    "letterName": "EP",
    "description": "Example Plugin for Panels",
    "websiteURL": "",
    "version": "0.0.13",
    "pluginsCoreVersion": "link:ftc-panels",
    "author": "Lazar",
    "manager": "src/manager.ts",
    "components": [
      {
        "type": "widget",
        "id": "Counter",
        "filepath": "src/widgets/Counter.svelte"
      },
      {
        "type": "docs",
        "id": "Overview",
        "filepath": "src/docs/Overview.svelte"
      },
      {
        "type": "docs",
        "id": "DocsPage1",
        "filepath": "src/docs/DocsPage1.svelte"
      }
    ],
    "templates": [],
    "includedPluginsIDs": [],
    "changelog": [
      {
        "version": "0.0.1",
        "release_date": "28.10.2000",
        "changes": [
          {
            "type": "added",
            "description": "New telemetry implementation.",
            "upgrading": ""
          },
          {
            "type": "changed",
            "description": "Renames config class",
            "upgrading": ""
          },
          {
            "type": "deprecated",
            "description": "Deprecated old functions",
            "upgrading": ""
          },
          {
            "type": "removed",
            "description": "Removed Telemetry class",
            "upgrading": ""
          },
          {
            "type": "fixed",
            "description": "Bug with Field",
            "upgrading": "Reset cookies"
          },
          {
            "type": "docs",
            "description": "Finished Docs",
            "upgrading": ""
          },
          {
            "type": "other",
            "description": "Changed Colors",
            "upgrading": ""
          }
        ]
      }
    ]
  },
  {
    "id": "com.bylazar.field",
    "name": "Field",
    "letterName": "F",
    "description": "Field Drawing for Panels",
    "websiteURL": "https://panels.bylazar.com/docs/com.bylazar.field/",
    "version": "0.0.16",
    "pluginsCoreVersion": "1.1.33",
    "author": "Lazar",
    "manager": "src/manager.ts",
    "components": [
      {
        "type": "widget",
        "id": "Field",
        "filepath": "src/widgets/Field.svelte"
      },
      {
        "type": "docs",
        "id": "Homepage",
        "filepath": "src/docs/Homepage.svelte"
      },
      {
        "type": "docs",
        "id": "DocsPage1",
        "filepath": "src/docs/DocsPage1.svelte"
      }
    ],
    "templates": [],
    "includedPluginsIDs": []
  },
  {
    "id": "com.bylazar.fullpanels",
    "name": "Full Panels",
    "letterName": "FP",
    "description": "Full Panels Installation",
    "websiteURL": "https://panels.bylazar.com/docs/com.bylazar.fullpanels/",
    "version": "0.0.50",
    "pluginsCoreVersion": "1.1.33",
    "author": "Lazar",
    "manager": "src/manager.ts",
    "components": [
      {
        "type": "docs",
        "id": "Homepage",
        "filepath": "src/docs/Homepage.svelte"
      }
    ],
    "templates": [
      {
        "name": "Default",
        "navlets": [
          {
            "navletID": "Battery",
            "pluginID": "com.bylazar.battery"
          },
          {
            "navletID": "Ping",
            "pluginID": "com.bylazar.pinger"
          }
        ],
        "widgets": [
          {
            "x": 0,
            "y": 0,
            "w": 5,
            "h": 4,
            "widgets": [
              {
                "pluginID": "com.bylazar.opmodecontrol",
                "widgetID": "OpModes Control"
              }
            ]
          },
          {
            "x": 5,
            "y": 0,
            "w": 6,
            "h": 12,
            "widgets": [
              {
                "pluginID": "com.bylazar.configurables",
                "widgetID": "Configurables"
              },
              {
                "pluginID": "com.bylazar.configurables",
                "widgetID": "ChangedConfigurables"
              }
            ]
          },
          {
            "x": 0,
            "y": 4,
            "w": 5,
            "h": 8,
            "widgets": [
              {
                "pluginID": "com.bylazar.gamepad",
                "widgetID": "Combined Gamepad"
              },
              {
                "pluginID": "com.bylazar.capture",
                "widgetID": "Capture"
              }
            ]
          },
          {
            "x": 11,
            "y": 0,
            "w": 5,
            "h": 9,
            "widgets": [
              {
                "pluginID": "com.bylazar.field",
                "widgetID": "Field"
              }
            ]
          },
          {
            "x": 11,
            "y": 9,
            "w": 5,
            "h": 3,
            "widgets": [
              {
                "pluginID": "com.bylazar.telemetry",
                "widgetID": "Telemetry"
              }
            ]
          }
        ]
      }
    ],
    "includedPluginsIDs": [
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
      "com.bylazar.pinger"
    ]
  },
  {
    "id": "com.bylazar.gamepad",
    "name": "Gamepad",
    "letterName": "G",
    "description": "Gamepad Plugin for Panels",
    "websiteURL": "https://panels.bylazar.com/docs/com.bylazar.gamepad/",
    "version": "0.0.18",
    "pluginsCoreVersion": "1.1.33",
    "author": "Lazar",
    "manager": "src/manager.ts",
    "components": [
      {
        "type": "widget",
        "id": "First Gamepad",
        "filepath": "src/widgets/FirstGamepad.svelte"
      },
      {
        "type": "widget",
        "id": "Second Gamepad",
        "filepath": "src/widgets/SecondGamepad.svelte"
      },
      {
        "type": "widget",
        "id": "Combined Gamepad",
        "filepath": "src/widgets/CombinedGamepad.svelte"
      },
      {
        "type": "docs",
        "id": "Homepage",
        "filepath": "src/docs/Homepage.svelte"
      },
      {
        "type": "docs",
        "id": "DocsPage1",
        "filepath": "src/docs/DocsPage1.svelte"
      }
    ],
    "templates": [],
    "includedPluginsIDs": []
  },
  {
    "id": "com.bylazar.graph",
    "name": "Graph",
    "letterName": "G",
    "description": "Graphs for Panels",
    "websiteURL": "https://panels.bylazar.com/docs/com.bylazar.graph",
    "version": "0.0.2",
    "pluginsCoreVersion": "1.1.33",
    "author": "Lazar",
    "manager": "src/manager.ts",
    "components": [
      {
        "type": "widget",
        "id": "Graph",
        "filepath": "src/widgets/Graph.svelte"
      },
      {
        "type": "docs",
        "id": "Overview",
        "filepath": "src/docs/Overview.svelte"
      }
    ],
    "templates": [],
    "includedPluginsIDs": []
  },
  {
    "id": "com.bylazar.lights",
    "name": "Lights",
    "letterName": "L",
    "description": "Gobilda Light-Based Telemetry",
    "websiteURL": "https://panels.bylazar.com/docs/com.bylazar.lights/",
    "version": "0.0.1",
    "pluginsCoreVersion": "1.1.33",
    "author": "Lazar",
    "manager": "src/manager.ts",
    "components": [
      {
        "type": "widget",
        "id": "Lights",
        "filepath": "src/widgets/Lights.svelte"
      },
      {
        "type": "docs",
        "id": "Homepage",
        "filepath": "src/docs/Homepage.svelte"
      }
    ],
    "templates": [],
    "includedPluginsIDs": []
  },
  {
    "id": "com.bylazar.limelightproxy",
    "name": "Limelight Proxy",
    "letterName": "LLP",
    "description": "Limelight Proxy for Panels",
    "websiteURL": "https://panels.bylazar.com/docs/com.bylazar.limelightproxy/",
    "version": "0.0.16",
    "pluginsCoreVersion": "1.1.33",
    "author": "Lazar",
    "manager": "src/manager.ts",
    "components": [
      {
        "type": "widget",
        "id": "CameraStream",
        "filepath": "src/widgets/CameraStream.svelte"
      },
      {
        "type": "widget",
        "id": "Dashboard",
        "filepath": "src/widgets/Dashboard.svelte"
      },
      {
        "type": "widget",
        "id": "Stats",
        "filepath": "src/widgets/Stats.svelte"
      },
      {
        "type": "navlet",
        "id": "Temperature",
        "filepath": "src/navlets/Temperature.svelte"
      },
      {
        "type": "docs",
        "id": "Homepage",
        "filepath": "src/docs/Homepage.svelte"
      },
      {
        "type": "docs",
        "id": "DocsPage1",
        "filepath": "src/docs/DocsPage1.svelte"
      }
    ],
    "templates": [],
    "includedPluginsIDs": []
  },
  {
    "id": "com.bylazar.opmodecontrol",
    "name": "OpMode Control",
    "letterName": "OC",
    "description": "OpMode Control for FTC Robots",
    "websiteURL": "https://panels.bylazar.com/docs/com.bylazar.opmodecontrol/",
    "version": "0.0.15",
    "pluginsCoreVersion": "1.1.33",
    "author": "Lazar",
    "manager": "src/manager.ts",
    "components": [
      {
        "type": "widget",
        "id": "OpModes Control",
        "filepath": "src/control/ControlWidget.svelte"
      },
      {
        "type": "docs",
        "id": "Homepage",
        "filepath": "src/docs/Homepage.svelte"
      }
    ],
    "templates": [],
    "includedPluginsIDs": []
  },
  {
    "id": "com.bylazar.pinger",
    "name": "Pinger",
    "letterName": "P",
    "description": "Latency Test for Panels",
    "websiteURL": "https://panels.bylazar.com/docs/com.bylazar.pinger/",
    "version": "0.0.11",
    "pluginsCoreVersion": "1.1.33",
    "author": "Lazar",
    "manager": "src/manager.ts",
    "components": [
      {
        "type": "navlet",
        "id": "Ping",
        "filepath": "src/navlets/Ping.svelte"
      },
      {
        "type": "docs",
        "id": "Overview",
        "filepath": "src/docs/Overview.svelte"
      }
    ],
    "templates": [],
    "includedPluginsIDs": []
  },
  {
    "id": "com.bylazar.telemetry",
    "name": "Telemetry",
    "letterName": "T",
    "description": "Text-Based Telemetry",
    "websiteURL": "https://panels.bylazar.com/docs/com.bylazar.telemetry/",
    "version": "0.0.13",
    "pluginsCoreVersion": "1.1.33",
    "author": "Lazar",
    "manager": "src/manager.ts",
    "components": [
      {
        "type": "widget",
        "id": "Telemetry",
        "filepath": "src/widgets/Telemetry.svelte"
      },
      {
        "type": "docs",
        "id": "Homepage",
        "filepath": "src/docs/Homepage.svelte"
      },
      {
        "type": "docs",
        "id": "DocsPage1",
        "filepath": "src/docs/DocsPage1.svelte"
      }
    ],
    "templates": [],
    "includedPluginsIDs": []
  },
  {
    "id": "com.bylazar.themes",
    "name": "Themes",
    "letterName": "T",
    "description": "Plugin for Panels that implements custom theming with saving and preset sharing.",
    "websiteURL": "https://panels.bylazar.com/docs/com.bylazar.themes/",
    "version": "0.0.16",
    "pluginsCoreVersion": "1.1.33",
    "author": "Lazar",
    "manager": "src/manager.ts",
    "components": [
      {
        "type": "widget",
        "id": "Theme Manager",
        "filepath": "src/widgets/Color.svelte"
      },
      {
        "type": "navlet",
        "id": "Colors Preview",
        "filepath": "src/navlets/Preview.svelte"
      },
      {
        "type": "docs",
        "id": "Homepage",
        "filepath": "src/docs/Homepage.svelte"
      }
    ],
    "templates": [],
    "includedPluginsIDs": []
  },
  {
    "id": "com.bylazar.utils",
    "name": "Utils",
    "letterName": "U",
    "description": "Utils for Panels",
    "websiteURL": "https://panels.bylazar.com/docs/com.bylazar.utils/",
    "version": "0.0.15",
    "pluginsCoreVersion": "1.1.33",
    "author": "Lazar",
    "manager": "src/manager.ts",
    "components": [
      {
        "type": "docs",
        "id": "Homepage",
        "filepath": "src/docs/Homepage.svelte"
      },
      {
        "type": "docs",
        "id": "Loop Timer",
        "filepath": "src/docs/LoopTimer.svelte"
      },
      {
        "type": "docs",
        "id": "Moving Average",
        "filepath": "src/docs/MovingAverage.svelte"
      }
    ],
    "templates": [],
    "includedPluginsIDs": []
  },
  {
    "id": "com.bylazar.panels",
    "name": "Panels",
    "letterName": "P",
    "description": "Empty Panels Installation ready to be extended",
    "websiteURL": "https://panels.bylazar.com",
    "mavenURL": "https://mymaven.bylazar.com/dev",
    "packageString": "com.bylazar:panels:<VERSION>",
    "version": "0.0.29",
    "pluginsCoreVersion": "1.1.33",
    "author": "Lazar",
    "manager": "",
    "components": [],
    "templates": [],
    "includedPluginsIDs": [],
    "changelog": []
  },
  {
    "id": "com.bylazar.pluginsjscore",
    "name": "Plugins JS Core",
    "letterName": "PJC",
    "description": "Plugins Javascript Core",
    "websiteURL": "https://panels.bylazar.com",
    "mavenURL": "",
    "packageString": "",
    "version": "1.1.33",
    "pluginsCoreVersion": "1.1.33",
    "author": "Lazar",
    "manager": "",
    "components": [],
    "templates": [],
    "includedPluginsIDs": [],
    "changelog": []
  }
] as const
