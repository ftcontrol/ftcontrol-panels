<script lang="ts">
  import { onMount } from "svelte"
  import Manager from "../manager"
  import {
    drawBase64Image,
    drawCircle,
    drawFieldImage,
    drawLine,
    drawRectangle,
    init,
  } from "./core"
  import { DrawablesTypes, emptyPacket, type Packet } from "../types"
  import { Distance, FIELD_HEIGHT, FIELD_WIDTH } from "./primitives"

  let canvas: HTMLCanvasElement

  let {
    manager,
  }: {
    manager: Manager
  } = $props()

  let packet: Packet = $state(emptyPacket)
  let images: Record<string, string> = $state(
    manager.state.get(manager.IMAGES_KEY)
  )

  function render() {
    init(canvas, new Distance(packet.offsetX), new Distance(packet.offsetY))
    if (packet.bgID != null) {
      const image = images[packet.bgID]
      if (image != null) {
        console.log("Drawing field")
        drawFieldImage(packet.bgID, image)
      }
    }
    for (const item of packet.items) {
      if (item.type == DrawablesTypes.CIRCLE) {
        drawCircle(
          new Distance(item.x),
          new Distance(item.y),
          new Distance(item.r),
          item.style.fill,
          item.style.outlineFill,
          new Distance(item.style.outlineWidth)
        )
      } else if (item.type == DrawablesTypes.RECTANGLE) {
        drawRectangle(
          new Distance(item.x),
          new Distance(item.y),
          new Distance(item.w),
          new Distance(item.h),
          item.style.fill,
          item.style.outlineFill,
          new Distance(item.style.outlineWidth)
        )
      } else if (item.type == DrawablesTypes.LINE) {
        drawLine(
          new Distance(item.x1),
          new Distance(item.y1),
          new Distance(item.x2),
          new Distance(item.y2),
          item.style.outlineFill,
          new Distance(item.style.outlineWidth)
        )
      } else if (item.type == DrawablesTypes.IMAGE) {
        drawBase64Image(
          item.id,
          images[item.id],
          new Distance(item.x),
          new Distance(item.y),
          new Distance(item.w),
          new Distance(item.h)
        )
      }
    }
  }

  onMount(async () => {
    manager.state.onChange(manager.PACKETS_KEY, (newValue) => {
      packet = newValue
      render()
    })
    manager.state.onChange(manager.IMAGES_KEY, (newValue) => {
      images = newValue
      render()
    })

    render()
  })
</script>

<canvas bind:this={canvas}></canvas>
<p>{JSON.stringify(packet)}</p>

<p>{JSON.stringify(images)}</p>

<style>
  p {
    max-width: 200px;
  }
  canvas {
    border: 1px solid currentColor;
    aspect-ratio: 1 / 1;
    display: block;
    max-width: 100%;
  }
</style>
