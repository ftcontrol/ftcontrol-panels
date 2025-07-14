<script lang="ts">
  import type { Snippet } from "svelte"

  let { children, scale = 1 }: { children?: Snippet; scale: number } = $props()
</script>

<div class="preview-box" style="--scale:{scale};">
  <div class="scaled-preview">
    {@render children?.()}
  </div>
</div>

<style>
  .preview-box {
    width: 200px;
    height: 120px;
    overflow: hidden;
    border: 1px solid #ccc;
    border-radius: 8px;
    pointer-events: none;
    position: relative;
    padding: var(--padding);
  }

  .scaled-preview {
    transform: scale(var(--scale));
    transform-origin: top left;
    width: calc(100% / var(--scale));
    height: calc(100% / var(--scale));
    pointer-events: none;
  }
</style>
