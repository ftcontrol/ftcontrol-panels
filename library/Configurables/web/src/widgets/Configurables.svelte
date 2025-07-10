<script lang="ts">
  import { onMount } from "svelte"
  import Manager from "../manager"

  let {
    manager,
  }: {
    manager: Manager
  } = $props()

  let configurables = $state([])

  onMount(() => {
    manager.state.onChange(manager.INITIAL_CONFIGURABLES_KEY, (data) => {
      configurables = data
    })
  })
</script>

{JSON.stringify(configurables)}

<style>
</style>
