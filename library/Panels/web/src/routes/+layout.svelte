<script lang="ts">
  import { onDestroy, onMount, type Snippet } from "svelte"
  import "./global.css"
  import Topbar from "$lib/Topbar.svelte"
  import { global, notifications } from "$lib"
  import NotificationsUi from "$lib/NotificationsUI.svelte"

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
