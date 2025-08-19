import { PluginManager } from "ftc-panels"

export default class Manager extends PluginManager {
  GRAPH_PACKET_KEY = "graphPacket"
  override onInit(): void {}

  static async getNewVersion(): Promise<string> {
    return ""
  }
}
