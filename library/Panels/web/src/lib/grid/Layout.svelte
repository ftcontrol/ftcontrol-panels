<script lang="ts">
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
</script>

<section style="--width:{manager.WIDTH}px;--height:{manager.HEIGHT}px;">
  {#each manager.widgets as widget (widget.id)}
    <WidgetItem {widget} />
  {/each}
</section>

<style>
  section {
    position: relative;
    background-color: red;
    width: calc(6 * var(--width));
    height: calc(6 * var(--height));
  }
</style>
