<script lang="ts">
  import { Button, type PluginInfo } from "ftc-panels"
  import type Manager from "../manager"

  let {
    info,
    manager,
  }: {
    info: PluginInfo
    manager: Manager
  } = $props()

  import { onMount } from "svelte"

  let last = $state(-1)

  onMount(() => {
    manager.sendRequest()

    setTimeout(() => {
      manager.sendRequest()
    }, 500)
    setTimeout(() => {
      manager.sendRequest()
    }, 1000)
    setTimeout(() => {
      manager.sendRequest()
    }, 2000)
    setTimeout(() => {
      manager.sendRequest()
    }, 4000)

    const interval = setInterval(() => {
      manager.sendRequest()
    }, 5000)

    manager.state.onChange(manager.LAST_PING_KEY, (data) => {
      last = data
    })

    return () => {
      clearInterval(interval)
    }
  })
</script>

<p>
  {last}ms
</p>

<style>
  p {
    margin: 0;
  }
</style>
