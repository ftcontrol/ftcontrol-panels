import { PluginManager } from "ftc-panels"

export default class Manager extends PluginManager {
  override onInit(): void {}

  static async getNewVersion(): string {
    return ""
  }
}
