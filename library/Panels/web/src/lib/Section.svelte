<script lang="ts">
  import type { Snippet } from "svelte"

  let { children, margin = false }: { children?: Snippet; margin?: boolean } =
    $props()
</script>

<section class:margin>
  {@render children?.()}
</section>

<style>
  section {
    background-color: var(--bgMedium);
    padding: var(--padding);
    border-radius: 1rem;
    overflow: auto;
  }
  section.margin {
    margin-bottom: calc(var(--padding) / 2);
  }
</style>
