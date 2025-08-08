<script lang="ts">
  import type Manager from "../manager"
  import GenericGamepad from "./GenericGamepad.svelte"

  let {
    manager,
  }: {
    manager: Manager
  } = $props()
</script>

<GenericGamepad {manager} gamepadIndex={0} />
