<script lang="ts">
  import { onMount, onDestroy } from "svelte"
  import Manager from "../manager"
  import { Renderer } from "./renderer"
  import { emptyPacket, type Packet, rotationToRadians } from "../types"
  import {
    Distance,
    FIELD_HEIGHT,
    FIELD_WIDTH,
    PIXELS_PER_INCH,
  } from "./primitives"

  let canvas: HTMLCanvasElement
  let renderer: Renderer | null = null

  let { manager }: { manager: Manager } = $props()

  let packet: Packet = $state(emptyPacket)
  let images: Record<string, string> = $state(manager.images)

  let mouseXIn = $state(0)
  let mouseYIn = $state(0)
  let hasMouse = $state(false)

  function updateMouseCoords(ev: PointerEvent) {
    if (!canvas) return

    // 1) Content-box geometry (exclude borders)
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

    // 2) CSS px -> device px (backing store)
    const scaleX = canvas.width / contentWidth
    const scaleY = canvas.height / contentHeight
    const cx = (ev.clientX - contentLeft) * scaleX
    const cy = (ev.clientY - contentTop) * scaleY

    // 3) Dynamic pixels-per-inch in device pixels
    const ppi = canvas.width / FIELD_WIDTH.inches // == (FIELD_WIDTH.pixels * dpr) / inches

    // 4) Invert overlay transform (translate center+offset, then rotate -θ)
    const centerX = canvas.width / 2
    const centerY = canvas.height / 2
    const offXpx = packet.offsetX * ppi
    const offYpx = packet.offsetY * ppi

    const x = cx - (centerX + offXpx)
    const y = cy - (centerY + offYpx)

    const θ = rotationToRadians(packet.rotation)
    const cos = Math.cos(θ),
      sin = Math.sin(θ)

    // inverse rotation R(-θ) = [[cos, sin], [-sin, cos]]
    const xr = x * cos + y * sin
    const yr = -x * sin + y * cos

    // 5) device px -> inches
    mouseXIn = xr / ppi
    mouseYIn = yr / ppi
    // if you want +Y up, use: mouseYIn = -yr / ppi

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
      renderer.setViewport(
        new Distance(packet.offsetX),
        new Distance(packet.offsetY),
        packet.rotation
      )
      await renderer.draw(packet, images)
    })
  }

  onMount(() => {
    renderer = new Renderer(canvas)

    canvas.addEventListener("pointermove", updateMouseCoords)
    canvas.addEventListener("pointerleave", leave)

    scheduleRender()

    manager.state.onChange(manager.PACKETS_KEY, (next) => {
      // next.offsetX = 0
      // next.offsetY = 0
      // next.offsetHeading = 0
      packet = next
      scheduleRender()
    })
    manager.state.onChange(manager.IMAGES_KEY, (next) => {
      images = next
      scheduleRender()
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
  <div>
    <div>offsetX: <b>{packet.offsetX.toFixed(2)}"</b></div>
    <div>offsetY: <b>{packet.offsetY.toFixed(2)}"</b></div>
    <div>
      heading: <b
        >{Math.round((rotationToRadians(packet.rotation) * 180) / Math.PI) %
          360}°</b
      >
    </div>
  </div>
  {#if hasMouse}
    <div>
      <div>mouseX: <b>{mouseXIn.toFixed(2)}"</b></div>
      <div>mouseY: <b>{mouseYIn.toFixed(2)}"</b></div>
    </div>
  {/if}
</div>

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
