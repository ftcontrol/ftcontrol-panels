<script lang="ts">
  import { onMount } from "svelte"
  import Manager from "../manager"

  let {
    manager,
  }: {
    manager: Manager
  } = $props()

  let packet = $state({})

  onMount(() => {
    manager.state.onChange(manager.PACKETS_KEY, (newValue) => {
      packet = newValue
    })
  })
</script>

<p>{JSON.stringify(packet)}</p>

<style>
</style>
