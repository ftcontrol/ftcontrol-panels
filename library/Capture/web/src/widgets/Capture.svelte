<script lang="ts">
  import { Button, Overlay } from "ftc-panels"
  import type Manager from "../manager"
  import { onDestroy, onMount } from "svelte"
  import { PluginStateManager, type PluginValue } from "ftc-panels"

  const speeds = ["0.1x", "0.25x", "0.5x", "1x", "2x", "3x", "4x"]

  let playbackSpeed: string = $state("1x")

  $effect(() => {
    if (playbackSpeed == null) return
    if (isForwarding) {
      startingTimestamp = timestamp
      startedForwardingAt = performance.now()
    }
  })

  let animationFrame: number | null = null
  let startedForwardingAt: number | null = null
  let startingTimestamp: number = 0

  function updatePlayback(time: number) {
    if (!isForwarding || startedForwardingAt === null) return

    const elapsed = time - startedForwardingAt
    const speedMultiplier = parseFloat(playbackSpeed.replace("x", ""))
    timestamp = Math.min(
      startingTimestamp + elapsed * speedMultiplier,
      duration
    )

    if (timestamp < duration) {
      animationFrame = requestAnimationFrame(updatePlayback)
    } else {
      timestamp = duration
      isForwarding = false
      animationFrame = null
      startedForwardingAt = null
    }
  }

  function togglePlayback() {
    isForwarding = !isForwarding

    if (isForwarding) {
      startingTimestamp = timestamp
      startedForwardingAt = performance.now()
      animationFrame = requestAnimationFrame(updatePlayback)
    } else {
      startedForwardingAt = null
      if (animationFrame !== null) {
        cancelAnimationFrame(animationFrame)
        animationFrame = null
      }
    }
  }

  onDestroy(() => {
    if (animationFrame !== null) {
      cancelAnimationFrame(animationFrame)
    }
  })

  let {
    manager,
  }: {
    manager: Manager
  } = $props()

  let ids = manager.settings["supportedPlugins"]

  type PluginData = Record<string, PluginValue>

  type LogEntry = {
    timestamp: number
    plugins: Record<string, PluginData>
  }

  let log: LogEntry[] = $state([])

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
    const all = Object.keys(manager.socket.socket.pluginManagers)
    ids = ids.filter((it) => all.includes(it))

    const interval = setInterval(() => {
      if (isCapturing) {
        const data: LogEntry = {
          timestamp: Date.now(),
          plugins: {},
        }
        for (const id of ids) {
          data.plugins[id] = getData(
            manager.socket.socket.pluginManagers[id].state.data
          )
        }
        log.push(data)
      }
      if (isPlaying || isForwarding) {
        for (const id of ids) {
          applyData(
            manager.socket.socket.pluginManagers[id].state,
            entry.plugins[id]
          )
        }
      }
    }, 50)

    return () => {
      clearInterval(interval)
    }
  })

  let isCapturing = $state(false)
  let isPlaying = $state(false)
  let isForwarding = $state(false)

  let hasRecording = $derived<boolean>(log.length > 0)

  let startTimestamp = $derived.by(() => {
    if (log.length == 0) return 0
    return log[0].timestamp
  })

  let endTimestamp = $derived.by(() => {
    if (log.length == 0) return 0
    return log[log.length - 1].timestamp
  })

  let duration = $derived(endTimestamp - startTimestamp)

  let timestamp = $state(0)

  let entry: LogEntry = $derived.by(() => {
    var answerIndex = 0
    var index = 0
    for (const entry of log) {
      if (entry.timestamp <= timestamp + startTimestamp) {
        answerIndex = index
      } else {
        return log[answerIndex]
      }
      index++
    }

    return log[log.length - 1] || null
  })
</script>

<p>Got {log.length} entries</p>
<p>
  Timestamp: {(timestamp / 1000).toFixed(2)}s / {(duration / 1000).toFixed(2)}s
</p>

<div class="flex-item">
  <p>Animation speed</p>

  <Overlay>
    {#snippet trigger()}
      <button style="border: 1px solid currentColor">{playbackSpeed}</button>
    {/snippet}
    {#snippet overlay({ close }: { close: () => void })}
      {#each speeds.filter((it) => it != playbackSpeed) as s}
        <button
          onclick={() => {
            playbackSpeed = s
            close()
          }}>{s}</button
        >
      {/each}
    {/snippet}
  </Overlay>
</div>

<input
  disabled={!hasRecording}
  bind:value={timestamp}
  type="range"
  min={0}
  max={duration}
/>

<div class="flex">
  <Button
    onclick={() => {
      isCapturing = true
    }}
    disabled={isCapturing}>Start</Button
  >
  <Button
    onclick={() => {
      isCapturing = false
    }}
    disabled={!isCapturing}>Stop</Button
  >
  <Button
    onclick={() => {
      isCapturing = false
      log = []
      timestamp = 0
      isPlaying = false
    }}
    disabled={!hasRecording}>Reset</Button
  >
</div>
<div class="flex">
  <Button
    onclick={() => {
      isPlaying = !isPlaying
    }}
    disabled={!hasRecording}
    >{isPlaying ? "Disable" : "Enable"} Visual Sync</Button
  >
  <Button onclick={togglePlayback} disabled={!hasRecording}>
    {isForwarding ? "Stop" : "Start"} Playing
  </Button>
</div>

<style>
  .flex-item {
    display: flex;
    gap: 1rem;
    align-items: center;
  }
  .flex {
    display: flex;
    gap: 1rem;
    justify-content: space-between;
    margin-block: 1rem;
  }
  button {
    all: unset;
    cursor: pointer;
    padding: 0.25em 1em;
  }
</style>
