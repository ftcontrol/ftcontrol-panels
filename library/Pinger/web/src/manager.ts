import { PluginManager, getLazarPackageLatestVersion } from "ftc-panels"
import { config } from "../config"

export default class Manager extends PluginManager {
  LAST_PING_KEY = "ping"
  RAW_PING_KEY = "ping_raw"

  SMOOTHING_ALPHA = 0.2

  override onInit(): void {
    this.state.update(this.LAST_PING_KEY, 0)
    this.state.update(this.RAW_PING_KEY, 0)
    this.socket.addMessageHandler("answer", (data: { timestamp: number }) => {
      if (!data) return

      const now = Date.now()

      const rtt = Math.max(0, now - data.timestamp)
      const oneWayMs = Math.round(rtt / 4)

      const prev = this.state.get(this.LAST_PING_KEY)
      const smooth = Math.round(prev + this.SMOOTHING_ALPHA * (oneWayMs - prev))

      this.state.update(this.RAW_PING_KEY, oneWayMs)
      this.state.update(this.LAST_PING_KEY, smooth)
    })
  }

  sendRequest() {
    this.socket.sendMessage("request", {
      timestamp: Date.now(),
    })
  }

  static async getNewVersion(): Promise<string> {
    return await getLazarPackageLatestVersion(config.id)
  }
}
