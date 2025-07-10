<script lang="ts">
  import { onMount } from "svelte"
  import "./global.css"
  import { GlobalSocket } from "ftc-panels/src/core/socket/global"
  import DynamicComponent from "ftc-panels/src/core/ui/DynamicComponent.svelte"
  import ThemeGenerator from "$lib/ThemeGenerator.svelte"
  import Panels from "$lib/Panels.svelte"
  import type { GenericData } from "ftc-panels/src/core/socket/types"
  import type { PluginInfo } from "ftc-panels/src/core/types"
  import { dev } from "$app/environment"
  import Topbar from "$lib/Topbar.svelte"
  import { global } from "$lib/index.svelte"

  import Overlay from "ftc-panels/src/core/ui/Overlay.svelte"

  onMount(async () => {
    global.init()

    return () => {
      global.close()
    }
  })
</script>

<Topbar />

<h1>Hi!</h1>
{#each global.plugins as plugin}
  <div
    style="background-color:var(--bgLight);padding:0.5rem;margin-bottom:1rem;"
  >
    <h2>{plugin.details.name}</h2>
    <p>{plugin.details.id}</p>
    <p>{plugin.details.author}</p>
  </div>
  {#each plugin.details.widgets as widget}
    <DynamicComponent
      globalSocket={global.socket}
      textContent={widget.textContent || ""}
      id={plugin.details.id}
    />
  {/each}
{/each}
<ThemeGenerator />

<Overlay>
  {#snippet trigger({isOpen})}
    Lazar: {isOpen}
  {/snippet}
  {#snippet overlay({close})}
    <h1>Hi</h1>
    <button onclick={close}>Close</button>
  {/snippet}
</Overlay>
