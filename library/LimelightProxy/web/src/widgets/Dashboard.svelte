<script lang="ts">
  import type Manager from "../manager"
  import { onMount } from "svelte"

  let {
    manager,
  }: {
    manager: Manager
  } = $props()

  let isDisabled = $state(false)
  let url = $state("")

  async function fetchCurrentURL() {
    try {
      const response = await fetch(url)

      if (!response.ok) {
        throw new Error(`HTTP error!`)
      }
      isDisabled = false
      console.log("Fetch successful")
    } catch (error) {
      console.error("Failed to fetch URL:", error)
      isDisabled = true
    }
  }

  onMount(() => {
    url = "http://" + window.location.hostname + ":5801"

    const interval = setInterval(() => {
      fetchCurrentURL()
    }, 2000)

    return () => {
      clearInterval(interval)
    }
  })
</script>

{#if url == ""}
  <p>Waiting</p>
{:else if isDisabled}
  <p>Disabled</p>
{:else}
  <iframe class:isDisabled src={url} title="Limelight Dashboard"> </iframe>
{/if}

<style>
  p {
    margin: 0;
  }
  .isDisabled {
    opacity: 0.5;
  }
  iframe {
    width: 100%;
    height: 100%;
  }
</style>
