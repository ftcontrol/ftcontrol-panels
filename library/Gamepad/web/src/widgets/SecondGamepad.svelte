<script lang="ts">
  import type Manager from "../manager"
  import GenericGamepad from "./GenericGamepad.svelte"

  let {
    manager,
  }: {
    manager: Manager
  } = $props()
</script>

<section>
    <GenericGamepad {manager} gamepadIndex={1} />
</section>

<style>
    section {
        width: 100%;
        display: flex;
    }
</style>