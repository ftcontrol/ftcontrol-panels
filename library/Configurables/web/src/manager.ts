import { PluginManager } from "ftc-panels"

export type OpModeStatus = "INIT" | "RUNNING" | "STOPPED"

export default class Manager extends PluginManager {
  INITIAL_CONFIGURABLES_KEY = "initialConfigurables"

  override onInit(): void {
    this.socket.addMessageHandler("initialConfigurables", (data) => {
      this.state.update(this.INITIAL_CONFIGURABLES_KEY, data)
    })
  }
}
