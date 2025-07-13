<script lang="ts">
  import { manager, type Widget } from "./widgets.svelte"

  let { widget }: { widget: Widget } = $props()

  let xOffset = $state(0)
  let yOffset = $state(0)

  let xMove = $state(0)
  let yMove = $state(0)

  let startX = $state(0)
  let startY = $state(0)

  function startDrag(e: MouseEvent) {
    widget.isMoving = true
    e.preventDefault()
    startX = e.clientX
    startY = e.clientY
    window.addEventListener("mousemove", onDrag)
    window.addEventListener("mouseup", stopDrag)
  }

  function onDrag(e: MouseEvent) {
    xMove = e.clientX - startX
    yMove = e.clientY - startY
    manager.updateMove(widget.id, xMove, yMove)
  }

  function stopDrag() {
    widget.isMoving = false
    window.removeEventListener("mousemove", onDrag)
    window.removeEventListener("mouseup", stopDrag)
    manager.finishMoveWidget(widget.id, xMove, yMove)
    xMove = 0
    yMove = 0
  }

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
    manager.updateResize(widget.id, xOffset, yOffset)
  }

  function stopResize() {
    window.removeEventListener("mousemove", onResize)
    window.removeEventListener("mouseup", stopResize)
    manager.finishResizeWidget(widget.id, xOffset, yOffset)
    xOffset = 0
    yOffset = 0
  }
</script>

<div
  style="--x:{widget.x};--y:{widget.y};--w:{widget.w};--h:{widget.h};--xOffset:{xOffset}px;--yOffset:{yOffset}px;--xMove:{xMove}px;--yMove:{yMove}px;"
>
  <p>
    {widget.id} / {manager.isOutOfBounds(widget)} / {widget.x} / {widget.y} / {widget.x +
      widget.w} / {widget.y + widget.h}
  </p>
  <p>{widget.isMoving}</p>
  <button onmousedown={startDrag}>M</button>
  <button class="resize" onmousedown={startResize}>R</button>
</div>

<style>
  div {
    background-color: red;
    position: absolute;
    outline: 1px solid black;
    top: calc(var(--y) * var(--height) + var(--yMove));
    left: calc(var(--x) * var(--width) + var(--xMove));
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
