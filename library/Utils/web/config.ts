import type { PluginConfig } from "ftc-panels"

export const config: PluginConfig = {
  id: "com.bylazar.utils",
  name: "Utils",
  letterName: "U",
  description: "Utils for Panels",
  websiteURL: "https://panels.bylazar.com/docs/com.bylazar.utils/",
  mavenURL: "https://mymaven.bylazar.com/releases",
  packageString: "com.bylazar:utils:<VERSION>",
  version: "1.0.2",
  pluginsCoreVersion: "1.1.43",
  author: "Lazar",
  manager: "src/manager.ts",
  components: [
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
      release_date: "31.08.2025",
      changes: [
        {
          type: "docs",
          description: "One page docs",
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
