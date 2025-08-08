import { PluginManager } from "ftc-panels"

export default class Manager extends PluginManager {
  FIRST_GAMEPAD_KEY = "gamepad0"
  SECOND_GAMEPAD_KEY = "gamepad1"

  override onInit(): void {
    this.socket.addMessageHandler("newGamepad0", (data) => {
      this.state.update(this.FIRST_GAMEPAD_KEY, data)
    })
    this.socket.addMessageHandler("newGamepad1", (data) => {
      this.state.update(this.SECOND_GAMEPAD_KEY, data)
    })
  }

  static getNewVersion(): string {
    return ""
  }
}
