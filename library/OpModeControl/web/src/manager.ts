import { PluginManager } from "ftc-panels/src/core/socket/manager"

type OpModeStatus = "init" | "running" | "stopped"

export default class Manager extends PluginManager {
  OPMODES_KEY = "opModes"
  ACTIVE_OPMODE_NAME_KEY = "activeOpModeName"
  ACTIVE_OPMODE_STATUS_KEY = "activeOpModeStatus"

  override onInit(): void {
    this.state.update(this.ACTIVE_OPMODE_NAME_KEY, "$Stop$Robot$")
    this.state.update(this.ACTIVE_OPMODE_STATUS_KEY, "stopped")
    this.socket.addMessageHandler("opModesList", (data) => {
      this.state.update(this.OPMODES_KEY, data.opModes)
    })
  }
}
