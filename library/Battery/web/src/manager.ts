import { PluginManager, getLazarPackageLatestVersion } from "ftc-panels"
import { config } from "../config"

export default class Manager extends PluginManager {
  BATTERY_KEY = "battery"
  override onInit(): void {
    this.state.update(this.BATTERY_KEY, -1)
    this.socket.addMessageHandler("battery", (data) => {
      this.state.update(this.BATTERY_KEY, data)
    })
  }

  static async getNewVersion(): Promise<string> {
    return await getLazarPackageLatestVersion(config.id)
  }
}
