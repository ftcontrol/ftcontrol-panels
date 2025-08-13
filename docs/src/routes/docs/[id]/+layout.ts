import { modules } from "$lib/data.js"
import type { EntryGenerator } from "./$types"

export async function load({ params }) {
  return {
    id: params.id,
  }
}