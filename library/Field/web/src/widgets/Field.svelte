<script lang="ts">
  import { onMount, onDestroy } from "svelte"
  import Manager from "../manager"
  import { Renderer } from "./renderer"
  import {
    CanvasRotation,
    type Circle,
    emptyPacket,
    emptyPreset,
    type FieldPresetParams,
    type Packet,
    rotationToRadians,
    type Style,
    DrawableType,
  } from "../types"
  import { FIELD_HEIGHT, FIELD_WIDTH } from "./primitives"
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

    const W = FIELD_WIDTH.pixels
    const H = FIELD_HEIGHT.pixels

    const flipX = !!packet.preset.flipX
    const flipY = !!packet.preset.flipY
    const reverseXY = !!packet.preset.reverseXY
    const sx = flipX ? -1 : 1
    const sy = flipY ? -1 : 1

    const S00 = reverseXY ? 0 : 1
    const S01 = reverseXY ? 1 : 0
    const S10 = reverseXY ? 1 : 0
    const S11 = reverseXY ? 0 : 1

    const B00 = S00 * sx
    const B01 = S01 * sy
    const B10 = S10 * sx
    const B11 = S11 * sy

    const mwc_a = 0,
      mwc_b = 1,
      mwc_c = -1,
      mwc_d = 0
    const L_a = mwc_a * B00 + mwc_b * B10
    const L_b = mwc_a * B01 + mwc_b * B11
    const L_c = mwc_c * B00 + mwc_d * B10
    const L_d = mwc_c * B01 + mwc_d * B11

    const ppi = FIELD_WIDTH.pixels / FIELD_WIDTH.inches
    const ox = (packet.preset.offsetX ?? 0) * ppi
    const oy = (packet.preset.offsetY ?? 0) * ppi
    const tX = L_a * ox + L_b * oy
    const tY = L_c * ox + L_d * oy

    const theta = rotationToRadians(packet.preset.rotation)
    const cos = Math.cos(theta)
    const sin = Math.sin(theta)

    const a = cos * L_a + -sin * L_c
    const b = sin * L_a + cos * L_c
    const c = cos * L_b + -sin * L_d
    const d = sin * L_b + cos * L_d
    const e = W / 2 + (cos * tX - sin * tY)
    const f = H / 2 + (sin * tX + cos * tY)

    const det = a * d - b * c
    if (Math.abs(det) < 1e-8) return
    const ia = d / det,
      ib = -b / det,
      ic = -c / det,
      id = a / det
    const ie = -(ia * e + ic * f),
      iff = -(ib * e + id * f)

    const wx_px = ia * cx + ic * cy + ie
    const wy_px = ib * cx + id * cy + iff

    mouseXIn = wx_px / ppi
    mouseYIn = wy_px / ppi
    hasMouse = true

    // packet.items = [
    //   {
    //     type: DrawableType.CIRCLE,
    //     x: mouseXIn,
    //     y: mouseYIn,
    //     r: 5,
    //     style: { fill: "green" } as Style,
    //   } as Circle,
    // ]
    // scheduleRender()
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
      if (replacePreset.name !== "NONE") {
        packet.preset = replacePreset
      }
      // optional demo marker (keep/remove as you wish)
      // packet.preset = {
      //   name: "NONE",
      //   offsetX: 24 * 0,
      //   offsetY: 24 * 0,
      //   rotation: CanvasRotation.DEG_0,
      //   flipX: false,
      //   flipY: false,
      //   reverseXY: true,
      // }
      // packet.items.push({
      //   type: DrawableType.CIRCLE,
      //   x: 0,
      //   y: 0,
      //   r: 5,
      //   style: { fill: "green" } as Style,
      // } as Circle)

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
      <div>offsetX: <b>{(packet.preset.offsetX ?? 0).toFixed(2)}"</b></div>
      <div>offsetY: <b>{(packet.preset.offsetY ?? 0).toFixed(2)}"</b></div>
      <div>
        heading:
        <b
          >{Math.round(
            (rotationToRadians(packet.preset.rotation) * 180) / Math.PI
          ) % 360}°</b
        >
      </div>
      <div>flipX: <b>{packet.preset.flipX ? "true" : "false"}</b></div>
      <div>flipY: <b>{packet.preset.flipY ? "true" : "false"}</b></div>
      <div>reverseXY: <b>{packet.preset.reverseXY ? "true" : "false"}</b></div>
    </div>
  {/if}
  {#if hasMouse}
    <div>
      <div>mouseX: <b>{mouseXIn.toFixed(2)}"</b></div>
      <div>mouseY: <b>{mouseYIn.toFixed(2)}"</b></div>
    </div>
  {/if}
</div>

<div class="flex">
  Preset:
  <Overlay>
    {#snippet trigger()}
      <button
        class="main"
        class:disabled={presets.length == 0}
        disabled={presets.length == 0}
      >
        {replacePreset.name}
      </button>
    {/snippet}
    {#snippet overlay({ close }: { close: () => void })}
      {#each presets.filter((it) => it.name !== replacePreset.name) as p}
        <div class="items">
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
        </div>
      {/each}
    {/snippet}
  </Overlay>
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
  button {
    all: unset;
    cursor: pointer;
    padding: 0.25rem;
    display: inline-block;
  }
  button.disabled {
    opacity: 0.5;
  }
  button.main {
    outline: 1px solid currentColor;
  }
  .flex {
    display: flex;
    align-items: center;
    gap: 0.25rem;
  }
  .items {
    display: flex;
    flex-direction: column;
  }
</style>
