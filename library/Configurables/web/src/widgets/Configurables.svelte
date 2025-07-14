<script lang="ts">
  import { onMount } from "svelte"
  import Manager from "../manager"
  import type { GenericTypeJson } from "../types"
  import Field from "./Field.svelte"

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

{#each Object.entries(configurables) as [key, value]}
  <div>
    <h3>{key}</h3>
    {#each value as item}
      <Field {item} />
    {/each}
  </div>
{/each}

<style>
</style>
