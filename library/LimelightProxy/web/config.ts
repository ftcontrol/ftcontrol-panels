import type { PluginConfig } from "ftc-panels"

export const config: PluginConfig = {
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
          description: "Updated ftc-panels to 1.1.40",
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
