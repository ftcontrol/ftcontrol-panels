export type Notification = {
  timestamp: number
  text: string
  actions: Action[]
}
type Action = {
  text: string
  task: () => void
}
export class NotificationsManager {
  data = $state<Notification[]>([])

  add(text: string) {
    this.data.push({
      timestamp: Date.now(),
      text,
      actions: [],
    })
  }

  addAction(text: string, actions: Action[]) {
    this.data.push({
      timestamp: Date.now(),
      text,
      actions: actions,
    })
  }
}
