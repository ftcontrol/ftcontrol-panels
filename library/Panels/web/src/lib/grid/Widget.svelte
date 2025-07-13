<script lang="ts">
  import {
    HEIGHT,
    manager,
    resizeWidget,
    WIDTH,
    type Widget,
  } from "./widgets.svelte"

  let { widget }: { widget: Widget } = $props()

  let xOffset = $state(0)
  let yOffset = $state(0)

  let startX = $state(0)
  let startY = $state(0)

  function startResize(e: MouseEvent) {
    startX = e.clientX
    startY = e.clientY
    window.addEventListener("mousemove", onResize)
    window.addEventListener("mouseup", stopResize)
  }

  function onResize(e: MouseEvent) {
    e.preventDefault()
    xOffset = e.clientX - startX
    yOffset = e.clientY - startY
  }

  function stopResize() {
    window.removeEventListener("mousemove", onResize)
    window.removeEventListener("mouseup", stopResize)

    const dw = Math.round(xOffset / WIDTH)
    const dh = Math.round(yOffset / HEIGHT)
    const newW = Math.min(Math.max(widget.minW, widget.w + dw), widget.maxW)
    const newH = Math.min(Math.max(widget.minH, widget.h + dh), widget.maxH)

    xOffset = 0
    yOffset = 0

    manager.widgets = resizeWidget(widget.id, newW, newH, manager.widgets)
  }
</script>

<div
  style="--x:{widget.x};--y:{widget.y};--w:{widget.w};--h:{widget.h};--xOffset:{xOffset}px;--yOffset:{yOffset}px;"
>
  <p>
    {widget.id}
  </p>
  <button>M</button>
  <button class="resize" onmousedown={startResize}>R</button>
</div>

<style>
  div {
    position: relative;
    outline: 1px solid black;
    top: calc(var(--y) * var(--height));
    left: calc(var(--x) * var(--width));
    height: calc(var(--h) * var(--height) + var(--yOffset));
    width: calc(var(--w) * var(--width) + var(--xOffset));
  }
  p {
    margin: 0;
  }
  .resize {
    position: absolute;
    right: 0;
    bottom: 0;
  }
</style>
