<script lang="ts">
  import { onMount } from "svelte"
  import Timer from "./Timer.svelte"
  import type Manager from "../manager"
  import type { OpMode } from "../types"
  import type { OpModeStatus } from "../manager"

  let {
    manager,
  }: {
    manager: Manager
  } = $props()

  let opModes: OpMode[] = $state([])
  let activeOpMode: OpMode = $state({
    name: "",
    group: "",
    flavour: "AUTONOMOUS",
    source: "EXTERNAL_LIBRARY",
    defaultGroup: "",
    autoTransition: "",
  })

  function stop() {
    manager.socket.sendMessage("stopActiveOpMode", null)
  }

  let activeStatus: OpModeStatus = $state("STOPPED")

  let activeStartTimestamp: number | null = $state(null)

  onMount(() => {
    manager.state.onChange(manager.OPMODES_KEY, (value) => {
      opModes = value
    })
    manager.state.onChange(manager.ACTIVE_OPMODE_KEY, (value) => {
      activeOpMode = value
    })
    manager.state.onChange(manager.ACTIVE_OPMODE_STATUS_KEY, (value) => {
      activeStatus = value
    })
    manager.state.onChange(
      manager.ACTIVE_OPMODE_START_TIMESTAMP_KEY,
      (value) => {
        activeStartTimestamp = value
      }
    )
  })
</script>

<Timer />

<p>3:00</p>

<p>{JSON.stringify(activeOpMode)}</p>
<p>{activeStatus}</p>
<p>{activeStartTimestamp}</p>
