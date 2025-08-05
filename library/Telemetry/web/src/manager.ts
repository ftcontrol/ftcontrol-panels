import { PluginManager } from "ftc-panels"

export type OpModeStatus = "INIT" | "RUNNING" | "STOPPED"

export default class Manager extends PluginManager {
  PACKETS_KEY = "packets"

  override onInit(): void {
    this.state.update(this.PACKETS_KEY, [])
    this.socket.addMessageHandler("telemetryPacket", (data) => {
      this.state.update(this.PACKETS_KEY, data)
    })
  }

  static async getNewVersion(): string {
    return ""
  }
}
