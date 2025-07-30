import { browser } from "$app/environment"
import { global } from "$lib"

export async function load() {
  if (!browser) return
  global.init()
}
