<script lang="ts">
  import { Button, TextInput, type PluginInfo } from "ftc-panels"
  import { global } from "$lib"
  import Section from "$lib/Section.svelte"
  import { goto } from "$app/navigation"

  let {
    plugin,
    showDetailsButton = true,
          transparent = false

  }: {
    plugin: PluginInfo
    showDetailsButton?: boolean
    transparent?: boolean
  } = $props()
</script>

<Section {transparent}>
  <p>{plugin.details.id}</p>
  <h2>
    {plugin.details.name} v{plugin.details.version}
    {#if global.devPlugins.includes(plugin.details.id)}
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
    {#if showDetailsButton}
      <Button
        onclick={() => {
          goto(`/plugins/${plugin.details.id}`)
        }}>Details</Button
      >
    {/if}
  </div>
</Section>

<style>
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
