<script lang="ts">
  import { onMount } from "svelte"
  import "./global.css"
  import { GlobalSocket } from "ftc-panels/src/core/socket/global"
  import DynamicComponent from "ftc-panels/src/core/ui/DynamicComponent.svelte"

  let socket = new GlobalSocket()

  onMount(() => {
    socket.init()

    return () => {
      socket.close()
    }
  })
</script>

<h1>Hi!</h1>

<DynamicComponent
  globalSocket={socket}
  id="com.bylazar.opmodecontrol"
  file="opmode"
/>

<h1>Socket Messages</h1>
<ul>
  {#each socket.log as message}
    <li>{message}</li>
  {/each}
</ul>
