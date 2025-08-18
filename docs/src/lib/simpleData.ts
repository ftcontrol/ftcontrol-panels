// Auto-generated file — do not edit directly
import { type PluginConfig } from "ftc-panels"

export const simpleModules: PluginConfig[] = [
  {
    "id": "com.bylazar.battery",
    "name": "Battery",
    "letterName": "B",
    "description": "Battery Utils for Panels",
    "websiteURL": "https://panels.bylazar.com/docs/com.bylazar.battery/",
    "version": "0.0.10",
    "pluginsCoreVersion": "1.1.26",
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
    "version": "0.0.9",
    "pluginsCoreVersion": "1.1.26",
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
    "version": "0.0.14",
    "pluginsCoreVersion": "1.1.26",
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
        "type": "widget",
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
    "version": "0.0.7",
    "pluginsCoreVersion": "1.1.26",
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
    "version": "0.0.8",
    "pluginsCoreVersion": "1.1.26",
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
    "includedPluginsIDs": []
  },
  {
    "id": "com.bylazar.field",
    "name": "Field",
    "letterName": "F",
    "description": "Field Drawing for Panels",
    "websiteURL": "https://panels.bylazar.com/docs/com.bylazar.field/",
    "version": "0.0.11",
    "pluginsCoreVersion": "1.1.26",
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
    "version": "0.0.40",
    "pluginsCoreVersion": "1.1.26",
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
    "version": "0.0.13",
    "pluginsCoreVersion": "1.1.26",
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
    "id": "com.bylazar.limelightproxy",
    "name": "Limelight Proxy",
    "letterName": "LLP",
    "description": "Limelight Proxy for Panels",
    "websiteURL": "https://panels.bylazar.com/docs/com.bylazar.limelightproxy/",
    "version": "0.0.10",
    "pluginsCoreVersion": "1.1.26",
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
    "version": "0.0.10",
    "pluginsCoreVersion": "1.1.26",
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
    "letterName": "EP",
    "description": "Latency Test for Panels",
    "websiteURL": "https://panels.bylazar.com/docs/com.bylazar.pinger/",
    "version": "0.0.6",
    "pluginsCoreVersion": "1.1.26",
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
    "version": "0.0.8",
    "pluginsCoreVersion": "1.1.26",
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
    "version": "0.0.11",
    "pluginsCoreVersion": "1.1.26",
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
    "version": "0.0.10",
    "pluginsCoreVersion": "1.1.26",
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
  }
] as const
