<script lang="ts">
  import type { Snippet } from "svelte"

  let { children }: { children?: Snippet } = $props()
</script>

<section>
  {@render children?.()}
</section>

<style>
  section {
    margin: 1rem;
  }
</style>
