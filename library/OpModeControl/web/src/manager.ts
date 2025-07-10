import { PluginManager } from "ftc-panels/src/core/socket/manager"
import { type OpMode } from "./types"

export default class Manager extends PluginManager {
  OPMODES_KEY = "opModes"

  override onInit(): void {
    console.log("Ran init of opmodecontrol manager")
    this.socket.addMessageHandler("opModesList", (data) => {
      this.state.update(this.OPMODES_KEY, data.opModes)
    })
  }
}
