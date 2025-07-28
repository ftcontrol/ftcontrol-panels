import { PluginManager } from "ftc-panels"

function getURL(folder: string = "dev", jar: string): string {
  return `https://raw.githubusercontent.com/lazarcloud/ftcontrol-maven/refs/heads/main/${folder}/com/bylazar/${jar}/maven-metadata-local.xml`
}

async function getLatestVersion(
  folder: string = "dev",
  jar: string
): Promise<string> {
  try {
    const response = await fetch(getURL(folder, jar))
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

export default class Manager extends PluginManager {
  interval: ReturnType<typeof setInterval> | null = null
  override onInit(): void {
    if (this.interval !== null) {
      clearInterval(this.interval)
    }

    this.interval = setInterval(async () => {
      const version = await getLatestVersion("dev", "")
    }, 1000)
  }
}
