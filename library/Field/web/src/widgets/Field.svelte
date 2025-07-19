<script lang="ts">
  import { onMount } from "svelte"
  import Manager from "../manager"

  let {
    manager,
  }: {
    manager: Manager
  } = $props()

  let packet = $state({})
  let images = {}

  onMount(() => {
    manager.state.onChange(manager.PACKETS_KEY, (newValue) => {
      packet = newValue
    })
    manager.state.onChange(manager.IMAGES_KEY, (newValue) => {
      images = newValue
    })
  })
</script>

<p>{JSON.stringify(packet)}</p>

<canvas></canvas>

<style>
  canvas {
    border: 1px solid currentColor;
    aspect-ratio: 1 / 1;
  }
</style>
