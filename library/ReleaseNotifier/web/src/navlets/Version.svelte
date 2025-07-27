<script lang="ts">
  import type Manager from "../manager"
  import { onMount } from "svelte"

  let {
    manager,
  }: {
    manager: Manager
  } = $props()

  let battery = $state(-1)

  onMount(() => {
    manager.state.onChange(manager.BATTERY_KEY, (data: number) => {
      battery = data
    })
  })
</script>

<p>Battery {battery}V</p>

<style>
  p {
    text-wrap: nowrap;
  }
</style>
