import type { PluginConfig } from "ftc-panels"

export const panelsConfig: PluginConfig = {
  id: "com.bylazar.panels",
  name: "Panels",
  letterName: "P",
  description: "Panels",
  websiteURL: "https://panels.bylazar.com",
  version: "0.0.23",
  pluginsCoreVersion: "1.1.22",
  author: "Lazar",
  manager: "",
  components: [],
  templates: [],
  includedPluginsIDs: [],
}

export class PanelsManager {
  config: PluginConfig = panelsConfig

  onInit(): void {}

  static async getNewVersion(): Promise<string> {
    try {
      const response = await fetch(
        `https://raw.githubusercontent.com/lazarcloud/ftcontrol-maven/refs/heads/main/dev/com/bylazar/panels/maven-metadata.xml`
      )
      if (!response.ok) throw new Error(`HTTP ${response.status}`)

      const xmlText = await response.text()
      const parser = new DOMParser()
      const xmlDoc = parser.parseFromString(xmlText, "application/xml")

      const latestVersion = xmlDoc.querySelector("latest")?.textContent

      return latestVersion || ""
    } catch (error) {
      return ""
    }
  }
}
