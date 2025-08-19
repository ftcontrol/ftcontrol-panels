import { simpleModules } from "$lib/simpleData"

export async function load({ params }) {
  return {
    page: params.page,
  }
}

export function entries(): { id: string; page: string }[] {
  return simpleModules.flatMap((module) => {
    return module.components
      .filter((it) => it.type == "docs")
      .map((page) => {
        return {
          id: module.id,
          page: page.id,
        }
      })
  })
}
