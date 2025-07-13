<script lang="ts">
  import { onMount } from "svelte"
  import WidgetItem from "./Widget.svelte"
  import { manager, type Widget } from "./widgets.svelte"

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
