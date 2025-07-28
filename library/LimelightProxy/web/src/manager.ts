import { PluginManager } from "ftc-panels"

export default class Manager extends PluginManager {
  override onInit(): void {}

  override hasNewVersion(currentVersion: string): boolean {
    return false
  }
}
