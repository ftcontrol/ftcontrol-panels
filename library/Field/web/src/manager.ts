import { PluginManager } from "ftc-panels"
import { emptyPacket } from "./types"

export default class Manager extends PluginManager {
  PACKETS_KEY = "packets"

  override onInit(): void {
    this.state.update(this.PACKETS_KEY, emptyPacket)
    this.socket.addMessageHandler("canvasPacket", (data) => {
      this.state.update(this.PACKETS_KEY, data)
    })
  }
}
