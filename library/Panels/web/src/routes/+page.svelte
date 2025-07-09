<script lang="ts">
  import { onMount } from "svelte"
  import "./global.css"
  import { GlobalSocket } from "ftc-panels/src/core/socket/global"
  import DynamicComponent from "ftc-panels/src/core/ui/DynamicComponent.svelte"
  import type { GenericData } from "ftc-panels/src/core/socket/types"
  import type { PluginInfo } from "ftc-panels/src/core/types"
  import { dev } from "$app/environment"

  let socket = new GlobalSocket()
  let plugins = $state<PluginInfo[]>([])

  onMount(() => {
    socket.init()

    socket.addMessageHandler("core", "pluginsDetails", (data: GenericData) => {
      plugins = data.plugins
    })

    return () => {
      socket.close()
    }
  })
</script>

<h1>Hi!</h1>
{#each plugins as plugin}
  <h2>{plugin.details.name}</h2>
  <p>{plugin.details.id}</p>
  <p>{plugin.details.author}</p>
  {#each plugin.details.widgets as widget}
    <DynamicComponent
      isDev={dev}
      globalSocket={socket}
      textContent={widget.textContent}
      id={plugin.details.id}
    />
  {/each}
{/each}

<h1>Socket Messages</h1>
<ul>
  {#each socket.log as message}
    <li>{message}</li>
  {/each}
</ul>
