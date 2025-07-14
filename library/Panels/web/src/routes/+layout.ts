import { browser } from "$app/environment"
import { global } from "$lib"

export async function load() {
  if (!browser) return
  global.init()
  setTimeout(() => {
    console.log(
      global.plugins.map((it) => it.details.widgets.map((w) => w.name))
    )
  }, 1000)
}
