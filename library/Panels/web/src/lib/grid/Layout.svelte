<script lang="ts">
  import { onMount } from "svelte"
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

  onMount(() => {
    const bounding = section.getBoundingClientRect()
    const width = bounding.width
    const height = bounding.height

    manager.WIDTH = width / manager.MAX_GRID_WIDTH
    manager.HEIGHT = height / manager.MAX_GRID_HEIGHT
  })

  let section: HTMLElement
</script>

<section
  bind:this={section}
  style="--width:{manager.WIDTH}px;--wCount:{manager.MAX_GRID_WIDTH};--height:{manager.HEIGHT}px;--hCount:{manager.MAX_GRID_HEIGHT};"
>
  <div>
    {#each gridCells as { x, y } (x + "-" + y)}
      <Overlay {x} {y} />
    {/each}
    {#each manager.widgets as widget (widget.id)}
      <WidgetItem {widget} />
    {/each}
  </div>
</section>

<style>
  section {
    position: relative;
    background-color: red;
    width: 100vw;
    height: 100vh;
    overflow: hidden;
  }
  div {
  }
</style>
