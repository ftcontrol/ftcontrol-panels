<script lang="ts">
  import { manager, type Widget } from "../widgets.svelte"
  import WidgetOptions from "./WidgetOptions.svelte"
  import Portal from "svelte-portal"
  import WidgetDrag from "./WidgetDrag.svelte"

  let {
    widget = $bindable(),
    isPossible = $bindable(),
  }: { widget: Widget; isPossible: boolean } = $props()

  let movingIndex = $state(0)

  let tabX = $state(0)
  let tabY = $state(0)

  let xOffset = $state(0)
  let yOffset = $state(0)

  function startMove(e: MouseEvent) {
    if (isPossible) return
    console.log("Started move of", movingIndex)

    manager.tabName = widget.widgets[movingIndex].widgetID

    window.addEventListener("mousemove", onMove)

    const tab = document.querySelector(
      `[data-widget="${widget.id}"][data-index="${movingIndex}"]`
    ) as HTMLElement

    xOffset = 0
    yOffset = 0

    const rect = tab?.getBoundingClientRect()

    if (rect) {
      xOffset = e.clientX - rect.left
      yOffset = e.clientY - rect.top
    }

    tabX = e.clientX
    tabY = e.clientY
    window.addEventListener("mouseup", stopMove)
  }

  function onMove(e: MouseEvent) {
    tabX = e.clientX
    tabY = e.clientY
    if (
      Math.abs(e.clientX - xOffset) >= 1 &&
      Math.abs(e.clientY - yOffset) >= 1
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
    manager.tabName = ""

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
</script>

{#if widget.widgets?.[movingIndex]?.isMoving}
  <Portal>
    <button
      class="tab absolute"
      style="--x:{tabX - xOffset}px;--y:{tabY - yOffset}px;"
      >{manager.tabName}</button
    >
  </Portal>
{/if}

<nav>
  <WidgetDrag bind:widget bind:isPossible />
  <div class="tabs">
    {#each widget.widgets as w, index}
      <button
        class="tab"
        data-widget={widget.id}
        data-index={index}
        class:moving={w.isMoving}
        onmousedown={(e: MouseEvent) => {
          movingIndex = index

          startMove(e)

          const element = e.currentTarget as HTMLElement

          element.scrollIntoView({
            behavior: "smooth",
            block: "nearest",
            inline: "center",
          })
        }}>{w.widgetID || "Empty"}</button
      >
      {#if manager.tabWidgetID == widget.id && manager.tabIndex == index}
        <button class="tab moving">{manager.tabName}</button>
      {/if}
    {/each}
    {#if widget.widgets.length == 0}
      <button class="tab" data-widget={widget.id} data-index={-1}>
        {#if manager.tabWidgetID == widget.id && manager.tabIndex == -1}
          {manager.tabName}
        {:else}
          Empty
        {/if}
      </button>
    {/if}
  </div>
  <div style="margin-left: auto;margin-right: var(--padding)">
    <WidgetOptions bind:widget />
  </div>
</nav>

<style>
  nav {
    display: flex;
    align-items: center;
    gap: 0.5rem;
    padding-left: var(--padding);
    padding-top: var(--padding);
    padding-bottom: calc(var(--padding) / 2);
    width: 100%;
  }
  .tabs {
    display: flex;
    align-items: center;
    gap: 0.5rem;
    overflow-y: auto;
  }
  .moving {
    opacity: 0.25;
  }
  .tab.absolute {
    position: absolute;
    top: var(--y);
    left: var(--x);
  }
  .tab {
    all: unset;
    cursor: pointer;
    padding: 0.25em 0.5em;
    border: 1px solid currentColor;
    border-radius: 0.25rem;
    text-wrap: nowrap;
  }
</style>
