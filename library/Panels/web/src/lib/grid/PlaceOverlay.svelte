<script lang="ts">
  import { manager } from "./widgets.svelte"
</script>

{#if manager.place != null}
  <div
    style="--x:{manager.place.x};--y:{manager.place.y};--w:{manager.place
      .w};--h:{manager.place.h};"
  ></div>
{/if}

<style>
  div {
    position: absolute;
    background-color: red;
    color: inherit;
    border: 1px solid var(--bgLight);
    top: calc(var(--y) * var(--height));
    left: calc(var(--x) * var(--width));
    height: calc(var(--height) * var(--h));
    width: calc(var(--width) * var(--w));
  }
</style>
