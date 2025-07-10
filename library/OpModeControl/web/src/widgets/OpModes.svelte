<script lang="ts">
  import { onMount } from "svelte"
  import Manager from "../manager"

  let {
    manager,
  }: {
    manager: Manager
  } = $props()
</script>

<h1>Hi!</h1>
