export type Widget = {
  id: string
  x: number
  y: number
  w: number
  h: number
  minW: number
  maxW: number
  minH: number
  maxH: number
}

class Manager {
  widgets: Widget[] = $state([
    {
      id: "test1",
      x: 0,
      y: 0,
      w: 1,
      h: 1,
      minW: 1,
      maxW: 6,
      minH: 1,
      maxH: 6,
    },
  ])
}

export const manager = new Manager()

export const WIDTH = 200
export const HEIGHT = 100

export function isColliding(a: Widget, b: Widget): boolean {
  return !(
    a.x + a.w <= b.x ||
    a.x >= b.x + b.w ||
    a.y + a.h <= b.y ||
    a.y >= b.y + b.h
  )
}

export function resolveCollisions(moved: Widget, widgets: Widget[]): Widget[] {
  const updated = widgets.map((w) => ({ ...w }))
  const queue = [moved]

  while (queue.length > 0) {
    const current = queue.pop()!
    for (let widget of updated) {
      if (widget.id !== current.id && isColliding(current, widget)) {
        widget.y = current.y + current.h
        queue.push(widget)
      }
    }
  }

  return updated
}

export function moveWidget(
  id: string,
  newX: number,
  newY: number,
  widgets: Widget[]
): Widget[] {
  const updated = widgets.map((w) =>
    w.id === id ? { ...w, x: newX, y: newY } : { ...w }
  )
  const moved = updated.find((w) => w.id === id)!
  return resolveCollisions(moved, updated)
}

export function resizeWidget(
  id: string,
  newW: number,
  newH: number,
  widgets: Widget[]
): Widget[] {
  const updated = widgets.map((w) =>
    w.id === id ? { ...w, w: newW, h: newH } : { ...w }
  )
  const resized = updated.find((w) => w.id === id)!
  return resolveCollisions(resized, updated)
}
