import {PluginManager} from "ftc-panels"

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

        function plugIn(e: GamepadEvent) {
            console.log("Gamepad connected:", e.gamepad)
            notifications.add(`Gamepad connected: ${e.gamepad.id}`)
        }

        function unPlug(e: GamepadEvent) {
            console.log("Gamepad disconnected:", e.gamepad)
            notifications.add(`Gamepad disconnected: ${e.gamepad.id}`)
        }

        window.removeEventListener("gamepadconnected", plugIn)
        window.removeEventListener("gamepaddisconnected", unPlug)

        window.addEventListener("gamepadconnected", plugIn)
        window.addEventListener("gamepaddisconnected", unPlug)
    }

    static getNewVersion(): string {
        return ""
    }
}
