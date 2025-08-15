import { PluginManager } from "ftc-panels"
import type { ChangeJson, GenericTypeJson } from "./types"

export default class Manager extends PluginManager {
  INITIAL_CONFIGURABLES_KEY = "initialConfigurables"
  CONFIGURABLES_KEY = "configurables"

  override onInit(): void {
    this.socket.addMessageHandler("initialConfigurables", (data) => {
      this.state.update(this.INITIAL_CONFIGURABLES_KEY, data)
    })
    this.socket.addMessageHandler("configurables", (data) => {
      this.state.update(this.CONFIGURABLES_KEY, data)
    })
    this.socket.addMessageHandler("newConfigurables", (data: ChangeJson[]) => {
      this.state.mutate(
        this.CONFIGURABLES_KEY,
        (currentValue: Record<string, GenericTypeJson[]>) => {
          function apply(itemList: GenericTypeJson[], change: ChangeJson) {
            for (const item of itemList) {
              if (item.id === change.id) {
                console.log("Got change for", item)
                item.value = change.newValueString
              }

              if (item.customValues?.length) {
                apply(item.customValues, change)
              }
            }
          }

          data.forEach((change: ChangeJson) => {
            for (const [, itemList] of Object.entries(currentValue)) {
              apply(itemList, change)
            }
          })

          return currentValue
        }
      )
    })
  }

  static async getNewVersion(): string {
    return ""
  }
}
