<script lang="ts">
  import { Button, TextInput, type PluginInfo } from "ftc-panels"
  import { global } from "$lib"
  import Section from "$lib/Section.svelte"
  import { goto } from "$app/navigation"

  let {
    plugin,
    showDetailsButton = true,
  }: {
    plugin: PluginInfo
    showDetailsButton?: boolean
  } = $props()

  let isDev = $derived(global.devServers.includes(plugin.details.id))
</script>

<Section>
  <p>{plugin.details.id}</p>
  <h2>
    {plugin.details.name}
    {#if global.devServers.includes(plugin.details.id)}
      DEV
    {/if}
  </h2>
  <p>by {plugin.details.author}</p>
  {#if plugin.details.websiteURL}
    <a href={plugin.details.websiteURL}>Website: {plugin.details.websiteURL}</a>
  {:else}
    <a>No website</a>
  {/if}
  <p>{plugin.details.description}</p>
  <div class="main-buttons">
    <div class="buttons">
      {#if showDetailsButton}
        <Button
          onclick={() => {
            goto(`/plugins/${plugin.details.id}`)
          }}>Details</Button
        >
      {/if}
      <Button
        onclick={() => {
          if (isDev) {
            global.devServers = global.devServers.filter(
              (it) => it != plugin.details.id
            )
          } else {
            global.devServers.push(plugin.details.id)
          }
        }}
      >
        {isDev ? "Disable Dev" : "Enable Dev"}
      </Button>
    </div>

    <TextInput
      bind:value={plugin.details.devURL}
      placeholder={"http://localhost:3000"}
    />
  </div>
</Section>

<style>
  .buttons {
    display: flex;
    gap: 1rem;
  }
  .main-buttons {
    display: flex;
    flex-direction: column;
    margin-top: 1rem;
    gap: 1rem;
  }
  h2,
  p {
    margin: 0;
  }
  p,
  a {
    margin: 0.25rem;
  }
</style>
