<script lang="ts">
  import { onMount } from "svelte"
  import { PluginSocket } from "ftc-panels/src/core/socket/plugin"

  let {
    socket,
  }: {
    socket: PluginSocket
  } = $props()

  onMount(() => {
    socket.addMessageHandler("time", (data) => {
      console.log("Data form plugin", data)
    })
  })
</script>

<h1>Hi!</h1>
