<script lang="ts">
  import { DynamicComponent, Overlay } from "ftc-panels"
  import { manager, type Widget } from "./widgets.svelte"
  import { global } from "$lib"
  import plugin from "@sveltejs/adapter-static"

  let {
    widget = $bindable(),
    isPossible,
  }: { widget: Widget; isPossible: boolean } = $props()

  let movingIndex = $state(0)

  //   let selectedPanel = $derived(widget.widgets[widget.selected])

  //   let plugin = $derived(
  //     global.plugins.find((it) => it.details.id == selectedPanel.pluginID)
  //   )

  //   let pluginWidget = $derived(
  //     plugin?.details.widgets.find((it) => it.name == selectedPanel.widgetID)
  //   )

  let xOffset = $state(0)
  let yOffset = $state(0)

  let xMove = $state(0)
  let yMove = $state(0)

  let startX = $state(0)
  let startY = $state(0)

  function startMove(e: MouseEvent) {
    if (isPossible) return
    console.log("Started move of", movingIndex)

    window.addEventListener("mousemove", onMove)
    startX = e.clientX
    startY = e.clientY
    window.addEventListener("mouseup", stopMove)
  }

  function onMove(e: MouseEvent) {
    if (
      Math.abs(e.clientX - startX) >= 1 &&
      Math.abs(e.clientY - startY) >= 1
    ) {
      widget.widgets[movingIndex].isMoving = true
    }
    const elements = document.elementsFromPoint(e.clientX, e.clientY)
    const el = elements.filter(
      (el) =>
        el instanceof HTMLElement &&
        el.hasAttribute("data-widget") &&
        el.hasAttribute("data-index")
    )[0]

    if (el == undefined) {
      manager.tabIndex = 0
      manager.tabWidgetID = ""
      return
    }

    let dataWidget = el.getAttribute("data-widget") || ""
    let dataIndex = parseInt(el.getAttribute("data-index") || "")
    if (
      dataWidget != widget.id ||
      (dataWidget == widget.id &&
        movingIndex != dataIndex &&
        movingIndex != dataIndex + 1)
    ) {
      manager.tabIndex = dataIndex
      manager.tabWidgetID = dataWidget
    }
  }
  function stopMove(e: MouseEvent) {
    console.log("Stopped move of", movingIndex)
    widget.widgets[movingIndex].isMoving = false

    manager.tabIndex = 0
    manager.tabWidgetID = ""

    const elements = document.elementsFromPoint(e.clientX, e.clientY)
    const el = elements.filter(
      (el) =>
        el instanceof HTMLElement &&
        el.hasAttribute("data-widget") &&
        el.hasAttribute("data-index")
    )[0]

    if (el == undefined) {
      window.removeEventListener("mousemove", onMove)
      window.removeEventListener("mouseup", stopMove)
      return
    }

    let dataWidget = el.getAttribute("data-widget")
    let dataIndex = parseInt(el.getAttribute("data-index") || "")

    if (
      dataWidget != widget.id ||
      (dataWidget == widget.id &&
        movingIndex != dataIndex &&
        movingIndex != dataIndex + 1)
    ) {
      console.log(el, dataWidget, dataIndex)

      const replaceWidget = manager.widgets.find((it) => it.id == dataWidget)

      if (replaceWidget != undefined && dataIndex != undefined) {
        const movingPanel = widget.widgets[movingIndex]

        widget.widgets.splice(movingIndex, 1)

        replaceWidget.widgets = [
          ...replaceWidget.widgets.slice(0, dataIndex + 1),
          movingPanel,
          ...replaceWidget.widgets.slice(dataIndex + 1),
        ]

        widget.selected -= 1
        if (widget.selected < 0) {
          widget.selected = 0
        }
      }
    } else {
      if (dataIndex == movingIndex) {
        widget.selected = movingIndex
      }
    }

    window.removeEventListener("mousemove", onMove)
    window.removeEventListener("mouseup", stopMove)
  }

  function startDrag(e: MouseEvent) {
    if (isPossible) return
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
    if (isPossible) return
    widget.isMoving = true
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
    widget.isMoving = false
    window.removeEventListener("mousemove", onResize)
    window.removeEventListener("mouseup", stopResize)
    manager.finishResizeWidget(widget.id, xOffset, yOffset)
    xOffset = 0
    yOffset = 0
  }
</script>

<div
  class="item"
  class:transparent={widget.isMoving && !isPossible}
  style="--x:{widget.x};--y:{widget.y};--w:{widget.w};--h:{widget.h};--xOffset:{xOffset}px;--yOffset:{yOffset}px;--xMove:{xMove}px;--yMove:{yMove}px;"
>
  <nav>
    <button onmousedown={startDrag}>M</button>
    {#each widget.widgets as w, index}
      <button
        data-widget={widget.id}
        data-index={index}
        class:moving={w.isMoving}
        onmousedown={(e: MouseEvent) => {
          movingIndex = index
          startMove(e)
        }}>{w.widgetID}</button
      >
      {#if manager.tabWidgetID == widget.id && manager.tabIndex == index}
        <button>SOMETHING</button>
      {/if}
    {/each}
    {#if widget.widgets.length == 0}
      <button data-widget={widget.id} data-index={-1}>MOVE</button>
    {/if}
  </nav>

  <section>
    {#if widget.widgets.length > 0}
      {#each widget.widgets, index}
        <div class="w" class:selected={index == widget.selected}>
          <DynamicComponent
            globalSocket={global.socket}
            textContent={global.plugins
              .find((it) => it.details.id == widget.widgets[index].pluginID)
              ?.details.widgets.find(
                (it) => it.name == widget.widgets[index].widgetID
              )?.textContent}
            id={widget.widgets[index].pluginID}
          />
        </div>
      {/each}
    {:else}
      <p>No widgets found</p>
    {/if}
  </section>
  <button class="resize" onmousedown={startResize}>R</button>
</div>

<style>
  .w {
    display: none;
  }

  .w.selected {
    display: block;
  }
  .item {
    background-color: var(--bgMedium);
    position: absolute;
    top: calc(var(--y) * var(--height) + var(--yMove));
    left: calc(var(--x) * var(--width) + var(--xMove));
    height: calc(var(--h) * var(--height) + var(--yOffset));
    width: calc(var(--w) * var(--width) + var(--xOffset));
  }
  .item {
    display: flex;
    flex-direction: column;
  }
  section {
    overflow: hidden;
    flex-grow: 1;
  }
  .transparent {
    opacity: 0.25;
  }
  p {
    margin: 0;
  }
  .resize {
    position: absolute;
    right: 0;
    bottom: 0;
  }

  .moving {
    opacity: 0.25;
  }
</style>
