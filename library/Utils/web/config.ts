import type { PluginConfig } from "ftc-panels"

export const config: PluginConfig = {
  id: "com.bylazar.utils",
  name: "Utils",
  letterName: "U",
  description: "Utils for Panels",
  websiteURL: "https://panels.bylazar.com/docs/com.bylazar.utils/",
  mavenURL: "https://mymaven.bylazar.com/releases",
  packageString: "com.bylazar:utils:<VERSION>",
  version: "1.0.6",
  pluginsCoreVersion: "1.1.44",
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
      version: "1.0.6",
      release_date: "3.10.2026",
      changes: [{ type: "other", description: "Migrated to Dairy build tooling with explicit versions and official Maven publishing", upgrading: "" }],
    },
    {
      version: "1.0.5",
      release_date: "3.10.2026",
      changes: [{ type: "other", description: "Built against FTC SDK 12.0.0 with pinned frontend core 1.1.44", upgrading: "Update the FTC SDK to 12.0.0 and Panels to 1.0.6 or FullPanels to 1.0.15." }],
    },
    {
      version: "1.0.4",
      release_date: "1.11.2025",
      changes: [
        {
          type: "other",
          description: "Updated Panels Core to 1.0.4",
          upgrading: "",
        },
      ],
    },
    {
      version: "1.0.3",
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
          description: "Updated ftc-panels to 1.1.44",
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
