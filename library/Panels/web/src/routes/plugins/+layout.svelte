<script lang="ts">
  import type { Snippet } from "svelte"

  let { children }: { children?: Snippet } = $props()
</script>

<section>
  {@render children?.()}
</section>

<style>
  section {
    margin: 0.5rem;
  }
</style>
