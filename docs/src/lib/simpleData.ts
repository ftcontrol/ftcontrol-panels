// Auto-generated file — do not edit directly
import { type PluginConfig } from "ftc-panels"

export const simpleModules: PluginConfig[] = [
  {
    id: "com.bylazar.battery",
    name: "Battery",
    letterName: "B",
    description: "Battery Utils for Panels",
    websiteURL: "https://panels.bylazar.com/docs/com.bylazar.battery/",
    mavenURL: "https://mymaven.bylazar.com/releases",
    packageString: "com.bylazar:battery:<VERSION>",
    version: "1.0.1",
    pluginsCoreVersion: "1.1.41",
    author: "Lazar",
    manager: "src/manager.ts",
    components: [
      {
        type: "navlet",
        id: "Battery",
        filepath: "src/navlets/Battery.svelte",
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
  },
  {
    id: "com.bylazar.capture",
    name: "Capture",
    letterName: "C",
    description: "Capture Plugin for Panels",
    websiteURL: "https://panels.bylazar.com/docs/com.bylazar.capture/",
    mavenURL: "https://mymaven.bylazar.com/releases",
    packageString: "com.bylazar:capture:<VERSION>",
    version: "1.0.1",
    pluginsCoreVersion: "1.1.41",
    author: "Lazar",
    manager: "src/manager.ts",
    components: [
      {
        type: "widget",
        id: "Capture",
        filepath: "src/widgets/Capture.svelte",
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
  },
  {
    id: "com.bylazar.configurables",
    name: "Configurables",
    letterName: "C",
    description: "Configurable variables for Panels",
    websiteURL: "https://panels.bylazar.com/docs/com.bylazar.configurables/",
    mavenURL: "https://mymaven.bylazar.com/releases",
    packageString: "com.bylazar:configurables:<VERSION>",
    version: "1.0.1",
    pluginsCoreVersion: "1.1.41",
    author: "Lazar",
    components: [
      {
        type: "widget",
        id: "Configurables",
        filepath: "src/widgets/configurables/Configurables.svelte",
      },
      {
        type: "widget",
        id: "ChangedConfigurables",
        filepath: "src/widgets/changed/ChangedConfigurables.svelte",
      },
      {
        type: "docs",
        id: "Homepage",
        filepath: "src/docs/Homepage.svelte",
      },
      {
        type: "docs",
        id: "Copy Semantics",
        filepath: "src/docs/CopySemantics.svelte",
      },
      {
        type: "docs",
        id: "Examples",
        filepath: "src/docs/Examples.svelte",
      },
    ],
    manager: "src/manager.ts",
    templates: [],
    includedPluginsIDs: [],
    changelog: [
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
  },
  {
    id: "com.bylazar.docs",
    name: "Docs",
    letterName: "D",
    description: "Docs Plugin for Panels",
    websiteURL: "https://panels.bylazar.com/docs/com.bylazar.docs/",
    mavenURL: "https://mymaven.bylazar.com/releases",
    packageString: "com.bylazar:docs:<VERSION>",
    version: "1.0.3",
    pluginsCoreVersion: "1.1.41",
    author: "Lazar",
    manager: "src/manager.ts",
    components: [
      {
        type: "docs",
        id: "Overview",
        filepath: "src/docs/Overview.svelte",
      },
      {
        type: "docs",
        id: "Prerequisites",
        filepath: "src/docs/Prerequisites.svelte",
      },
      {
        type: "docs",
        id: "Accessing Panels",
        filepath: "src/docs/Accessing.svelte",
      },
      {
        type: "docs",
        id: "Plugins Development",
        filepath: "src/docs/PluginsDev.svelte",
      },
    ],
    templates: [],
    includedPluginsIDs: [],
    changelog: [
      {
        version: "1.0.3",
        release_date: "27.08.2025",
        changes: [
          {
            type: "docs",
            description:
              "Added warning for people that don't read entire install guide",
            upgrading: "",
          },
          {
            type: "other",
            description: "Updated ftc-panels to 1.1.43",
            upgrading: "",
          },
        ],
      },
      {
        version: "1.0.2",
        release_date: "26.08.2025",
        changes: [
          {
            type: "docs",
            description: "Fixed Quickstart links",
            upgrading: "",
          },
        ],
      },
      {
        version: "1.0.1",
        release_date: "26.08.2025",
        changes: [
          {
            type: "docs",
            description: "Fixed Kotlin Installation Guide",
            upgrading: "",
          },
          {
            type: "docs",
            description: "Added Java & Kotlin Quickstarts links",
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
  },
  {
    id: "com.bylazar.field",
    name: "Field",
    letterName: "F",
    description: "Field Drawing for Panels",
    websiteURL: "https://panels.bylazar.com/docs/com.bylazar.field/",
    mavenURL: "https://mymaven.bylazar.com/releases",
    packageString: "com.bylazar:field:<VERSION>",
    version: "1.0.2",
    pluginsCoreVersion: "1.1.41",
    author: "Lazar",
    manager: "src/manager.ts",
    components: [
      {
        type: "widget",
        id: "Field",
        filepath: "src/widgets/Field.svelte",
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
        version: "1.0.1",
        release_date: "26.08.2025",
        changes: [
          {
            type: "docs",
            description: "Removed test docs page",
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
  },
  {
    id: "com.bylazar.fullpanels",
    name: "Full Panels",
    letterName: "FP",
    description: "Full Panels Installation",
    websiteURL: "https://panels.bylazar.com/docs/com.bylazar.fullpanels/",
    mavenURL: "https://mymaven.bylazar.com/releases",
    packageString: "com.bylazar:fullpanels:<VERSION>",
    version: "1.0.3",
    pluginsCoreVersion: "1.1.41",
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
          {
            navletID: "Battery",
            pluginID: "com.bylazar.battery",
          },
          {
            navletID: "Ping",
            pluginID: "com.bylazar.pinger",
          },
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
              {
                pluginID: "com.bylazar.gamepad",
                widgetID: "Combined Gamepad",
              },
              {
                pluginID: "com.bylazar.capture",
                widgetID: "Capture",
              },
            ],
          },
          {
            x: 11,
            y: 0,
            w: 5,
            h: 9,
            widgets: [
              {
                pluginID: "com.bylazar.field",
                widgetID: "Field",
              },
            ],
          },
          {
            x: 11,
            y: 9,
            w: 5,
            h: 3,
            widgets: [
              {
                pluginID: "com.bylazar.telemetry",
                widgetID: "Telemetry",
              },
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
  },
  {
    id: "com.bylazar.gamepad",
    name: "Gamepad",
    letterName: "G",
    description: "Gamepad Plugin for Panels",
    websiteURL: "https://panels.bylazar.com/docs/com.bylazar.gamepad/",
    mavenURL: "https://mymaven.bylazar.com/releases",
    packageString: "com.bylazar:gamepad:<VERSION>",
    version: "1.0.1",
    pluginsCoreVersion: "1.1.41",
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
  },
  {
    id: "com.bylazar.graph",
    name: "Graph",
    letterName: "G",
    description: "Graphs for Panels",
    websiteURL: "https://panels.bylazar.com/docs/com.bylazar.graph",
    mavenURL: "https://mymaven.bylazar.com/releases",
    packageString: "com.bylazar:graph:<VERSION>",
    version: "1.0.1",
    pluginsCoreVersion: "1.1.41",
    author: "Lazar",
    manager: "src/manager.ts",
    components: [
      {
        type: "widget",
        id: "Graph",
        filepath: "src/widgets/Graph.svelte",
      },
      {
        type: "docs",
        id: "Overview",
        filepath: "src/docs/Overview.svelte",
      },
    ],
    templates: [],
    includedPluginsIDs: [],
    changelog: [
      {
        version: "1.0.1",
        release_date: "27.08.2025",
        changes: [
          {
            type: "docs",
            description: "Basic docs",
            upgrading: "",
          },
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
  },
  {
    id: "com.bylazar.lights",
    name: "Lights",
    letterName: "L",
    description: "Gobilda Light-Based Telemetry",
    websiteURL: "https://panels.bylazar.com/docs/com.bylazar.lights/",
    mavenURL: "https://mymaven.bylazar.com/releases",
    packageString: "com.bylazar:lights:<VERSION>",
    version: "1.0.1",
    pluginsCoreVersion: "1.1.41",
    author: "Lazar",
    manager: "src/manager.ts",
    components: [
      {
        type: "widget",
        id: "Lights",
        filepath: "src/widgets/Lights.svelte",
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
  },
  {
    id: "com.bylazar.limelightproxy",
    name: "Limelight Proxy",
    letterName: "LLP",
    description: "Limelight Proxy for Panels",
    websiteURL: "https://panels.bylazar.com/docs/com.bylazar.limelightproxy/",
    mavenURL: "https://mymaven.bylazar.com/releases",
    packageString: "com.bylazar:limelightproxy:<VERSION>",
    version: "1.0.1",
    pluginsCoreVersion: "1.1.41",
    author: "Lazar",
    manager: "src/manager.ts",
    components: [
      {
        type: "widget",
        id: "CameraStream",
        filepath: "src/widgets/CameraStream.svelte",
      },
      {
        type: "widget",
        id: "Dashboard",
        filepath: "src/widgets/Dashboard.svelte",
      },
      {
        type: "widget",
        id: "Stats",
        filepath: "src/widgets/Stats.svelte",
      },
      {
        type: "navlet",
        id: "Temperature",
        filepath: "src/navlets/Temperature.svelte",
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
  },
  {
    id: "com.bylazar.opmodecontrol",
    name: "OpMode Control",
    letterName: "OC",
    description: "OpMode Control for FTC Robots",
    websiteURL: "https://panels.bylazar.com/docs/com.bylazar.opmodecontrol/",
    mavenURL: "https://mymaven.bylazar.com/releases",
    packageString: "com.bylazar:opmodecontrol:<VERSION>",
    version: "1.0.1",
    pluginsCoreVersion: "1.1.41",
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
  },
  {
    id: "com.bylazar.pinger",
    name: "Pinger",
    letterName: "P",
    description: "Latency Test for Panels",
    websiteURL: "https://panels.bylazar.com/docs/com.bylazar.pinger/",
    mavenURL: "https://mymaven.bylazar.com/releases",
    packageString: "com.bylazar:pinger:<VERSION>",
    version: "1.0.1",
    pluginsCoreVersion: "1.1.41",
    author: "Lazar",
    manager: "src/manager.ts",
    components: [
      {
        type: "navlet",
        id: "Ping",
        filepath: "src/navlets/Ping.svelte",
      },
      {
        type: "docs",
        id: "Overview",
        filepath: "src/docs/Overview.svelte",
      },
    ],
    templates: [],
    includedPluginsIDs: [],
    changelog: [
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
  },
  {
    id: "com.bylazar.telemetry",
    name: "Telemetry",
    letterName: "T",
    description: "Text-Based Telemetry",
    websiteURL: "https://panels.bylazar.com/docs/com.bylazar.telemetry/",
    mavenURL: "https://mymaven.bylazar.com/releases",
    packageString: "com.bylazar:telemetry:<VERSION>",
    version: "1.0.1",
    pluginsCoreVersion: "1.1.41",
    author: "Lazar",
    manager: "src/manager.ts",
    components: [
      {
        type: "widget",
        id: "Telemetry",
        filepath: "src/widgets/Telemetry.svelte",
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
  },
  {
    id: "com.bylazar.themes",
    name: "Themes",
    letterName: "T",
    description:
      "Plugin for Panels that implements custom theming with saving and preset sharing.",
    websiteURL: "https://panels.bylazar.com/docs/com.bylazar.themes/",
    mavenURL: "https://mymaven.bylazar.com/releases",
    packageString: "com.bylazar:themes:<VERSION>",
    version: "1.0.1",
    pluginsCoreVersion: "1.1.41",
    author: "Lazar",
    manager: "src/manager.ts",
    components: [
      {
        type: "widget",
        id: "Theme Manager",
        filepath: "src/widgets/Color.svelte",
      },
      {
        type: "navlet",
        id: "Colors Preview",
        filepath: "src/navlets/Preview.svelte",
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
  },
  {
    id: "com.bylazar.utils",
    name: "Utils",
    letterName: "U",
    description: "Utils for Panels",
    websiteURL: "https://panels.bylazar.com/docs/com.bylazar.utils/",
    mavenURL: "https://mymaven.bylazar.com/releases",
    packageString: "com.bylazar:utils:<VERSION>",
    version: "1.0.1",
    pluginsCoreVersion: "1.1.41",
    author: "Lazar",
    manager: "src/manager.ts",
    components: [
      {
        type: "docs",
        id: "Homepage",
        filepath: "src/docs/Homepage.svelte",
      },
      {
        type: "docs",
        id: "Loop Timer",
        filepath: "src/docs/LoopTimer.svelte",
      },
      {
        type: "docs",
        id: "Moving Average",
        filepath: "src/docs/MovingAverage.svelte",
      },
    ],
    templates: [],
    includedPluginsIDs: [],
    changelog: [
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
  },
  {
    id: "com.bylazar.panels",
    name: "Panels",
    letterName: "P",
    description: "Empty Panels Installation ready to be extended",
    websiteURL: "https://panels.bylazar.com",
    mavenURL: "https://mymaven.bylazar.com/releases",
    packageString: "com.bylazar:panels:<VERSION>",
    version: "1.0.1",
    pluginsCoreVersion: "1.1.41",
    author: "Lazar",
    manager: "",
    components: [],
    templates: [],
    includedPluginsIDs: [],
    changelog: [
      {
        version: "1.0.1",
        release_date: "27.08.2025",
        changes: [
          {
            type: "fixed",
            description: "Made Panels widgets mobile responsive",
            upgrading: "",
          },
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
  },
  {
    id: "com.bylazar.pluginsjscore",
    name: "Plugins JS Core",
    letterName: "PJC",
    description: "Plugins Javascript Core",
    websiteURL: "https://panels.bylazar.com",
    mavenURL: "",
    packageString: "",
    version: "1.1.39",
    pluginsCoreVersion: "1.1.39",
    author: "Lazar",
    manager: "",
    components: [],
    templates: [],
    includedPluginsIDs: [],
    changelog: [
      {
        version: "1.1.41",
        release_date: "27.08.2025",
        changes: [
          {
            type: "fixed",
            description: "Fixed Overlay component.",
            upgrading: "",
          },
        ],
      },
      {
        version: "1.1.40",
        release_date: "27.08.2025",
        changes: [
          {
            type: "fixed",
            description: "Fixed import for Link in docs.",
            upgrading: "",
          },
        ],
      },
      {
        version: "1.1.39",
        release_date: "27.08.2025",
        changes: [
          {
            type: "deprecated",
            description:
              "Moved common docs components for Panels and Docs Website to ftc-panels-docs",
            upgrading: "Use the new library",
          },
          {
            type: "fixed",
            description: "Properly exported typed svelte components.",
            upgrading: "",
          },
        ],
      },
      {
        version: "1.1.38",
        release_date: "26.08.2025",
        changes: [
          {
            type: "added",
            description:
              "Added generic functions to fetch latest versions from GitHub hosted mavens",
            upgrading: "",
          },
        ],
      },
      {
        version: "1.1.37",
        release_date: "25.08.2025",
        changes: [
          {
            type: "fixed",
            description:
              "Fixed docs handling of URL special characters for selected link",
            upgrading: "",
          },
          {
            type: "fixed",
            description: "Fixed JS Core plugin config versions",
            upgrading: "",
          },
        ],
      },
      {
        version: "1.1.36",
        release_date: "25.08.2025",
        changes: [
          {
            type: "fixed",
            description: "Fixed minor docs shared UI logic",
            upgrading: "",
          },
        ],
      },
    ],
  },
] as const
