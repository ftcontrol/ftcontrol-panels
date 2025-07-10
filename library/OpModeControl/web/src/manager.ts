import { PluginManager } from "ftc-panels/src/core/socket/manager"
import { type OpMode } from "./types"

export default class Manager extends PluginManager {
  opModes: OpMode[] = []
  callbacks: ((value: OpMode[]) => void)[] = []

  override onInit(): void {
    console.log("Ran init of opmodecontrol manager")
    this.socket.addMessageHandler("opModesList", (data) => {
      this.opModes = data.opModes
      this.callbacks.forEach((it) => it(this.opModes))
    })
  }

  onOpModesChange(callback: (value: OpMode[]) => void) {
    this.callbacks.push(callback)
    callback(this.opModes)
  }
}
