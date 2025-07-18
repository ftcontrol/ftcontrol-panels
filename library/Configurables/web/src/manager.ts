import { PluginManager } from "ftc-panels"
import type { ChangeJson, GenericTypeJson } from "./types"

export type OpModeStatus = "INIT" | "RUNNING" | "STOPPED"

export default class Manager extends PluginManager {
  INITIAL_CONFIGURABLES_KEY = "initialConfigurables"

  override onInit(): void {
    this.socket.addMessageHandler("initialConfigurables", (data) => {
      this.state.update(this.INITIAL_CONFIGURABLES_KEY, data)
    })
    this.socket.addMessageHandler("newConfigurables", (data: ChangeJson[]) => {
      this.state.mutate(
        this.INITIAL_CONFIGURABLES_KEY,
        (currentValue: Record<string, GenericTypeJson[]>) => {
          data.forEach((change: ChangeJson) => {
            for (const [className, itemList] of Object.entries(currentValue)) {
              for (const item of itemList) {
                if (item.id === change.id) {
                  console.log("Got change", item)
                  item.value = change.newValueString
                }
              }
            }
          })
          return currentValue
        }
      )
    })
  }
}
