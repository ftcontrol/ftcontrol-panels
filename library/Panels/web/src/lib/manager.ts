import {
  defaultSettings,
  getLazarPackageLatestVersion,
  type PluginConfig,
  type PluginSettings,
} from "ftc-panels"

export const panelsConfig: PluginConfig = {
  id: "com.bylazar.panels",
  name: "Panels",
  letterName: "P",
  description: "Empty Panels Installation ready to be extended",
  websiteURL: "https://panels.bylazar.com",
  mavenURL: "https://mymaven.bylazar.com/releases",
  packageString: "com.bylazar:panels:<VERSION>",
  version: "1.0.6",
  pluginsCoreVersion: "1.1.44",
  author: "Lazar",
  manager: "",
  components: [],
  templates: [],
  includedPluginsIDs: [],
  changelog: [
    {
      version: "1.0.6",
      release_date: "3.10.2026",
      changes: [{ type: "other", description: "Built against FTC SDK 12.0.0 with pinned frontend core 1.1.44", upgrading: "Update the FTC SDK to 12.0.0 and use plugins compatible with frontend core 1.1.44." }],
    },
    {
      version: "1.0.5",
      release_date: "12.12.2025",
      changes: [
        {
          type: "other",
          description: "Reduced logs amount",
          upgrading: "",
        },
      ],
    },
    {
      version: "1.0.4",
      release_date: "1.11.2025",
      changes: [
        {
          type: "added",
          description:
            "Method to get connected clients count (used by CameraStream Plugin)",
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
      release_date: "27.08.2025",
      changes: [
        {
          type: "docs",
          description: "Added a Core Plugins Section & Fixed UI on Safari",
          upgrading: "",
        },
      ],
    },
    {
      version: "1.0.1",
      release_date: "27.08.2025",
      changes: [
        {
          type: "fixed",
          description: "Made Panels widgets mobile responsive",
          upgrading: "",
        },
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

export class PanelsManager {
  config: PluginConfig = panelsConfig
  settings: PluginSettings = defaultSettings

  onInit(): void {}

  static async getNewVersion(): Promise<string> {
    return await getLazarPackageLatestVersion(panelsConfig.id)
  }
}
