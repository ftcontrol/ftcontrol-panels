<script lang="ts">
  import "./global.css"
  import type {Snippet} from "svelte";

  let { children }: { children?: Snippet } = $props()
</script>

<section>
    {@render children?.()}
</section>

<style>
  section {
    overflow-y: auto;
    width: 100vw;
    height: 100vh;
    display: flex;
    flex-direction: column;
  }
</style>
