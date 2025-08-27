<script lang="ts">
  import { onDestroy, onMount } from "svelte"
  import WidgetItem from "./widget/Widget.svelte"

  import Overlay from "./Overlay.svelte"
  import PlaceOverlay from "./PlaceOverlay.svelte"
  import { setContext } from "svelte"
  import type { Manager } from "./widgets.svelte"
  import TemplatesChoose from "./TemplatesChoose.svelte"

  let {
    manager = $bindable(),
    enableInteractions = false,
  }: {
    manager: Manager
    enableInteractions?: boolean
  } = $props()

  setContext("manager", manager)

  let mouseGridPos = $state<{
    x: number
    y: number
    isInsideWidget: boolean
  } | null>(null)

  function onMouseMove(e: MouseEvent) {
    if (!manager.enableInteractions) return
    if (!section) return
    const rect = section.getBoundingClientRect()
    const mouseX = e.clientX - rect.left
    const mouseY = e.clientY - rect.top

    const x = Math.floor(mouseX / manager.WIDTH)
    const y = Math.floor(mouseY / manager.HEIGHT)

    const clampedX = Math.min(Math.max(x, 0), manager.MAX_GRID_WIDTH - 1)
    const clampedY = Math.min(Math.max(y, 0), manager.MAX_GRID_HEIGHT - 1)

    if (manager.getWidget(clampedX, clampedY, manager.widgets) != undefined) {
      if (manager.placeStart != null) {
        mouseGridPos = { x: clampedX, y: clampedY, isInsideWidget: true }
      } else {
        mouseGridPos = null
      }
      return
    }

    mouseGridPos = { x: clampedX, y: clampedY, isInsideWidget: false }
  }

  function onMouseLeave() {
    if (!manager.enableInteractions) return
    mouseGridPos = null
  }

  let gridCells = $derived.by(() => {
    let list = []
    for (let y = 0; y < manager.MAX_GRID_HEIGHT; y++) {
      for (let x = 0; x < manager.MAX_GRID_WIDTH; x++) {
        list.push({ x, y })
      }
    }
    return list
  })

  onMount(() => {
    if (enableInteractions) {
      manager.loadInteractive()
    }
    manager.updateGridSize(section)
    if (!manager.enableInteractions) return
    window.addEventListener("resize", () => manager.updateGridSize(section))
  })

  onDestroy(() => {
    window.removeEventListener("resize", () => manager.updateGridSize(section))
  })

  let section: HTMLElement
</script>

<div class="wrapper">
  <section
    bind:this={section}
    onmousemove={onMouseMove}
    onmouseleave={onMouseLeave}
    role="grid"
    tabindex="0"
    style="--width:{manager.WIDTH}px;--wCount:{manager.MAX_GRID_WIDTH};--height:{manager.HEIGHT}px;--hCount:{manager.MAX_GRID_HEIGHT};"
  >
    {#if manager.isEmpty}
      <div
        class="item"
        style="--w:{manager.MAX_GRID_WIDTH};--h:{manager.MAX_GRID_HEIGHT};"
      >
        <TemplatesChoose
          set={(t) => {
            manager.replaceCurrentWith(t)
          }}
        />
      </div>
    {:else}
      {#if manager.isMoving}
        {#each gridCells as { x, y } (x + "-" + y)}
          <Overlay {x} {y} />
        {/each}
        {#each manager.possibleWidgets as widget, index}
          <WidgetItem
            isPossible={true}
            bind:widget={manager.possibleWidgets[index]}
          />
        {/each}
      {/if}
      <PlaceOverlay />

      {#each manager.widgets as widget, index}
        {#if !manager.isMoving || widget.isMoving}
          <WidgetItem isPossible={false} bind:widget={manager.widgets[index]} />
        {/if}
      {/each}

      {#if mouseGridPos != null && !(manager.isMoving && manager.placeStart == null) && manager.tabName == ""}
        <Overlay x={mouseGridPos.x} y={mouseGridPos.y} isMouse={true} />
      {/if}
    {/if}
  </section>
</div>

<style>
  .item {
    position: absolute;
    top: 0;
    left: 0;
    height: calc(var(--h) * var(--height));
    width: calc(var(--w) * var(--width));
    display: grid;
    place-items: center;
    flex-direction: column;
    padding: var(--spacing);
  }
  .wrapper {
    overflow-x: auto;
    height: 100%;
  }
  section {
    --spacing: 0.25rem;
    position: relative;

    flex-grow: 1;
    overflow: hidden;
    margin: var(--spacing);
    min-width: 1200px;
    height: calc(100% - 2 * var(--spacing));
  }
</style>
