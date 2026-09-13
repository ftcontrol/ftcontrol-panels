import type { PluginConfig } from "ftc-panels"

export const config: PluginConfig = {
  id: "com.bylazar.field",
  name: "Field",
  letterName: "F",
  description: "Field Drawing for Panels",
  websiteURL: "https://panels.bylazar.com/docs/com.bylazar.field/",
  mavenURL: "https://mymaven.bylazar.com/releases",
  packageString: "com.bylazar:field:<VERSION>",
  version: "1.0.7",
  pluginsCoreVersion: "1.1.44",
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
      version: "1.0.7",
      release_date: "13.09.2026",
      changes: [
        {
          type: "added",
          description: "Added BIOBUZZ light and dark field images",
          upgrading: "BIOBUZZ is now the default field image. DECODE images remain available.",
        },
      ],
    },
    {
      version: "1.0.6",
      release_date: "6.12.2025",
      changes: [
        {
          type: "fixed",
          description: "Resolved issue with field and OS zoom level",
          upgrading: "",
        },
      ],
    },
    {
      version: "1.0.5",
      release_date: "14.11.2025",
      changes: [
        {
          type: "added",
          description:
            "Custom Presets can be shown in widget & minor bug fixes",
          upgrading: "",
        },
      ],
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
        {
          type: "added",
          description: "Added Decode Field Images",
          upgrading: "",
        },
      ],
    },
    {
      version: "1.0.2",
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
