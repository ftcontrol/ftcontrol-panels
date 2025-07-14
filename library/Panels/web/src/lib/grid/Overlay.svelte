<script lang="ts">
  import { manager, type Widget } from "./widgets.svelte"

  let { x, y }: { x: number; y: number } = $props()

  let w = $derived(manager.getWidget(x, y, manager.possibleWidgets))

  let blue = $derived.by(() => {
    if (w == undefined) return true
    if (w.isMoving) return false
    return false
  })
</script>

<div style="--x:{x};--y:{y};"></div>

<style>
  div {
    position: absolute;
    outline: 1px solid var(--bgLight);
    top: calc(var(--y) * var(--height));
    left: calc(var(--x) * var(--width));
    height: calc(var(--height));
    width: calc(var(--width));
  }
</style>
