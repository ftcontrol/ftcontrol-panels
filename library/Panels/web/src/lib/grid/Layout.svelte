<script lang="ts">
  import { onDestroy, onMount } from "svelte"
  import WidgetItem from "./widget/Widget.svelte"
  import { manager, type Widget } from "./widgets.svelte"
  import Overlay from "./Overlay.svelte"
  import PlaceOverlay from "./PlaceOverlay.svelte"

  let mouseGridPos = $state<{
    x: number
    y: number
    isInsideWidget: boolean
  } | null>(null)

  function onMouseMove(e: MouseEvent) {
    if (!section) return
    const rect = section.getBoundingClientRect()
    const mouseX = e.clientX - rect.left
    const mouseY = e.clientY - rect.top

    const x = Math.floor(mouseX / manager.WIDTH)
    const y = Math.floor(mouseY / manager.HEIGHT)

    const clampedX = Math.min(Math.max(x, 0), manager.MAX_GRID_WIDTH - 1)
    const clampedY = Math.min(Math.max(y, 0), manager.MAX_GRID_HEIGHT - 1)

    if (manager.getWidget(clampedX, clampedY, manager.widgets) != undefined) {
      if (manager.placeStart != null) {
        mouseGridPos = { x: clampedX, y: clampedY, isInsideWidget: true }
      } else {
        mouseGridPos = null
      }
      return
    }

    mouseGridPos = { x: clampedX, y: clampedY, isInsideWidget: false }
  }

  function onMouseLeave() {
    mouseGridPos = null
  }

  function getSurface(widget: Widget) {
    return widget.w * widget.h
  }

  let gridCells = $derived.by(() => {
    let list = []
    for (let y = 0; y < manager.MAX_GRID_HEIGHT; y++) {
      for (let x = 0; x < manager.MAX_GRID_WIDTH; x++) {
        list.push({ x, y })
      }
    }
    return list
  })

  function updateGridSize() {
    const bounding = section.getBoundingClientRect()
    const width = bounding.width
    const height = bounding.height

    manager.WIDTH = width / manager.MAX_GRID_WIDTH
    manager.HEIGHT = height / manager.MAX_GRID_HEIGHT
  }

  onMount(() => {
    updateGridSize()
    window.addEventListener("resize", updateGridSize)
  })

  onDestroy(() => {
    window.removeEventListener("resize", updateGridSize)
  })

  let section: HTMLElement
</script>

<section
  bind:this={section}
  onmousemove={onMouseMove}
  onmouseleave={onMouseLeave}
  role="grid"
  tabindex="0"
  style="--width:{manager.WIDTH}px;--wCount:{manager.MAX_GRID_WIDTH};--height:{manager.HEIGHT}px;--hCount:{manager.MAX_GRID_HEIGHT};"
>
  <div>
    {#if manager.isMoving}
      {#each gridCells as { x, y } (x + "-" + y)}
        <Overlay {x} {y} />
      {/each}
      {#each manager.possibleWidgets as widget, index}
        <WidgetItem
          isPossible={true}
          bind:widget={manager.possibleWidgets[index]}
        />
      {/each}
    {/if}
    <PlaceOverlay />

    {#each manager.widgets as widget, index}
      {#if !manager.isMoving || widget.isMoving}
        <WidgetItem isPossible={false} bind:widget={manager.widgets[index]} />
      {/if}
    {/each}

    {#if mouseGridPos && !(manager.isMoving && manager.placeStart == null) && manager.tabName == ""}
      <Overlay x={mouseGridPos.x} y={mouseGridPos.y} isMouse={true} />
    {/if}
  </div>
</section>

<style>
  section {
    --spacing: 0.25rem;
    position: relative;

    flex-grow: 1;
    overflow: hidden;
    margin: var(--spacing);
  }
</style>
