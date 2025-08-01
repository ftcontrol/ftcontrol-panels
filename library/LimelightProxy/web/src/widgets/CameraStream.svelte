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
      const response = await fetch(
        "http://" + window.location.hostname + ":5807/status"
      )

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
    url = "http://" + window.location.hostname + ":5800"

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
{:else}
  <img class:isDisabled src={url} alt="Limelight Stream" />
{/if}

<style>
  p {
    margin: 0;
  }
  .isDisabled {
    opacity: 0.5;
  }
  img {
    width: 100%;
    height: auto;
  }
</style>
