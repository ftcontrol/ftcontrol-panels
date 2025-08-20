import { PluginManager } from "ftc-panels"
import type { GraphEntry } from "./types"

export default class Manager extends PluginManager {
  GRAPH_PACKET_KEY = "graphPacket"
  override onInit(): void {
    this.state.update(this.GRAPH_PACKET_KEY, [])
    this.socket.addMessageHandler("graphPacket", (data: GraphEntry[]) => {
      this.state.mutate(this.GRAPH_PACKET_KEY, (currentValue: GraphEntry[]) => {
        for (const item of data) {
          currentValue.push(item)
        }
        const now = Date.now()
        const ago = now - 1 * 60 * 1000
        return currentValue.filter((entry) => entry.timestamp >= ago)
      })
    })
  }

  static async getNewVersion(): Promise<string> {
    return ""
  }
}
