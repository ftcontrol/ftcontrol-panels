<script lang="ts">
  import { onMount } from "svelte"
  import Manager from "../manager"
  import type { GenericTypeJson } from "../types"

  let {
    manager,
  }: {
    manager: Manager
  } = $props()

  let configurables: Record<string, GenericTypeJson[]> = $state({})

  onMount(() => {
    manager.state.onChange(manager.INITIAL_CONFIGURABLES_KEY, (data) => {
      configurables = data
    })
  })
</script>

<h1>Test 5</h1>
{#each Object.entries(configurables) as [key, value]}
  <div>
    <h3>{key}</h3>
    <ul>
      {#each value as item}
        <li>{JSON.stringify(item)}</li>
      {/each}
    </ul>
  </div>
{/each}

<style>
</style>
