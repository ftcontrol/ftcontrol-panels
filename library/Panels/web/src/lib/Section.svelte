<script lang="ts">
  import type { Snippet } from "svelte"

  let { children, margin = false, transparent = false }: { children?: Snippet; margin?: boolean; transparent?: boolean } =
    $props()
</script>

<section class:margin class:transparent>
  {@render children?.()}
</section>

<style>
  section {
    background-color: var(--bgMedium);
    padding: var(--padding);
    border-radius: 1rem;
    overflow: auto;
    position: relative;
  }
  section.transparent {
    background-color: transparent;
  }
  section.margin {
    margin-bottom: calc(var(--padding) / 2);
  }
</style>
