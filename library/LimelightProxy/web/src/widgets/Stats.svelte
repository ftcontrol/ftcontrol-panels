<script lang="ts">
  import type Manager from "../manager"

  import { onMount } from "svelte"

  type Response = {
    cameraQuat: {
      w: number
      x: number
      y: number
      z: number
    }

    cpu: number
    cid: number
    finalYaw: number
    fps: number
    hwType: number
    ignoreNT: number
    interfaceNeedsRefresh: number
    name: string
    pipeImgCount: number
    pipelineIndex: number
    pipelineType: string
    ram: number
    snapshotMode: number
    temp: number
  }

  let {
    manager,
  }: {
    manager: Manager
  } = $props()

  let isDisabled = $state(false)
  let url = $state("")

  let data: Response | null = $state(null)

  async function fetchCurrentURL() {
    try {
      const response = await fetch(url)

      if (!response.ok) {
        throw new Error(`HTTP error!`)
      }
      data = (await response.json()) as Response
      console.log(data)
      isDisabled = false
      console.log("Fetch successful")
    } catch (error) {
      console.error("Failed to fetch URL:", error)
      isDisabled = true
    }
  }

  onMount(() => {
    url = "http://" + window.location.hostname + ":5807/status"

    const interval = setInterval(() => {
      fetchCurrentURL()
    }, 2000)

    return () => {
      clearInterval(interval)
    }
  })
</script>

<p>{url}</p>
{#if url == ""}
  <p>Waiting</p>
{:else if isDisabled}
  <p>Disabled</p>
{:else if data != null}
  <p>CPU: {data.cpu}</p>
  <p>RAM: {data.ram}</p>
  <p>TEMP: {data.temp}°C</p>
{/if}

<style>
  p {
    margin: 0;
  }
</style>
