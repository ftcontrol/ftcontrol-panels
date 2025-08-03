import { PluginManager } from "ftc-panels"

export default class Manager extends PluginManager {
  GAMEPAD_KEY = "gamepad"

  override onInit(): void {
    this.socket.addMessageHandler("newGamepad", (data) => {
      this.state.update(this.GAMEPAD_KEY, data)
    })
  }

  override getNewVersion(): string {
    return ""
  }
}
