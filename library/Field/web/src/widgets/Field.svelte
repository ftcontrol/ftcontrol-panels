<script lang="ts">
  import { onMount, onDestroy } from "svelte"
  import Manager from "../manager"
  import { Renderer } from "./renderer"
  import {
    CanvasRotation,
    emptyPacket,
    emptyPreset,
    type FieldPresetParams,
    type Packet,
    rotationToRadians,
  } from "../types"
  import {
    Distance,
    FIELD_HEIGHT,
    FIELD_WIDTH,
    PIXELS_PER_INCH,
  } from "./primitives"
  import { Overlay } from "ftc-panels"

  let canvas: HTMLCanvasElement
  let renderer: Renderer | null = null

  let { manager }: { manager: Manager } = $props()

  let packet: Packet = $state(emptyPacket)
  let lastPacketPreset: FieldPresetParams = $state(emptyPreset)
  let images: Record<string, string> = $state(manager.images)
  let presets: FieldPresetParams[] = $state([])
  let replacePreset = $state<FieldPresetParams>(emptyPreset)

  let mouseXIn = $state(0)
  let mouseYIn = $state(0)
  let hasMouse = $state(false)

  function updateMouseCoords(ev: PointerEvent) {
    if (!canvas) return

    const rect = canvas.getBoundingClientRect()
    const cs = getComputedStyle(canvas)
    const bl = parseFloat(cs.borderLeftWidth) || 0
    const bt = parseFloat(cs.borderTopWidth) || 0
    const br = parseFloat(cs.borderRightWidth) || 0
    const bb = parseFloat(cs.borderBottomWidth) || 0

    const contentLeft = rect.left + bl
    const contentTop = rect.top + bt
    const contentWidth = rect.width - bl - br
    const contentHeight = rect.height - bt - bb

    const cx = (ev.clientX - contentLeft) * (canvas.width / contentWidth)
    const cy = (ev.clientY - contentTop) * (canvas.height / contentHeight)

    const ppi = canvas.width / FIELD_WIDTH.inches
    const centerX = canvas.width / 2
    const centerY = canvas.height / 2
    const offXpx = packet.preset.offsetX * ppi
    const offYpx = packet.preset.offsetY * ppi
    const flipX = packet.preset.flipX
    const flipY = packet.preset.flipY
    const deg = (rotationToRadians(packet.preset.rotation) * 180) / Math.PI

    const M = new DOMMatrix()
      .translateSelf(centerX + offXpx, centerY + offYpx)
      .rotateSelf(0, 0, deg)
      .scaleSelf(flipX ? -1 : 1, flipY ? -1 : 1)

    const inv = M.inverse()
    const pt = new DOMPoint(cx, cy).matrixTransform(inv)

    mouseXIn = pt.x / ppi
    mouseYIn = pt.y / ppi
    hasMouse = true
  }

  function leave() {
    hasMouse = false
  }

  let needsRender = false
  function scheduleRender() {
    if (needsRender) return
    needsRender = true
    requestAnimationFrame(async () => {
      needsRender = false
      if (!renderer) return
      renderer.setViewport(packet)
      await renderer.draw(packet, images)
    })
  }

  onMount(() => {
    renderer = new Renderer(canvas)

    canvas.addEventListener("pointermove", updateMouseCoords)
    canvas.addEventListener("pointerleave", leave)

    scheduleRender()

    manager.state.onChange(manager.PACKETS_KEY, (next) => {
      packet = next
      if (replacePreset.name != "NONE") packet.preset = replacePreset
      packet.preset = {
        name: "NONE",
        offsetX: 24.0 * 9,
        offsetY: -24.0 * 9,
        rotation: CanvasRotation.DEG_270,
        flipX: false,
        flipY: true,
      }
      lastPacketPreset = next.preset
      scheduleRender()
    })
    manager.state.onChange(manager.IMAGES_KEY, (next) => {
      images = next
      scheduleRender()
    })

    manager.state.onChange(manager.PRESETS_KEY, (next) => {
      presets = next
    })
  })

  onDestroy(() => {
    canvas?.removeEventListener("pointermove", updateMouseCoords)
    canvas?.removeEventListener("pointerleave", leave)
    renderer?.destroy()
    renderer = null
  })
</script>

<canvas bind:this={canvas}></canvas>

<div class="info">
  {#if packet.preset != undefined}
    <div>
      <div>offsetX: <b>{packet.preset.offsetX.toFixed(2)}"</b></div>
      <div>offsetY: <b>{packet.preset.offsetY.toFixed(2)}"</b></div>
      <div>
        heading: <b
          >{Math.round(
            (rotationToRadians(packet.preset.rotation) * 180) / Math.PI
          ) % 360}°</b
        >
      </div>
      <div>flipX: <b>{packet.preset.flipX}"</b></div>
      <div>flipY: <b>{packet.preset.flipY}"</b></div>
    </div>
  {/if}
  {#if hasMouse}
    <div>
      <div>mouseX: <b>{mouseXIn.toFixed(2)}"</b></div>
      <div>mouseY: <b>{mouseYIn.toFixed(2)}"</b></div>
    </div>
  {/if}
</div>

<Overlay>
  {#snippet trigger()}
    <button class:disabled={presets.length == 0}>
      {replacePreset.name}
    </button>
  {/snippet}
  {#snippet overlay({ close }: { close: () => void })}
    {#each presets.filter((it) => it.name !== replacePreset.name) as p}
      <button
        onclick={() => {
          replacePreset = p
          if (p.name == "NONE") {
            packet.preset = lastPacketPreset
          } else {
            packet.preset = p
          }
          scheduleRender()
          close()
        }}
      >
        {p.name}
      </button>
    {/each}
  {/snippet}
</Overlay>

<style>
  canvas {
    border: 1px solid currentColor;
    aspect-ratio: 1 / 1;
    display: block;
    max-width: 100%;
  }

  .info {
    font-size: 1em;
    margin-block: var(--padding);
    display: flex;
    flex-wrap: wrap;
    gap: var(--padding);
  }

  b {
    font-weight: 600;
  }
</style>
