<script lang="ts">
  import { setContext, type Snippet } from "svelte"
  import Topbar from "$lib/Topbar.svelte"
  import { global } from "$lib"
  import "./global.css"

  import NotificationsUi from "$lib/NotificationsUI.svelte"
  import { manager } from "$lib/grid/widgets.svelte"

  setContext("manager", manager.manager)

  let { children }: { children?: Snippet } = $props()
</script>

<NotificationsUi />
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
</style>
