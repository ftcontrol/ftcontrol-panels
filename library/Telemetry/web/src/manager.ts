import { PluginManager, getLazarPackageLatestVersion } from "ftc-panels"
import { config } from "../config"

export default class Manager extends PluginManager {
  PACKETS_KEY = "packets"

  override onInit(): void {
    this.state.update(this.PACKETS_KEY, [])
    this.socket.addMessageHandler("telemetryPacket", (data) => {
      this.state.update(this.PACKETS_KEY, data)
    })
  }

  static async getNewVersion(): Promise<string> {
    return await getLazarPackageLatestVersion(config.id)
  }
}
