<script lang="ts">
  import { onDestroy, onMount } from "svelte"
  import WidgetItem from "./Widget.svelte"
  import { manager, type Widget } from "./widgets.svelte"
  import Overlay from "./Overlay.svelte"

  function getSurface(widget: Widget) {
    return widget.w * widget.h
  }

  let emptyCells = $derived.by(() => {
    let cells = 6 * 6
    for (const w of manager.widgets) {
      cells -= getSurface(w)
    }
    return cells
  })

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
  style="--width:{manager.WIDTH}px;--wCount:{manager.MAX_GRID_WIDTH};--height:{manager.HEIGHT}px;--hCount:{manager.MAX_GRID_HEIGHT};"
>
  <div>
    {#if manager.isMoving}
      {#each gridCells as { x, y } (x + "-" + y)}
        <Overlay {x} {y} />
      {/each}
      {#each manager.possibleWidgets as widget (widget.id)}
        <WidgetItem isPossible={true} {widget} />
      {/each}
    {/if}
    {#each manager.widgets as widget, index}
      {#if !manager.isMoving || widget.isMoving}
        <WidgetItem isPossible={false} bind:widget={manager.widgets[index]} />
      {/if}
    {/each}
  </div>
</section>

<style>
  section {
    position: relative;
    width: 100%;
    height: 100%;
    overflow: hidden;
  }
</style>
