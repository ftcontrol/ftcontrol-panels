import { global } from "$lib"
import { getCookie, setCookie } from "ftc-panels"

export type Navlet = {
  pluginID: string
  navletID: string
}

class Manager {
  load() {
    var data = getCookie("navlets")
    if (data == null) data = "[]"
    this.navlets = JSON.parse(data) || []
  }

  navlets: Navlet[] = $state([])

  save() {
    setCookie("navlets", JSON.stringify(this.navlets))
  }

  addNavlet() {
    this.navlets.push({
      pluginID: "",
      navletID: "",
    })
  }

  removeNavlet(index: number) {
    this.navlets.splice(index, 1)
    this.save()
  }

  isValid(pluginID: string, navletID: string) {
    const plugin = global.plugins.find((it) => it.details.id == pluginID)
    if (plugin == undefined) return false
    const navlet = plugin.details.navlets.find((it) => it.name == navletID)
    if (navlet == undefined) return false
    return true
  }
}

export const manager = new Manager()
