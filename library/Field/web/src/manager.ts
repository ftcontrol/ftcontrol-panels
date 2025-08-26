import { PluginManager, getLazarPackageLatestVersion } from "ftc-panels"
import { config } from "../config"

import {
  emptyPacket,
  emptyPreset,
  type FieldPresetParams,
  type Packet,
} from "./types"

type ImagesMap = Record<string, string>

export default class Manager extends PluginManager {
  readonly PACKETS_KEY = "packets"
  readonly IMAGES_KEY = "images"
  readonly PRESETS_KEY = "presets"

  override onInit(): void {
    this.state.update(this.PACKETS_KEY, emptyPacket)
    this.state.update(this.IMAGES_KEY, {})

    this.socket.addMessageHandler("canvasPacket", (data: Packet) => {
      this.state.update(this.PACKETS_KEY, data)
    })

    this.socket.addMessageHandler("canvasImages", (data: ImagesMap) => {
      this.state.update(this.IMAGES_KEY, data)
    })

    this.socket.addMessageHandler(
      "canvasPresets",
      (data: FieldPresetParams[]) => {
        this.state.update(this.PRESETS_KEY, [...data, emptyPreset])
      }
    )
  }

  get packets(): Packet {
    return this.state.get(this.PACKETS_KEY)
  }
  get images(): ImagesMap {
    return this.state.get(this.IMAGES_KEY)
  }

  get presets(): FieldPresetParams[] {
    return this.state.get(this.PRESETS_KEY)
  }

  static async getNewVersion(): Promise<string> {
    return await getLazarPackageLatestVersion(config.id)
  }
}
