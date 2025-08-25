<script lang="ts">
  import { onMount } from "svelte"
  import Timer from "./Timer.svelte"
  import type Manager from "../manager"
  import type { OpModeStatus } from "../manager"
  import { Overlay } from "ftc-panels"

  let {
    manager,
  }: {
    manager: Manager
  } = $props()

  function stop() {
    manager.socket.sendMessage("stopActiveOpMode", null)
  }

  let activeStatus: OpModeStatus = $state("STOPPED")

  let activeStartTimestamp: number | null = $state(null)
  let deltaTime: number | null = $state(null)

  onMount(() => {
    manager.state.onChange(manager.ACTIVE_OPMODE_STATUS_KEY, (value) => {
      activeStatus = value
    })
    manager.state.onChange(
      manager.ACTIVE_OPMODE_START_DELTA_MS_KEY,
      (value) => {
        deltaTime = value
      }
    )
    manager.state.onChange(
      manager.ACTIVE_OPMODE_START_TIMESTAMP_KEY,
      (value) => {
        activeStartTimestamp = value
      }
    )
  })

  let durations = $state([10 * 1000, 20 * 1000, 30 * 1000, 60 * 1000])
  let maxDuration = $state(durations[0])

  $effect(() => {
    if (
      activeStatus === "RUNNING" &&
      deltaTime != null &&
      maxDuration != null &&
      deltaTime > maxDuration &&
      isLinkedToOpmode
    ) {
      stop()
    }
  })

  function msToMinutesAndSeconds(ms: number | null): {
    minutes: number
    seconds: number
  } {
    if (ms == null) return { minutes: 0, seconds: 0 }
    const totalSeconds = Math.floor(ms / 1000)
    const minutes = Math.floor(totalSeconds / 60)
    const seconds = totalSeconds % 60

    return { minutes, seconds }
  }

  function msToMinutesAndSecondsString(ms: number | null): string {
    const data = msToMinutesAndSeconds(ms)
    return `${data.minutes}:${data.seconds}`
  }

  function chooseDuration(s: number, close: () => void) {
    const willStopNow =
      activeStatus === "RUNNING" && deltaTime != null && deltaTime > s
    close()

    if (willStopNow) {
      manager.notifications.addAction("This will immediately stop the opmode", [
        {
          text: "OK",
          task: () => {
            maxDuration = s
          },
        },
        {
          text: "Revert",
          task: () => {},
        },
      ])
    } else {
      maxDuration = s
    }
  }

  let isLinkedToOpmode = $state(false)
</script>

<div>
  <Timer />
  <button></button>
  <p>{isLinkedToOpmode ? "linket to opmode" : "standalone"}</p>
</div>

<p>
  {msToMinutesAndSecondsString(deltaTime)} / <Overlay>
    {#snippet trigger()}
      <button style="border: 1px solid currentColor;display: inline-block;"
        >{msToMinutesAndSecondsString(maxDuration)}</button
      >
    {/snippet}
    {#snippet overlay({ close }: { close: () => void })}
      {#each durations.filter((it) => it != maxDuration) as s}
        <button onclick={() => chooseDuration(s, close)}>
          {msToMinutesAndSecondsString(s)}
        </button>
      {/each}
    {/snippet}
  </Overlay>
</p>
