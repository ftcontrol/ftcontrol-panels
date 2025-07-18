<script lang="ts">
  import { Button } from "ftc-panels"
  import type Manager from "../manager"
  import { onMount } from "svelte"
  import { PluginStateManager, type PluginValue } from "ftc-panels"

  let {
    manager,
  }: {
    manager: Manager
  } = $props()

  const ids = ["com.bylazar.telemetry"]

  let log: Record<string, Record<string, PluginValue>>[] = $state([])

  function getData(data: Record<string, PluginValue>): Record<string, any> {
    const result: Record<string, any> = {}
    for (const key of Object.keys(data)) {
      result[key] = data[key].value
    }
    return result
  }

  function applyData(manager: PluginStateManager, data: Record<string, any>) {
    for (const key of Object.keys(data)) {
      manager.update(key, data[key])
    }
  }

  onMount(() => {
    console.log(Object.keys(manager.socket.socket.pluginManagers))

    const interval = setInterval(() => {
      if (isCapturing) {
        const data: Record<string, PluginValue> = {}
        for (const id of ids) {
          data[id] = getData(
            manager.socket.socket.pluginManagers[id].state.data
          )
        }
        log.push(data)
      }
      if (isPlaying) {
        for (const id of ids) {
          applyData(
            manager.socket.socket.pluginManagers[id].state,
            log[index][id]
          )
        }
      }
    }, 100)

    return () => {
      clearInterval(interval)
    }
  })

  let isCapturing = $state(false)
  let isPlaying = $state(false)

  let index = $state(0)
</script>

<p>{log.length}</p>
<Button
  onclick={() => {
    isCapturing = !isCapturing
  }}
>
  {isCapturing ? "Stop" : "Start"}
</Button>
<Button
  onclick={() => {
    isPlaying = !isPlaying
  }}
>
  {isPlaying ? "Stop" : "Play"}
</Button>
<input type="number" min={0} max={log.length} bind:value={index} />

<style>
</style>
