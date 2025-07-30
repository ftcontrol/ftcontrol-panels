<script lang="ts">
  import { setContext, type Snippet } from "svelte"
  import Topbar from "$lib/Topbar.svelte"
  import { global } from "$lib"
  import "./global.css"

  import NotificationsUi from "$lib/NotificationsUI.svelte"
  import { manager } from "$lib/grid/widgets.svelte"
  import CanvasRender from "$lib/grid/CanvasRender.svelte"

  setContext("manager", manager.manager)

  let { children }: { children?: Snippet } = $props()
</script>

<NotificationsUi />
<section>
  <Topbar />

  {#if global.isPrepared}
    {@render children?.()}
  {/if}

  {#if global.isConnected}
    {#each global.plugins as p}
      {#each p.details.templates as t}
        <CanvasRender {t} pID={p.details.id} show={false} />
      {/each}
    {/each}
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
</style>
