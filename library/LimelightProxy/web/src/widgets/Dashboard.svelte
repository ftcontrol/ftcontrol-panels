<script lang="ts">
  import type Manager from "../manager"
  import { Button } from "ftc-panels"
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

<p>{url}</p>

<Button
  disabled={isDisabled}
  onclick={() => {
    window.open(url, "_blank")
  }}
>
  Open Dashboard
</Button>
