<script lang="ts">
  import { onMount } from "svelte"
  import Manager from "../manager"

  let {
    manager,
  }: {
    manager: Manager
  } = $props()

  let lights: LightObject[] = $state([])

  onMount(() => {
    manager.state.onChange(manager.LIGHTS_KEY, (newValue) => {
      lights = newValue
    })
  })
</script>

{#each lights as light}
  <p>{light.id} {light.type} {light.value}</p>
{/each}
{#if lights.length == 0}
  <p>No lights found.</p>
{/if}

<style>
  p {
    padding-block: 0.25rem;
  }
</style>
