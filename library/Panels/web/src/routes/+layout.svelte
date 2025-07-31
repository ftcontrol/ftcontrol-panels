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
    {#each global.allTemplates as t}
      <CanvasRender {t} pID={t.pluginID} show={false} />
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
