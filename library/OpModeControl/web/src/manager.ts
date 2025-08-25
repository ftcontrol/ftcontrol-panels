import { PluginManager } from "ftc-panels"

export type OpModeStatus = "INIT" | "RUNNING" | "STOPPED"

export default class Manager extends PluginManager {
  OPMODES_KEY = "opModes"
  ACTIVE_OPMODE_KEY = "activeOpMode"
  ACTIVE_OPMODE_STATUS_KEY = "activeOpModeStatus"
  ACTIVE_OPMODE_START_TIMESTAMP_KEY = "activeOpModeStartTimestamp"
  ACTIVE_OPMODE_START_DELTA_MS_KEY = "activeOpModeDeltaMS"

  override onInit(): void {
    this.state.update(this.OPMODES_KEY, [])
    this.state.update(this.ACTIVE_OPMODE_STATUS_KEY, "stopped")
    this.state.update(this.ACTIVE_OPMODE_START_TIMESTAMP_KEY, null)
    this.state.update(this.ACTIVE_OPMODE_START_DELTA_MS_KEY, null)
    this.socket.addMessageHandler("opModesList", (data) => {
      this.state.update(this.OPMODES_KEY, data.opModes)
    })
    this.socket.addMessageHandler("activeOpMode", (data) => {
      this.state.update(this.ACTIVE_OPMODE_KEY, data.opMode)
      this.state.update(this.ACTIVE_OPMODE_STATUS_KEY, data.status)
      this.state.update(
        this.ACTIVE_OPMODE_START_TIMESTAMP_KEY,
        data.startTimestamp
      )
    })
    this.socket.addMessageHandler("opModesList", (data) => {
      this.state.update(this.OPMODES_KEY, data.opModes)
    })
    this.socket.addMessageHandler("deltaMs", (data) => {
      this.state.update(this.ACTIVE_OPMODE_START_DELTA_MS_KEY, data)
    })
  }

  static async getNewVersion(): Promise<string> {
    return ""
  }
}
