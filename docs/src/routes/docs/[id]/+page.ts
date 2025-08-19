import { simpleModules } from "$lib/simpleData"

export function entries(): { id: string }[] {
  return simpleModules.map((it) => {
    return {
      id: it.id,
    }
  })
}
