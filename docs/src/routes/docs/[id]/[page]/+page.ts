import { modules } from "$lib/data"

export async function load({ params }) {
  return {
    page: params.page,
  }
}

export function entries(): { id: string; page: string }[] {
  return modules.flatMap((module) => {
    return module.docs.chapters.map((page) => {
      return {
        id: module.id,
        page: page.name
      }
    })
  })
}