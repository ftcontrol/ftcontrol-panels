<script lang="ts">
  import { onMount } from "svelte"
  import Manager from "../manager"
  import type { GenericTypeJson } from "../types"
  import Field from "./Field.svelte"
  import { Toggle } from "ftc-panels"

  import { setContext } from "svelte"
  import Arrow from "./Arrow.svelte"

  let {
    manager,
  }: {
    manager: Manager
  } = $props()

  setContext("manager", manager)

  let configurables: Record<string, GenericTypeJson[]> = $state({})

  onMount(() => {
    manager.state.onChange(manager.INITIAL_CONFIGURABLES_KEY, (data) => {
      configurables = data
    })
  })
</script>

{#each Object.entries(configurables) as [key, value]}
  <Toggle>
    {#snippet trigger({ isOpen }: { isOpen: boolean })}
      <h3>
        <Arrow {isOpen} />
        {key.split(".")[key.split(".").length - 1]}
      </h3>
    {/snippet}
    {#snippet content({ close }: { close: () => void })}
      <div class="container">
        {#each value as item}
          <Field {item} />
        {/each}
      </div>
    {/snippet}
  </Toggle>
{/each}

<style>
  h3 {
    margin: 0;
    margin-bottom: var(--padding);
  }
  .container {
    display: flex;
    flex-direction: column;
    gap: var(--padding);
    margin-bottom: var(--padding);
  }
</style>
