<script lang="ts">
  import { onMount } from "svelte"
  import Manager from "../manager"
  import { Overlay, Button } from "ftc-panels"

  let {
    manager,
  }: {
    manager: Manager
  } = $props()

  let lines: string[] = $state([])

  onMount(() => {
    manager.state.onChange(manager.PACKETS_KEY, (newValue) => {
      lines = newValue
    })
  })
</script>

{#each lines as line}
  <p>{line}</p>
{/each}
{#if lines.length == 0}
  <p>No telemetry found.</p>
{/if}

<style>
  p {
    color: red;
  }
</style>
