<script lang="ts">
  import { onMount } from "svelte"
  import "./global.css"
  import { GlobalSocket } from "ftc-panels/src/core/socket/global"
  import DynamicComponent from "ftc-panels/src/core/ui/DynamicComponent.svelte"
  import type { GenericData } from "ftc-panels/src/core/socket/types"
  import type { PluginInfo } from "ftc-panels/src/core/types"
  import { dev } from "$app/environment"

  let socket: GlobalSocket
  let plugins = $state<PluginInfo[]>([])

  async function getPluginsUntilReady(): Promise<string> {
    const url = dev ? "http://localhost:8001" : window.location.origin
    let attempts = 0
    let maxAttempts = 20

    while (attempts < maxAttempts) {
      try {
        const response = await fetch(`${url}/plugins`)
        const text = await response.text()

        if (text && text.trim() != "null") {
          return text
        }
      } catch (err) {
        console.warn("Fetch failed, retrying...", err)
      }

      attempts++
      await new Promise((resolve) => setTimeout(resolve, 500))
    }

    throw new Error("Failed to get plugins after multiple attempts.")
  }

  onMount(async () => {
    try {
      const data = await getPluginsUntilReady()

      plugins = JSON.parse(data).data.plugins

      socket = new GlobalSocket()
      socket.init(plugins)
    } catch (e) {
      console.error(e)
    }

    return () => {
      socket?.close()
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
      globalSocket={socket}
      textContent={widget.textContent || ""}
      id={plugin.details.id}
    />
  {/each}
{/each}
