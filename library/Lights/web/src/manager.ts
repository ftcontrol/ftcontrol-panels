import { PluginManager, getLazarPackageLatestVersion } from "ftc-panels"
import { config } from "../config"

export default class Manager extends PluginManager {
  LIGHTS_KEY = "lights"

  override onInit(): void {
    this.state.update(this.LIGHTS_KEY, [])
    this.socket.addMessageHandler("lightsPacket", (data) => {
      this.state.update(this.LIGHTS_KEY, data)
    })
  }

  static async getNewVersion(): Promise<string> {
    return await getLazarPackageLatestVersion(config.id)
  }
}
