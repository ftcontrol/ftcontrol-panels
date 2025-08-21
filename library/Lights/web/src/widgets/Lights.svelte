<script lang="ts">
  import { onMount } from "svelte"
  import Manager from "../manager"
  import { hueToHex } from "./hue"
  import { LightType, type LightObject } from "../types"
  import Light from "./Light.svelte"

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

<div>
  {#each lights as light}
    {#if light.type == LightType.RGB_INDICATOR}
      <Light hex={hueToHex(light.value)} type={light.type} id={light.id} />
    {:else}
      <Light
        hex={light.value ? "#ffffff" : "#000000"}
        type={light.type}
        id={light.id}
      />
    {/if}
  {/each}
  {#if lights.length == 0}
    <p>No lights found.</p>
  {/if}
</div>

<style>
  p {
    padding-block: 0.25rem;
  }
  div {
    display: flex;
    gap: var(--padding);
  }
</style>
