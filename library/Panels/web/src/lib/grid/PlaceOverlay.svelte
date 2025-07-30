<script lang="ts">
  import { getContext } from "svelte"
  import type { Manager } from "./widgets.svelte"
  const manager = getContext("manager") as Manager
</script>

{#if manager.place != null}
  <div
    class:invalid={!manager.isValid}
    style="--x:{manager.place.x};--y:{manager.place.y};--w:{manager.place
      .w};--h:{manager.place.h};"
  ></div>
{/if}

<style>
  div {
    position: absolute;
    background-color: var(--bgLight);
    color: inherit;
    border-radius: 1rem;
    border: 1px solid var(--bgLight);
    top: calc(var(--y) * var(--height));
    left: calc(var(--x) * var(--width));
    height: calc(var(--height) * var(--h));
    width: calc(var(--width) * var(--w));
  }
  div.invalid {
    background-color: red;
  }
</style>
