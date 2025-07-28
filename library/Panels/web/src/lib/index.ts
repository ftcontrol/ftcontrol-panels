import { GlobalState } from "$lib/state.svelte"
import { NotificationsManager } from "./notifications.svelte"

export const global = new GlobalState()

export const notifications = new NotificationsManager()
