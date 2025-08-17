<script lang="ts">
  import {
    Button,
    type PluginConfig,
    type PluginInfo,
    SimpleDynamicComponent,
  } from "ftc-panels"
  import type { PageProps } from "./$types"
  import { modules } from "$lib/data"
  import { goto } from "$app/navigation"

  let { data }: PageProps = $props()
  let plugin = $derived(modules.find((it) => it.id == data.id) as PluginConfig)

  function processWebsiteURL(url: string) {
    try {
      const parsed = new URL(url)
      return parsed.hostname
    } catch (e) {
      try {
        const parsed = new URL("http://" + url)
        return parsed.hostname
      } catch {
        return url
      }
    }
  }
</script>

{#key data}
  <section>
    <p>{plugin.id}</p>
    <h2>
      {plugin.name} v{plugin.version}
    </h2>
    <p>by {plugin.author}</p>
    {#if plugin.websiteURL}
      <a href={plugin.websiteURL}
        >Website: {processWebsiteURL(plugin.websiteURL)}</a
      >
    {:else}
      <a>No website</a>
    {/if}
    <br />
    <p>Included Plugins</p>
    {#each plugin.includedPluginsIDs as id}
      <p>{id}</p>
    {/each}
    <p>{plugin.description}</p>
  </section>
  <SimpleDynamicComponent
    info={plugin}
    textContent={plugin.docs.homepage.textContent}
  />
{/key}

<style>
  section {
    background-color: var(--bgLight);
    padding: var(--padding);
    border-radius: 1rem;
    overflow: auto;
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
