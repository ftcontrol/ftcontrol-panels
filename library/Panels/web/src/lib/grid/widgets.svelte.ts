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
    {
      id: "test2",
      x: 1,
      y: 1,
      w: 1,
      h: 1,
      minW: 1,
      maxW: 6,
      minH: 1,
      maxH: 6,
    },
  ])

  WIDTH = 200
  HEIGHT = 100
  MAX_GRID_WIDTH = 6
  MAX_GRID_HEIGHT = 6

  isColliding(a: Widget, b: Widget): boolean {
    return !(
      a.x + a.w <= b.x ||
      a.x >= b.x + b.w ||
      a.y + a.h <= b.y ||
      a.y >= b.y + b.h
    )
  }

  resolveCollisions(moved: Widget, widgets: Widget[]): Widget[] {
    const updated = widgets.map((w) => ({ ...w }))
    const queue = [moved]

    while (queue.length > 0) {
      const current = queue.pop()!
      for (let widget of updated) {
        if (widget.id !== current.id && this.isColliding(current, widget)) {
          widget.y = current.y + current.h
          queue.push(widget)
        }
      }
    }

    if ([moved, ...updated].some((w) => this.isOutOfBounds(w))) {
      return this.widgets
    }

    return updated
  }

  isOutOfBounds(widget: Widget): boolean {
    return (
      widget.x < 0 ||
      widget.y < 0 ||
      widget.x + widget.w > this.MAX_GRID_WIDTH ||
      widget.y + widget.h > this.MAX_GRID_HEIGHT
    )
  }

  moveWidget(id: string, xMove: number, yMove: number) {
    const dx = Math.round(xMove / this.WIDTH)
    const dy = Math.round(yMove / this.HEIGHT)

    const updated = this.widgets.map((w) =>
      w.id === id ? { ...w, x: w.x + dx, y: w.y + dy } : { ...w }
    )
    const moved = updated.find((w) => w.id === id)!
    this.widgets = this.resolveCollisions(moved, updated)
  }

  resizeWidget(id: string, xOffset: number, yOffset: number) {
    const dw = Math.round(xOffset / this.WIDTH)
    const dh = Math.round(yOffset / this.HEIGHT)

    const updated = this.widgets.map((w) =>
      w.id === id
        ? {
            ...w,
            w: clamp(w.minW, w.w + dw, w.maxW),
            h: clamp(w.minH, w.h + dh, w.maxH),
          }
        : { ...w }
    )
    const resized = updated.find((w) => w.id === id)!
    this.widgets = this.resolveCollisions(resized, updated)
  }
}

export const manager = new Manager()

function clamp(min: number, value: number, max: number) {
  return Math.min(Math.max(min, value), max)
}
