<script lang="ts">
  import { onMount } from "svelte"
  import type Manager from "../manager"
  import GamepadDrawing from "./GamepadDrawing.svelte"
  import type { Gamepad } from "../types"

  let {
    manager,
  }: {
    manager: Manager
  } = $props()

  function hasChange(g: Gamepad) {
    if (g.options) return true
    return false
  }

  onMount(() => {
    const interval = setInterval(() => {
      if (!hasChange(g)) return
      manager.socket.sendMessage("gamepad", g)
    }, 50)

    return () => clearInterval(interval)
  })

  $effect(() => {
    if (!hasChange(g)) return
    manager.socket.sendMessage("gamepad", g)
  })

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

<section>
  {#if g.l1}
    <div>L1</div>
  {/if}
  {#if g.l2}
    <div>L2</div>
  {/if}
  {#if g.r1}
    <div>R1</div>
  {/if}
  {#if g.r2}
    <div>R2</div>
  {/if}

  {#if g.dpad_left}
    <div>←</div>
  {/if}
  {#if g.dpad_right}
    <div>→</div>
  {/if}
  {#if g.dpad_up}
    <div>↑</div>
  {/if}
  {#if g.dpad_down}
    <div>↓</div>
  {/if}

  {#if g.circle}
    <div>●</div>
  {/if}
  {#if g.cross}
    <div>✚</div>
  {/if}
  {#if g.triangle}
    <div>▲</div>
  {/if}
  {#if g.square}
    <div>■</div>
  {/if}

  {#if g.share}
    <div>SHR</div>
  {/if}
  {#if g.options}
    <div>OPTS</div>
  {/if}
  {#if g.ps}
    <div>PS</div>
  {/if}
  {#if g.touchpad}
    <div>touchpad</div>
  {/if}

  {#if g.leftStick.value || g.leftStick.x || g.leftStick.y}
    <div>L STICK</div>
  {/if}
  {#if g.rightStick.value || g.rightStick.x || g.rightStick.y}
    <div>R STICK</div>
  {/if}
</section>

<style>
  div {
    outline: 1px solid currentColor;
    padding: 0.5em 0.75em;
  }
  section {
    position: absolute;
    left: var(--padding);
    bottom: var(--padding);
  }
</style>
