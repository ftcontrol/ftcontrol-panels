import { PluginManager } from "ftc-panels/src/core/socket/manager"

export default class Manager extends PluginManager {
  something: string = "lazar"

  override onInit(): void {
    console.log("Ran init of opmodecontrol manager")
    this.socket.addMessageHandler("opModesList", (data) => {
      console.log("Data form plugin", data)
    })
  }
}
