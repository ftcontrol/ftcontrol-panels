<script lang="ts">
  import { getContext } from "svelte"
  import type { Manager } from "./widgets.svelte"
  const manager = getContext("manager") as Manager

  let {
    x,
    y,
    isMouse = false,
  }: { x: number; y: number; isMouse?: boolean } = $props()

  function handleMouseMove() {
    if (manager.placeStart == null) return
    manager.updatePlace(x, y)
  }

  function handleMouseLeave() {
    cleanup()
    manager.resetPlace()
  }

  function handleMouseOut(e) {
    var from = e.relatedTarget || e.toElement
    if (!from || from.nodeName == "HTML") {
      handleMouseLeave()
    }
  }

  function cleanup() {
    window.removeEventListener("mouseout", handleMouseOut)
    window.removeEventListener("blur", handleMouseLeave)
  }
</script>

{#if isMouse}
  <button
    oncontextmenu={(e) => e.preventDefault()}
    onmousedown={(e) => {
      if (e.button != 0) return
      manager.startPlace(x, y)

      window.addEventListener("mouseout", handleMouseOut)
      window.addEventListener("blur", handleMouseLeave)
    }}
    onmouseup={(e) => {
      if (e.button != 0) return
      manager.endPlace(x, y)
      cleanup()
    }}
    onmousemove={handleMouseMove}
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
