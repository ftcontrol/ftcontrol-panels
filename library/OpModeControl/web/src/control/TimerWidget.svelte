<script lang="ts">
  import { onMount } from "svelte"
  import Timer from "./Timer.svelte"
  import type Manager from "../manager"
  import type { OpModeStatus } from "../manager"
  import { Overlay, Button } from "ftc-panels"

  let { manager }: { manager: Manager } = $props()

  function stop() {
    manager.socket.sendMessage("stopActiveOpMode", null)
  }

  let activeStatus: OpModeStatus = $state("STOPPED")
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
  })

  let durations = $state([10_000, 20_000, 30_000, 60_000, 120_000])
  let maxDuration = $state(durations[2])

  let isLinkedToOpmode = $state(false)

  $effect(() => {
    if (
      isLinkedToOpmode &&
      activeStatus === "RUNNING" &&
      deltaTime != null &&
      maxDuration != null &&
      deltaTime > maxDuration
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
    const { minutes, seconds } = msToMinutesAndSeconds(ms)
    return `${minutes}:${String(seconds).padStart(2, "0")}`
  }

  function chooseDuration(s: number, close: () => void) {
    if (activeStatus === "RUNNING" && isLinkedToOpmode) return

    const willStopNow =
      isLinkedToOpmode &&
      activeStatus === "RUNNING" &&
      deltaTime != null &&
      deltaTime > s

    close()

    if (willStopNow) {
      manager.notifications.addAction("This will immediately stop the opmode", [
        {
          text: "OK",
          task: () => {
            maxDuration = s
          },
        },
        { text: "Revert", task: () => {} },
      ])
    } else {
      maxDuration = s
    }
  }

  function onToggleLinked() {
    if (activeStatus === "RUNNING") {
      manager.notifications.add(
        "You can’t change linking while the opmode is RUNNING. Stop it first."
      )
      return
    }
    isLinkedToOpmode = !isLinkedToOpmode
  }

  const canChangeLinked = () => activeStatus !== "RUNNING"
  const canChangeDuration = () =>
    !(activeStatus === "RUNNING" && isLinkedToOpmode)
</script>

<nav>
  <span class="timer">
    <span class="current-time">
      {msToMinutesAndSecondsString(deltaTime)}
    </span>
    <div class="separator"></div>
    <Overlay>
      {#snippet trigger()}
        <button
          class="current-max-time"
          class:disabled={!canChangeDuration()}
          aria-disabled={!canChangeDuration()}
        >
          {msToMinutesAndSecondsString(maxDuration)}
        </button>
      {/snippet}
      {#snippet overlay({ close }: { close: () => void })}
        {#each durations.filter((it) => it !== maxDuration) as s}
          <button
            class="menu"
            onclick={() => chooseDuration(s, close)}
            disabled={!canChangeDuration()}
          >
            {msToMinutesAndSecondsString(s)}
          </button>
        {/each}
      {/snippet}
    </Overlay>
  </span>

  <div class="status">
    <span class="dot" data-state={activeStatus}></span>
    <span class="label">{activeStatus}</span>
  </div>
</nav>

<div class="controls">
  <Button disabled={!canChangeLinked()} onclick={onToggleLinked}>
    <Timer />
    {isLinkedToOpmode ? "linked to opmode" : "standalone"}
  </Button>
  <Button onclick={stop} disabled={activeStatus !== "RUNNING"}>Stop</Button>
</div>

<style>
  nav {
    display: flex;
    align-items: center;
    gap: var(--padding);
    justify-content: space-between;
    margin-bottom: var(--padding);
  }
  .controls {
    display: flex;
    gap: var(--padding);
  }

  .status {
    display: inline-flex;
    align-items: center;
    gap: 8px;
    font-weight: 600;
  }
  .dot {
    width: 10px;
    height: 10px;
    border-radius: 999px;
    background: hsl(0 0% 70%);
  }
  .dot[data-state="RUNNING"] {
    background: hsl(140 70% 40%);
  }
  .dot[data-state="STOPPED"] {
    background: hsl(0 70% 45%);
  }
  .dot[data-state="PAUSED"] {
    background: hsl(40 80% 45%);
  }
  .timer {
    display: flex;
    align-items: center;
    gap: calc(var(--padding));
    width: fit-content;
  }
  .separator {
    background-color: currentColor;
    height: 24px;
    width: 2px;
    margin-top: 2px;
    rotate: 4deg;
  }
  .current-max-time {
    all: unset;
    cursor: pointer;
    border: 1px solid currentColor;
    padding: 0.2rem 1rem;
  }
  .current-time,
  .current-max-time {
    font-weight: 600;
    font-size: 2rem;
  }
  .menu {
    all: unset;
    cursor: pointer;
    padding: 0.25em 1em;
  }
</style>
