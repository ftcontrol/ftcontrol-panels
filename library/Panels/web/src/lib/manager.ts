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
  version: "1.0.1",
  pluginsCoreVersion: "1.1.41",
  author: "Lazar",
  manager: "",
  components: [],
  templates: [],
  includedPluginsIDs: [],
  changelog: [
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
          description: "Updated ftc-panels to 1.1.40",
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
