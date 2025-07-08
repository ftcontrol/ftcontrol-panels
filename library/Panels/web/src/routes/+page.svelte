<script lang="ts">
  import { onMount } from "svelte"
  import "./global.css"
  import { testVariable } from "ftc-panels/src/core"

  let messages: string[] = $state([])

  let socket: WebSocket

  onMount(() => {
    const host = window.location.hostname
    const wsUrl = `ws://${host}:8002`

    socket = new WebSocket(wsUrl)

    socket.onopen = () => {
      console.log("WebSocket connection opened:", wsUrl)
    }

    socket.onmessage = (event) => {
      messages = [...messages, event.data]
    }

    socket.onerror = (error) => {
      console.error("WebSocket error:", error)
    }

    socket.onclose = () => {
      console.log("WebSocket connection closed")
    }

    return () => {
      socket.close()
    }
  })
</script>

<h1>Hi!</h1>

<h1>Socket Messages</h1>
{testVariable}
<ul>
  {#each messages as message}
    <li>{message}</li>
  {/each}
</ul>
