import type { PluginConfig } from "ftc-panels"

export const config: PluginConfig = {
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
          description: "Updated ftc-panels to 1.1.40",
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
}
