import type { PluginConfig } from "ftc-panels"

export const config: PluginConfig = {
  id: "com.bylazar.limelightproxy",
  name: "Limelight Proxy",
  letterName: "LLP",
  description: "Limelight Proxy for Panels",
  websiteURL: "https://panels.bylazar.com/docs/com.bylazar.limelightproxy/",
  mavenURL: "https://mymaven.bylazar.com/dev",
  packageString: "com.bylazar:limelightproxy:<VERSION>",
  version: "0.0.17",
  pluginsCoreVersion: "1.1.37",
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
  changelog: [],
}
