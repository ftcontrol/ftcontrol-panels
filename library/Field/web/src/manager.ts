import { PluginManager } from "ftc-panels"
import { emptyPacket, type Packet } from "./types"

type ImagesMap = Record<string, string>

export default class Manager extends PluginManager {
  readonly PACKETS_KEY = "packets"
  readonly IMAGES_KEY = "images"

  override onInit(): void {
    this.state.update(this.PACKETS_KEY, emptyPacket)
    this.state.update(this.IMAGES_KEY, {})

    this.socket.addMessageHandler("canvasPacket", (data: Packet) => {
      this.state.update(this.PACKETS_KEY, data)
    })

    this.socket.addMessageHandler("canvasImages", (data: ImagesMap) => {
      this.state.update(this.IMAGES_KEY, data)
    })
  }

  get packets(): Packet {
    return this.state.get(this.PACKETS_KEY)
  }
  get images(): ImagesMap {
    return this.state.get(this.IMAGES_KEY)
  }

  static async getNewVersion(): Promise<string> {
    return ""
  }
}
