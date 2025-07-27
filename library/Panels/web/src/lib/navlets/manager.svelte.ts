import { global } from "$lib"

export type Navlet = {
  pluginID: string
  navletID: string
}

class Manager {
  navlets: Navlet[] = $state([
    {
      pluginID: "com.bylazar.battery",
      navletID: "Battery",
    },
  ])

  addNavlet() {
    this.navlets.push({
      pluginID: "",
      navletID: "",
    })
  }

  removeNavlet(index: number) {
    this.navlets.splice(index, 1)
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
