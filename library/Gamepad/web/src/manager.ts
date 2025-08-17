import { PluginManager } from "ftc-panels"

export default class Manager extends PluginManager {
  FIRST_GAMEPAD_KEY = "gamepad0"
  SECOND_GAMEPAD_KEY = "gamepad1"

  plugIn(e: GamepadEvent) {
    console.log("Gamepad connected:", e.gamepad)
    this.notifications.add(`Gamepad connected: ${e.gamepad.id}`)
  }

  unPlug(e: GamepadEvent) {
    console.log("Gamepad disconnected:", e.gamepad)
    this.notifications.add(`Gamepad disconnected: ${e.gamepad.id}`)
  }

  override onInit(): void {
    this.socket.addMessageHandler("newGamepad0", (data) => {
      this.state.update(this.FIRST_GAMEPAD_KEY, data)
    })
    this.socket.addMessageHandler("newGamepad1", (data) => {
      this.state.update(this.SECOND_GAMEPAD_KEY, data)
    })

    window.removeEventListener("gamepadconnected", this.plugIn)
    window.removeEventListener("gamepaddisconnected", this.unPlug)

    window.addEventListener("gamepadconnected", this.plugIn)
    window.addEventListener("gamepaddisconnected", this.unPlug)
  }

  static async getNewVersion(): Promise<string> {
    return ""
  }
}
