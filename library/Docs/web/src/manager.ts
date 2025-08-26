import { PluginManager, getLazarPackageLatestVersion } from "ftc-panels"
import { config } from "../config"

export default class Manager extends PluginManager {
  override onInit(): void {}

  static async getNewVersion(): Promise<string> {
    return await getLazarPackageLatestVersion(config.id)
  }
}
