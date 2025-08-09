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
  <GenericGamepad {manager} gamepadIndex={0} />
  <GenericGamepad {manager} gamepadIndex={1} />
</section>

<style>
  section {
    display: flex;
    gap: var(--padding);
    height: 100%;
    flex-wrap: wrap;
  }
</style>
