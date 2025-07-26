<script lang="ts">
  import { manager, type Widget } from "./widgets.svelte"

  let {
    x,
    y,
    isMouse = false,
  }: { x: number; y: number; isMouse?: boolean } = $props()

  let w = $derived(manager.getWidget(x, y, manager.possibleWidgets))

  let blue = $derived.by(() => {
    if (w == undefined) return true
    if (w.isMoving) return false
    return false
  })
</script>

{#if isMouse}
  <button
    oncontextmenu={(e) => e.preventDefault()}
    onmousedown={(e) => {
      if (e.button != 0) return
      manager.startPlace(x, y)
    }}
    onmouseup={(e) => {
      if (e.button != 0) return
      manager.endPlace(x, y)
    }}
    onmousemove={() => {
      manager.updatePlace(x, y)
    }}
    style="--x:{x};--y:{y};">+</button
  >
{:else}
  <div style="--x:{x};--y:{y};"></div>
{/if}

<style>
  div,
  button {
    position: absolute;
    background-color: transparent;
    color: inherit;
    border: 1px solid var(--bgLight);
    top: calc(var(--y) * var(--height));
    left: calc(var(--x) * var(--width));
    height: calc(var(--height));
    width: calc(var(--width));
    display: grid;
    place-content: center;
  }
</style>
