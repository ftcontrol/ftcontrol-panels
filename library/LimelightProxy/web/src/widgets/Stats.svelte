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

  let data = $state({})

  async function fetchCurrentURL() {
    try {
      const response = await fetch(url)

      if (!response.ok) {
        throw new Error(`HTTP error!`)
      }
      const json = await response.json()
      console.log(json)
      isDisabled = false
      data = JSON.parse(json)
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

{#if url == ""}
  <p>Waiting</p>
{:else if isDisabled}
  <p>Disabled</p>
{:else}
  <p>{JSON.stringify(data)}</p>
{/if}

<style>
  p {
    margin: 0;
  }
</style>
