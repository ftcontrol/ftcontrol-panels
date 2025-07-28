import { PluginManager } from "ftc-panels"
import { emptyPacket } from "./types"

export default class Manager extends PluginManager {
  PACKETS_KEY = "packets"
  IMAGES_KEY = "images"

  override onInit(): void {
    this.state.update(this.PACKETS_KEY, emptyPacket)
    this.state.update(this.IMAGES_KEY, {})
    this.socket.addMessageHandler("canvasPacket", (data) => {
      this.state.update(this.PACKETS_KEY, data)
    })
    this.socket.addMessageHandler("canvasImages", (data) => {
      this.state.update(this.IMAGES_KEY, data)
    })
  }

  override hasNewVersion(currentVersion: string): boolean {
    return false
  }
}
