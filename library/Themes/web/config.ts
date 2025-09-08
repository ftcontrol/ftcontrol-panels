import type { PluginConfig } from "ftc-panels"

export const config: PluginConfig = {
  id: "com.bylazar.themes",
  name: "Themes",
  letterName: "T",
  description:
    "Plugin for Panels that implements custom theming with saving and preset sharing.",
  websiteURL: "https://panels.bylazar.com/docs/com.bylazar.themes/",
  mavenURL: "https://mymaven.bylazar.com/releases",
  packageString: "com.bylazar:themes:<VERSION>",
  version: "1.0.2",
  pluginsCoreVersion: "1.1.43",
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
      version: "1.0.2",
      release_date: "9.09.2025",
      changes: [
        {
          type: "other",
          description: "Updated SDK to 11.0.0",
          upgrading: "",
        },
      ],
    },
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
