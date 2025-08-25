import type { PluginConfig } from "ftc-panels"

export const config: PluginConfig = {
  id: "com.bylazar.themes",
  name: "Themes",
  letterName: "T",
  description:
    "Plugin for Panels that implements custom theming with saving and preset sharing.",
  websiteURL: "https://panels.bylazar.com/docs/com.bylazar.themes/",
  mavenURL: "https://mymaven.bylazar.com/dev",
  packageString: "com.bylazar:themes:<VERSION>",
  version: "0.0.17",
  pluginsCoreVersion: "1.1.37",
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
  changelog: [],
}
