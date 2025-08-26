import type { PluginConfig } from "ftc-panels"

export const config: PluginConfig = {
  id: "com.bylazar.docs",
  name: "Docs",
  letterName: "D",
  description: "Docs Plugin for Panels",
  websiteURL: "https://panels.bylazar.com/docs/com.bylazar.docs/",
  mavenURL: "https://mymaven.bylazar.com/releases",
  packageString: "com.bylazar:docs:<VERSION>",
  version: "1.0.1",
  pluginsCoreVersion: "1.1.38",
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
}
