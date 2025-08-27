import type { PluginConfig } from "ftc-panels"

export const config: PluginConfig = {
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
}
