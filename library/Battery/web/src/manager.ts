import { PluginManager } from "ftc-panels"

export default class Manager extends PluginManager {
  BATTERY_KEY = "battery"
  override onInit(): void {
    this.socket.addMessageHandler("battery", (data) => {
      this.state.update(this.BATTERY_KEY, data)
    })
  }

  static async getNewVersion(): Promise<string> {
    return ""
  }
}
