import { PluginManager, getLazarPackageLatestVersion } from "ftc-panels"
import { config } from "../config"
import { CodeFormatter } from "ftc-panels-docs"
import { mount } from "svelte"

export default class Manager extends PluginManager {
  override onInit(): void {
    mount(CodeFormatter, {
      target: document.createElement("div"),
    })
  }

  static async getNewVersion(): Promise<string> {
    return await getLazarPackageLatestVersion(config.id)
  }
}
