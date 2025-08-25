import type { PluginConfig } from "ftc-panels"

export const config: PluginConfig = {
  id: "com.bylazar.docs",
  name: "Docs",
  letterName: "D",
  description: "Docs Plugin for Panels",
  websiteURL: "https://panels.bylazar.com/docs/com.bylazar.docs/",
  mavenURL: "https://mymaven.bylazar.com/dev",
  packageString: "com.bylazar:docs:<VERSION>",
  version: "0.0.13",
  pluginsCoreVersion: "1.1.37",
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
  changelog: [],
}
