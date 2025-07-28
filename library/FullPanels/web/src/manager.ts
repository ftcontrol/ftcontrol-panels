import { PluginManager } from "ftc-panels"

export default class Manager extends PluginManager {
  override onInit(): void {}

  async getLatestVersion(): Promise<string> {
    try {
      const response = await fetch(
        `https://raw.githubusercontent.com/lazarcloud/ftcontrol-maven/refs/heads/main/dev/com/bylazar/fullpanels/maven-metadata.xml`
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

  override async hasNewVersion(currentVersion: string): Promise<boolean> {
    const version = await this.getLatestVersion()
    if (version == "") return false
    return version != currentVersion
  }
}
