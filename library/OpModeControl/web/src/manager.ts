import { PluginManager } from "ftc-panels"

export type OpModeStatus = "INIT" | "RUNNING" | "STOPPED"

export default class Manager extends PluginManager {
  OPMODES_KEY = "opModes"
  ACTIVE_OPMODE_KEY = "activeOpMode"
  ACTIVE_OPMODE_STATUS_KEY = "activeOpModeStatus"

  override onInit(): void {
    this.state.update(this.ACTIVE_OPMODE_STATUS_KEY, "stopped")
    this.socket.addMessageHandler("opModesList", (data) => {
      this.state.update(this.OPMODES_KEY, data.opModes)
    })
    this.socket.addMessageHandler("activeOpMode", (data) => {
      this.state.update(this.ACTIVE_OPMODE_KEY, data.opMode)
      this.state.update(this.ACTIVE_OPMODE_STATUS_KEY, data.status)
    })
  }

  override getNewVersion(): string {
    return ""
  }
}
