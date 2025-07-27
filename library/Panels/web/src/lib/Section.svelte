<script lang="ts">
  import type { Snippet } from "svelte"

  let { children }: { children?: Snippet } = $props()
</script>

<section>
  {@render children?.()}
</section>

<style>
  section {
    background-color: var(--bgMedium);
    padding: var(--padding);
    border-radius: 1rem;
    overflow: auto;
    margin-bottom: calc(var(--padding) / 2);
  }
</style>
