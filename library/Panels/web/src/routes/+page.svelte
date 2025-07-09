<script lang="ts">
  import { onMount } from "svelte"
  import "./global.css"
  import { GlobalSocket } from "ftc-panels/src/core/socket/global"

  let socket = new GlobalSocket()

  onMount(() => {
    socket.init()

    return () => {
      socket.close()
    }
  })
</script>

<h1>Hi!</h1>

<h1>Socket Messages</h1>
<ul>
  {#each socket.log as message}
    <li>{message}</li>
  {/each}
</ul>
