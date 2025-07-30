import { goto } from "$app/navigation"
import { global } from "$lib"
import { getCookie, setCookie } from "ftc-panels"

export type Widget = {
  offset: {
    x: number
    y: number
  }
  move: {
    x: number
    y: number
  }
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

export type Preset = {
  selected: number
  data: {
    name: string
    widgets: Widget[]
    navlets: Navlet[]
  }[]
}

export type Navlet = {
  pluginID: string
  navletID: string
}

export class Manager {
  template = {
    selected: 0,
    data: [
      {
        name: "Default",
        widgets: [],
        navlets: [],
      },
    ],
  }
  enableInteractions = false
  updateGridSize(section: HTMLElement) {
    const bounding = section.getBoundingClientRect()
    const width = bounding.width
    const height = bounding.height

    this.WIDTH = width / this.MAX_GRID_WIDTH
    this.HEIGHT = height / this.MAX_GRID_HEIGHT
  }
  load(
    section: HTMLElement,
    enableInteractions: boolean = true,
    defaultTemplate: Preset | null = null
  ) {
    this.updateGridSize(section)
    if (defaultTemplate == null) {
      var data = getCookie("layout")
      if (data == null) data = JSON.stringify(this.template)
      this.presets = JSON.parse(data) || structuredClone(this.template)
    } else {
      this.presets = defaultTemplate
    }
    this.enableInteractions = enableInteractions

    this.widgets = [...this.presets.data[this.presets.selected].widgets]
    this.navlets = [...this.presets.data[this.presets.selected].navlets]
  }

  deletePreset(index: number) {
    if (!this.enableInteractions) return
    if (this.presets.data.length <= 1) return

    this.presets.data.splice(index, 1)

    if (this.presets.selected === index) {
      this.presets.selected = 0
    } else if (this.presets.selected > index) {
      this.presets.selected -= 1
    }

    this.widgets = [...this.presets.data[this.presets.selected].widgets]
    this.navlets = [...this.presets.data[this.presets.selected].navlets]

    this.save()
  }

  newPreset() {
    if (!this.enableInteractions) return
    this.presets.data.push(structuredClone(this.template.data[0]))
    this.presets.selected = this.presets.data.length - 1

    goto("/")

    this.widgets = [...this.presets.data[this.presets.selected].widgets]
    this.navlets = [...this.presets.data[this.presets.selected].navlets]

    this.save()
  }

  change(index: number) {
    if (!this.enableInteractions) return
    this.save()
    this.presets.selected = index
    this.widgets = [...this.presets.data[this.presets.selected].widgets]
    this.navlets = [...this.presets.data[this.presets.selected].navlets]
    this.save()
    goto("/")
  }

  save() {
    if (!this.enableInteractions) return
    while (this.presets.data.length <= this.presets.selected) {
      this.presets.data.push(this.template.data[0])
    }

    this.presets.data[this.presets.selected].widgets = [...this.widgets]
    this.presets.data[this.presets.selected].navlets = [...this.navlets]

    setCookie("layout", JSON.stringify(this.presets))
  }

  presets: Preset = $state(this.template)

  widgets: Widget[] = $state([])
  navlets: Navlet[] = $state([])

  addNavlet() {
    if (!this.enableInteractions) return
    this.navlets.push({
      pluginID: "",
      navletID: "",
    })
    this.save()
  }

  removeNavlet(index: number) {
    if (!this.enableInteractions) return
    this.navlets.splice(index, 1)
    this.save()
  }

  isValidNavlet(pluginID: string, navletID: string) {
    if (!this.enableInteractions) return
    const plugin = global.plugins.find((it) => it.details.id == pluginID)
    if (plugin == undefined) return false
    const navlet = plugin.details.navlets.find((it) => it.name == navletID)
    if (navlet == undefined) return false
    return true
  }

  possibleWidgets = $state(this.widgets)

  isMoving = $derived.by(() => {
    for (const w of this.widgets) {
      if (w.isMoving) return true
    }
    if (this.placeStart != null) return true
    return false
  })

  WIDTH = $state(200)
  HEIGHT = $state(100)
  MAX_GRID_WIDTH = $state(16)
  MAX_GRID_HEIGHT = $state(12)

  tabIndex = $state(0)
  tabWidgetID = $state("")
  tabName = $state("")

  isValid = $state(true)

  isColliding(a: Widget, b: Widget): boolean {
    return !(
      a.x + a.w <= b.x ||
      a.x >= b.x + b.w ||
      a.y + a.h <= b.y ||
      a.y >= b.y + b.h
    )
  }

  removeWidget(id: string) {
    if (!this.enableInteractions) return
    this.widgets = this.widgets.filter((it) => it.id != id)
    this.save()
  }

  getWidget(x: number, y: number, widgets: Widget[]): Widget | undefined {
    return widgets.find(
      (w) => x >= w.x && x < w.x + w.w && y >= w.y && y < w.y + w.h
    )
  }

  placeStart: { x: number; y: number } | null = $state(null)
  placeEnd: { x: number; y: number } | null = $state(null)
  place: { x: number; y: number; w: number; h: number } | null = $state(null)

  startPlace(x: number, y: number) {
    if (!this.enableInteractions) return
    console.log("Start", x, y)
    this.placeStart = { x, y }
    this.place = {
      x,
      y,
      w: 1,
      h: 1,
    }
    this.updatePlace(x, y)
  }
  updatePlace(uX: number, uY: number) {
    if (!this.enableInteractions) return
    if (this.placeStart == null) return
    console.log("Update", uX, uY)
    this.placeEnd = { x: uX, y: uY }

    let startX = this.placeStart.x
    let startY = this.placeStart.y
    let endX = this.placeEnd.x
    let endY = this.placeEnd.y

    let w = Math.abs(endX - startX) + 1
    let h = Math.abs(endY - startY) + 1

    let x = Math.min(startX, endX)
    let y = Math.min(startY, endY)

    this.place = { x, y, w, h }

    const dummyWidget = {
      offset: {
        x: 0,
        y: 0,
      },
      move: {
        x: 0,
        y: 0,
      },
      selected: 0,
      isMoving: false,
      id: Math.random().toString(),
      widgets: [],
      x: x,
      y: y,
      w: w,
      h: h,
      minW: 1,
      maxW: 60,
      minH: 1,
      maxH: 60,
    }
    this.possibleWidgets = this.resolveCollisions(dummyWidget, [
      dummyWidget,
      ...this.widgets,
    ])
  }
  endPlace(x: number, y: number) {
    if (!this.enableInteractions) return
    if (this.placeStart == null) return
    console.log("End", x, y)

    this.updatePlace(x, y)

    if (this.place == null) return

    const dummyWidget = {
      offset: {
        x: 0,
        y: 0,
      },
      move: {
        x: 0,
        y: 0,
      },
      selected: 0,
      isMoving: false,
      id: Math.random().toString(),
      widgets: [],
      x: this.place.x,
      y: this.place.y,
      w: this.place.w,
      h: this.place.h,
      minW: 1,
      maxW: 60,
      minH: 1,
      maxH: 60,
    }
    this.widgets = this.resolveCollisions(dummyWidget, [
      ...this.widgets,
      dummyWidget,
    ])

    this.placeStart = null
    this.placeEnd = null
    this.place = null
    this.save()
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
          { dx: widget.x, dy: current.y + current.h },
          { dx: current.x + current.w, dy: current.y },
          { dx: current.x - widget.w, dy: current.y },
          { dx: widget.x, dy: current.y - widget.h },
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

    if ([moved, ...updated].some((w) => this.isOutOfBounds(w))) {
      this.isValid = false
      return this.widgets
    }
    this.isValid = true
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

  exists(pluginID: string, widgetID: string): boolean {
    if (!global.isConnected) return true
    const plugin = global.plugins.find((it) => it.details.id == pluginID)
    if (plugin == undefined) return false
    const widget = plugin.details.widgets.find((it) => it.name == widgetID)
    if (widget == undefined) return false
    return true
  }

  updateMove(id: string) {
    if (!this.enableInteractions) return
    this.possibleWidgets = this.moveWidget(id, this.widgets)
  }

  updateResize(id: string) {
    if (!this.enableInteractions) return
    this.possibleWidgets = this.resizeWidget(id, this.widgets)
  }

  finishMoveWidget(id: string) {
    if (!this.enableInteractions) return
    this.widgets = this.moveWidget(id, this.widgets)
    this.save()
  }
  finishResizeWidget(id: string) {
    if (!this.enableInteractions) return
    this.widgets = this.resizeWidget(id, this.widgets)
    this.save()
  }

  getWidgetById(id: string, widgets: Widget[]): Widget | undefined {
    return widgets.find((it) => it.id == id)
  }

  moveWidget(id: string, widgets: Widget[]): Widget[] {
    const widget = this.getWidgetById(id, widgets)
    if (widget == undefined) return this.widgets
    const dx = Math.round(widget.move.x / this.WIDTH)
    const dy = Math.round(widget.move.y / this.HEIGHT)

    const updated = widgets.map((w) =>
      w.id === id
        ? { ...w, x: w.x + dx, y: w.y + dy, move: { x: 0, y: 0 } }
        : { ...w }
    )
    const moved = updated.find((w) => w.id === id)!
    return this.resolveCollisions(moved, updated)
  }

  resizeWidget(id: string, widgets: Widget[]) {
    const widget = this.getWidgetById(id, widgets)
    if (widget == undefined) return this.widgets
    const dw = Math.round(widget.offset.x / this.WIDTH)
    const dh = Math.round(widget.offset.y / this.HEIGHT)

    const updated = widgets.map((w) =>
      w.id === id
        ? {
            ...w,
            w: clamp(w.minW, w.w + dw, w.maxW),
            h: clamp(w.minH, w.h + dh, w.maxH),
            offset: { x: 0, y: 0 },
          }
        : { ...w }
    )
    const resized = updated.find((w) => w.id === id)!
    return this.resolveCollisions(resized, updated)
  }
}

export const manager = $state({
  manager: new Manager(),
})

function clamp(min: number, value: number, max: number) {
  return Math.min(Math.max(min, value), max)
}
