<script lang="ts">
  import { onMount } from "svelte"
  import Manager from "../manager"
  import type { OpMode } from "../types"

  let {
    manager,
  }: {
    manager: Manager
  } = $props()

  let opModes: OpMode[] = $state([])
  onMount(() => {
    manager.onOpModesChange((value) => {
      opModes = value
    })
  })
</script>

<h1>Hi!</h1>

{JSON.stringify(opModes)}

{#each opModes as opMode}
  <p>{opMode.name} / {opMode.group}</p>
  <p>{opMode.flavour} / {opMode.source}</p>
{/each}
