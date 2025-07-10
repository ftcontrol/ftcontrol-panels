<script lang="ts">
  import { onMount } from "svelte"
  import Manager from "../manager"
  import type { OpMode } from "../types"
  import Overlay from "ftc-panels/src/core/ui/Overlay.svelte"

  let {
    manager,
  }: {
    manager: Manager
  } = $props()

  let opModes: OpMode[] = $state([])
  onMount(() => {
    manager.state.onChange(manager.OPMODES_KEY, (value) => {
      opModes = value
    })
  })
</script>

<h1>Hi!</h1>

<Overlay>
  {#snippet trigger()}
    Autos
  {/snippet}
  {#snippet overlay()}
    {#each opModes.filter((it) => it.flavour == "AUTONOMOUS") as opMode}
      <p>{opMode.name} / {opMode.group}</p>
      <p>{opMode.flavour} / {opMode.source}</p>
    {/each}
    {#if opModes.filter((it) => it.flavour == "AUTONOMOUS").length == 0}
      <p>No OpModes found.</p>
    {/if}
  {/snippet}
</Overlay>

<Overlay>
  {#snippet trigger()}
    TeleOps
  {/snippet}
  {#snippet overlay()}
    {#each opModes.filter((it) => it.flavour == "TELEOP") as opMode}
      <p>{opMode.name} / {opMode.group}</p>
      <p>{opMode.flavour} / {opMode.source}</p>
    {/each}
    {#if opModes.filter((it) => it.flavour == "TELEOP").length == 0}
      <p>No OpModes found.</p>
    {/if}
  {/snippet}
</Overlay>
