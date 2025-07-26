<script lang="ts">
  import type Manager from "../manager"
  import GamepadDrawing from "./GamepadDrawing.svelte"

  let {
    manager,
  }: {
    manager: Manager
  } = $props()

  let g = $state({
    l1: false,
    l2: false,
    r1: false,
    r2: false,
    leftStick: {
      x: 0,
      y: 0,
      value: false,
    },
    rightStick: {
      x: 0,
      y: 0,
      value: false,
    },
    cross: false,
    circle: false,
    square: false,
    triangle: false,

    dpad_up: false,
    dpad_left: false,
    dpad_right: false,
    dpad_down: false,

    touchpad: false,
    options: false,
    share: false,
    ps: false,
  })
</script>

<GamepadDrawing bind:gamepad={g} />

<style>
</style>
