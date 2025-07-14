<script lang="ts">
  import Move from "$lib/icons/Move.svelte"
  import { manager, type Widget } from "../widgets.svelte"
  let {
    widget = $bindable(),
    isPossible = $bindable(),
  }: { widget: Widget; isPossible: boolean } = $props()

  let startX = $state(0)
  let startY = $state(0)

  function startDrag(e: MouseEvent) {
    if (isPossible) return
    console.log("Start drag")

    widget.isMoving = true
    e.preventDefault()
    startX = e.clientX
    startY = e.clientY
    window.addEventListener("mousemove", onDrag)
    window.addEventListener("mouseup", stopDrag)
  }

  function onDrag(e: MouseEvent) {
    console.log("update drag")
    widget.move = {
      x: e.clientX - startX,
      y: e.clientY - startY,
    }
    manager.updateMove(widget.id)
  }

  function stopDrag() {
    console.log("Stop drag")
    widget.isMoving = false
    window.removeEventListener("mousemove", onDrag)
    window.removeEventListener("mouseup", stopDrag)
    manager.finishMoveWidget(widget.id)
    widget.move = {
      x: 0,
      y: 0,
    }
  }
</script>

<button class="icon" onmousedown={startDrag}><Move /></button>

<style>
  .icon {
    all: unset;
    cursor: pointer;
    width: fit-content;
    display: flex;
  }
</style>
