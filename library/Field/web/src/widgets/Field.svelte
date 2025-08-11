<script lang="ts">
  import { onMount, tick } from "svelte"
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
  import { Distance } from "./primitives"

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

  async function render() {
    console.log("rendering full")

    init(canvas, new Distance(packet.offsetX), new Distance(packet.offsetY))
    if (packet.bgID != null) {
      if (packet.bgID in images) {
        const image = images[packet.bgID]
        if (image != null && image !== undefined) {
          console.log("Drawing field")
          await drawFieldImage(packet.bgID, image)
        }
      } else {
        console.warn(`Background ID ${packet.bgID} not found in images`)
      }
    }
    for (const item of packet.items) {
      if (item.type == DrawablesTypes.CIRCLE) {
        console.log("rendering circle", item)
        drawCircle(
          new Distance(item.x),
          new Distance(item.y),
          new Distance(item.r),
          item.style.fill,
          item.style.outlineFill,
          new Distance(item.style.outlineWidth)
        )
      } else if (item.type == DrawablesTypes.RECTANGLE) {
        console.log("rendering rect", item)

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
        console.log("rendering line", item)

        drawLine(
          new Distance(item.x1),
          new Distance(item.y1),
          new Distance(item.x2),
          new Distance(item.y2),
          item.style.outlineFill,
          new Distance(item.style.outlineWidth)
        )
      } else if (item.type == DrawablesTypes.IMAGE) {
        console.log("rendering image", item)

        await drawBase64Image(
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
    manager.state.onChange(manager.PACKETS_KEY, async (newValue) => {
      packet = newValue
      await render()
    })
    manager.state.onChange(manager.IMAGES_KEY, async (newValue) => {
    console.log("Got images", newValue)
        if(Object.keys(newValue).length == 0) return
      images = newValue
      await render()
    })

    await render()
  })
</script>

<canvas bind:this={canvas}></canvas>
<p>Images: {Object.values(images).length}</p>
<!-- <p>{JSON.stringify(packet)}</p>-->

<!--<p>{JSON.stringify(images)}</p>-->

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
