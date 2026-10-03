import type { PluginConfig } from "ftc-panels"

export const config: PluginConfig = {
  id: "com.bylazar.camerastream",
  name: "Camera Stream",
  letterName: "CS",
  description: "Webcam stream utils for Panels",
  websiteURL: "https://panels.bylazar.com/docs/com.bylazar.camerastream/",
  mavenURL: "https://mymaven.bylazar.com/releases",
  packageString: "com.bylazar:camerastream:<VERSION>",
  version: "1.0.2",
  pluginsCoreVersion: "1.1.44",
  author: "Lazar",
  manager: "src/manager.ts",
  components: [
    {
      type: "widget",
      id: "Camera Stream",
      filepath: "src/navlets/CameraStream.svelte",
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
      release_date: "3.10.2026",
      changes: [{ type: "other", description: "Migrated to Dairy build tooling with explicit versions and official Maven publishing", upgrading: "" }],
    },
    {
      version: "1.0.1",
      release_date: "3.10.2026",
      changes: [{ type: "other", description: "Built against FTC SDK 12.0.0 with pinned frontend core 1.1.44", upgrading: "Update the FTC SDK to 12.0.0 and Panels to 1.0.6 or FullPanels to 1.0.15." }],
    },
    {
      version: "1.0.0",
      release_date: "1.11.2025",
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
