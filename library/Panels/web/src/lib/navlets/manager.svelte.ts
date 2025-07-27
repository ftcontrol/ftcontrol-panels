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
}

export const manager = new Manager()
