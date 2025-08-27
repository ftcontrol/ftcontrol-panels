import type { PluginConfig } from "ftc-panels"

export const config: PluginConfig = {
  id: "com.bylazar.configurables",
  name: "Configurables",
  letterName: "C",
  description: "Configurable variables for Panels",
  websiteURL: "https://panels.bylazar.com/docs/com.bylazar.configurables/",
  mavenURL: "https://mymaven.bylazar.com/releases",
  packageString: "com.bylazar:configurables:<VERSION>",
  version: "1.0.1",
  pluginsCoreVersion: "1.1.43",
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
}
