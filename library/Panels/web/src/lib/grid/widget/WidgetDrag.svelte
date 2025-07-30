<script lang="ts">
  import Move from "$lib/icons/Move.svelte"
  import { getContext } from "svelte"
  import { type ExtendedWidgetGroup } from "../widgets.svelte"
  import type { Manager } from "../widgets.svelte"
  const manager = getContext("manager") as Manager
  let {
    widget = $bindable(),
    isPossible = $bindable(),
  }: { widget: ExtendedWidgetGroup; isPossible: boolean } = $props()

  let startX = $state(0)
  let startY = $state(0)

  function startDrag(e: MouseEvent) {
    if (!manager.enableInteractions) return
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
    if (!manager.enableInteractions) return
    console.log("update drag")
    widget.move = {
      x: e.clientX - startX,
      y: e.clientY - startY,
    }
    manager.updateMove(widget.id)
  }

  function stopDrag() {
    if (!manager.enableInteractions) return
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
