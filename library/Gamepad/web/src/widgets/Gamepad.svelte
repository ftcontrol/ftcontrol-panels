<script lang="ts">
  import type Manager from "../manager"
  import GamepadDrawing from "./GamepadDrawing.svelte"

  let {
    manager,
  }: {
    manager: Manager
  } = $props()
</script>

<GamepadDrawing
  gamepad={{
    l1: true,
  }}
/>

<style>
</style>
