<script lang="ts">
  import { onMount } from "svelte"
  import Manager, { type OpModeStatus } from "../manager"
  import type { OpMode } from "../types"
  import { Overlay, Button } from "ftc-panels"
  import Arrow from "./Arrow.svelte"

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

  let activeStatus: OpModeStatus = $state("STOPPED")

  onMount(() => {
    manager.state.onChange(manager.OPMODES_KEY, (value) => {
      opModes = value
    })
    manager.state.onChange(manager.ACTIVE_OPMODE_KEY, (value) => {
      activeOpMode = value
      if (activeOpMode.name == "") return
      selectedOpModeName = activeOpMode.name
    })
    manager.state.onChange(manager.ACTIVE_OPMODE_STATUS_KEY, (value) => {
      activeStatus = value
    })
  })

  function selectOpMode(opMode: OpMode) {
    selectedOpModeName = opMode.name
  }

  let selectedOpModeName = $state("")

  let disableInit = $derived.by(() => {
    if (selectedOpModeName == "") return true
    if (activeStatus == "INIT") return true
    return false
  })
  let disablePlay = $derived.by(() => {
    if (selectedOpModeName == "") return true
    if (activeStatus != "INIT") return true
    return false
  })
  let disableStop = $derived.by(() => {
    if (selectedOpModeName == "") return true
    if (activeStatus == "STOPPED") return true
    return false
  })
</script>

<div class="top">
  <Overlay>
    {#snippet trigger()}
      <Button transparent={true}>Autos <Arrow /></Button>
    {/snippet}
    {#snippet overlay({ close }: { close: () => void })}
      {#each opModes.filter((it) => it.flavour == "AUTONOMOUS") as opMode}
        <div class="list">
          <p>{opMode.name} / {opMode.group}</p>
          <p>{opMode.flavour} / {opMode.source}</p>
          <button
            onclick={() => {
              selectOpMode(opMode)
              close()
            }}>Select</button
          >
        </div>
      {/each}
      {#if opModes.filter((it) => it.flavour == "AUTONOMOUS").length == 0}
        <p>No OpModes found.</p>
      {/if}
    {/snippet}
  </Overlay>

  <Overlay>
    {#snippet trigger()}
      <Button transparent={true}>TeleOps <Arrow /></Button>
    {/snippet}
    {#snippet overlay({ close }: { close: () => void })}
      {#each opModes.filter((it) => it.flavour == "TELEOP") as opMode}
        <div class="list">
          <p>{opMode.name} / {opMode.group}</p>
          <p>{opMode.flavour} / {opMode.source}</p>
          <button
            onclick={() => {
              selectOpMode(opMode)
              close()
            }}>Select</button
          >
        </div>
      {/each}
      {#if opModes.filter((it) => it.flavour == "TELEOP").length == 0}
        <p>No OpModes found.</p>
      {/if}
    {/snippet}
  </Overlay>
</div>
{#if selectedOpModeName == ""}
  <p class="title">Nothing selected</p>
{:else}
  <p class="title">
    <span class="title_small">
      {opModes.find((it) => it.name == selectedOpModeName)?.flavour}
    </span>
    {selectedOpModeName}
  </p>
{/if}

<div class="flex">
  <Button
    disabled={disableInit}
    onclick={() => {
      manager.socket.sendMessage("initOpMode", selectedOpModeName)
    }}
  >
    Initialize
  </Button>
  <Button
    disabled={disablePlay}
    onclick={() => {
      manager.socket.sendMessage("startActiveOpMode", null)
    }}
  >
    Start
  </Button>
  <Button
    disabled={disableStop}
    onclick={() => {
      manager.socket.sendMessage("stopActiveOpMode", null)
    }}
  >
    Stop
  </Button>
</div>

<style>
  .top {
    display: flex;
    gap: 1rem;
    justify-content: space-between;
  }
  .flex {
    display: flex;
    gap: 1rem;
    justify-content: space-between;
  }
  span {
    display: flex;
    align-items: center;
    gap: 0.3rem;
  }

  .title {
    font-size: 1.25rem;
    text-align: center;
    position: relative;
    margin: 0;
    margin-bottom: 1rem;
  }

  .title_small {
    font-size: 1rem;
    text-align: center;
    position: absolute;
    left: 50%;
    transform: translate(-50%, 0);
    bottom: -1rem;
  }

  .list {
    max-height: 500px;
    overflow-y: auto;
  }
</style>
