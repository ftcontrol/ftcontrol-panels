import { modules } from "$lib/data"

export function entries(): { id: string }[] {
  return modules.map((it) => {
    return {
      id: it.id,
    }
  })
}
