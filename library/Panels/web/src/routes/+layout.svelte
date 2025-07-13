<script lang="ts">
  import { onDestroy, onMount, type Snippet } from "svelte"
  import "./global.css"
  import Topbar from "$lib/Topbar.svelte"
  import { global } from "$lib"

  let { children }: { children?: Snippet } = $props()

  onMount(() => {
    global.init()
    setTimeout(() => {
      console.log(
        global.plugins.map((it) => it.details.widgets.map((w) => w.name))
      )
    }, 1000)
  })

  onDestroy(() => {
    global.close()
  })
</script>

<section>
  <Topbar />

  {#if global.isConnected}
    {@render children?.()}
  {/if}
</section>

<style>
  section {
    overflow-y: auto;
    width: 100vw;
    height: 100vh;
    display: flex;
    flex-direction: column;
  }
  div {
    background-color: yellow;
  }
</style>
