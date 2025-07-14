export type Widget = {
  selected: number
  isMoving: boolean
  id: string
  widgets: Panel[]
  x: number
  y: number
  w: number
  h: number
  minW: number
  maxW: number
  minH: number
  maxH: number
}

export type Panel = {
  isMoving: boolean
  pluginID: string
  widgetID: string
}

class Manager {
  widgets: Widget[] = $state([
    {
      selected: 0,
      isMoving: false,
      id: Math.random().toString(),
      widgets: [
        {
          isMoving: false,
          pluginID: "com.bylazar.opmodecontrol",
          widgetID: "OpModes Control",
        },
        {
          isMoving: false,
          pluginID: "com.bylazar.exampleplugin",
          widgetID: "Counter",
        },
        {
          isMoving: false,
          pluginID: "com.bylazar.configurables",
          widgetID: "Configurables",
        },
      ],
      x: 0,
      y: 0,
      w: 3,
      h: 2,
      minW: 1,
      maxW: 6,
      minH: 1,
      maxH: 60,
    },
    {
      selected: 0,
      isMoving: false,
      id: Math.random().toString(),
      widgets: [
        {
          isMoving: false,
          pluginID: "com.bylazar.telemetry",
          widgetID: "Telemetry",
        },
      ],
      x: 1,
      y: 2,
      w: 1,
      h: 1,
      minW: 1,
      maxW: 6,
      minH: 1,
      maxH: 60,
    },
  ])

  possibleWidgets = $state(this.widgets)

  isMoving = $derived.by(() => {
    for (const w of this.widgets) {
      if (w.isMoving) return true
    }
    return false
  })

  WIDTH = $state(200)
  HEIGHT = $state(100)
  MAX_GRID_WIDTH = $state(8)
  MAX_GRID_HEIGHT = $state(6)

  tabIndex = $state(0)
  tabWidgetID = $state("")

  isColliding(a: Widget, b: Widget): boolean {
    return !(
      a.x + a.w <= b.x ||
      a.x >= b.x + b.w ||
      a.y + a.h <= b.y ||
      a.y >= b.y + b.h
    )
  }

  getWidget(x: number, y: number, widgets: Widget[]): Widget | undefined {
    return widgets.find(
      (w) => x >= w.x && x < w.x + w.w && y >= w.y && y < w.y + w.h
    )
  }

  resolveCollisions(moved: Widget, widgets: Widget[]) {
    const updated = widgets.map((w) => ({ ...w }))
    const queue = [moved]

    while (queue.length > 0) {
      const current = queue.pop()!

      for (const widget of updated) {
        if (widget.id === current.id) continue
        if (!this.isColliding(current, widget)) continue

        const directions = [
          { dx: 0, dy: current.y + current.h }, // down
          { dx: current.x + current.w, dy: current.y }, // right
          { dx: current.x - widget.w, dy: current.y }, // left
        ]

        let movedSuccessfully = false
        for (const dir of directions) {
          const trial = { ...widget, x: dir.dx, y: dir.dy }
          if (
            !this.isOutOfBounds(trial) &&
            !this.willCollide(trial, updated, widget.id)
          ) {
            widget.x = trial.x
            widget.y = trial.y
            queue.push(widget)
            movedSuccessfully = true
            break
          }
        }

        if (!movedSuccessfully) {
          widget.y = current.y + current.h
          queue.push(widget)
        }
      }
    }

    if ([moved, ...updated].some((w) => this.isOutOfBounds(w)))
      return this.widgets
    return updated
  }

  willCollide(test: Widget, widgets: Widget[], excludeId: string): boolean {
    return widgets.some((w) => w.id !== excludeId && this.isColliding(test, w))
  }

  isOutOfBounds(widget: Widget): boolean {
    return (
      widget.x < 0 ||
      widget.y < 0 ||
      widget.x + widget.w > this.MAX_GRID_WIDTH ||
      widget.y + widget.h > this.MAX_GRID_HEIGHT
    )
  }

  updateMove(id: string, xMove: number, yMove: number) {
    this.possibleWidgets = this.moveWidget(id, xMove, yMove, this.widgets)
  }

  updateResize(id: string, xOffset: number, yOffset: number) {
    this.possibleWidgets = this.resizeWidget(id, xOffset, yOffset, this.widgets)
  }

  finishMoveWidget(id: string, xMove: number, yMove: number) {
    this.widgets = this.moveWidget(id, xMove, yMove, this.widgets)
  }
  finishResizeWidget(id: string, xOffset: number, yOffset: number) {
    this.widgets = this.resizeWidget(id, xOffset, yOffset, this.widgets)
  }

  moveWidget(
    id: string,
    xMove: number,
    yMove: number,
    widgets: Widget[]
  ): Widget[] {
    const dx = Math.round(xMove / this.WIDTH)
    const dy = Math.round(yMove / this.HEIGHT)

    const updated = widgets.map((w) =>
      w.id === id ? { ...w, x: w.x + dx, y: w.y + dy } : { ...w }
    )
    const moved = updated.find((w) => w.id === id)!
    return this.resolveCollisions(moved, updated)
  }

  resizeWidget(
    id: string,
    xOffset: number,
    yOffset: number,
    widgets: Widget[]
  ) {
    const dw = Math.round(xOffset / this.WIDTH)
    const dh = Math.round(yOffset / this.HEIGHT)

    const updated = widgets.map((w) =>
      w.id === id
        ? {
            ...w,
            w: clamp(w.minW, w.w + dw, w.maxW),
            h: clamp(w.minH, w.h + dh, w.maxH),
          }
        : { ...w }
    )
    const resized = updated.find((w) => w.id === id)!
    return this.resolveCollisions(resized, updated)
  }
}

export const manager = new Manager()

function clamp(min: number, value: number, max: number) {
  return Math.min(Math.max(min, value), max)
}
