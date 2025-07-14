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
    padding: 1rem;
    border-radius: 1rem;
    overflow: auto;
  }
</style>
