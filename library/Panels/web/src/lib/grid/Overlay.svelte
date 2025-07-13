<script lang="ts">
  let { x, y }: { x: number; y: number } = $props()
</script>

<div style="--x:{x};--y:{y};"></div>

<style>
  div {
    position: absolute;
    outline: 1px solid black;
    top: calc(var(--y) * var(--height));
    left: calc(var(--x) * var(--width));
    height: calc(var(--height));
    width: calc(var(--width));
  }
</style>
