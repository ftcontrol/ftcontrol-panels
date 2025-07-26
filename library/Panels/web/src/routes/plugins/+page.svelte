<script lang="ts">
  import { goto } from "$app/navigation"
  import { global } from "$lib"
  import Section from "$lib/Section.svelte"
  import { Button } from "ftc-panels"
</script>

<h1>Plugins</h1>
<div>
  {#each global.plugins as plugin}
    <Section>
      <p>{plugin.details.id}</p>
      <h2>
        {plugin.details.name}
        {#if global.devServers
          .map((it) => it.pluginID)
          .includes(plugin.details.id)}
          DEV
        {/if}
      </h2>
      <p>by {plugin.details.author}</p>
      <br />
      <p>{plugin.details.description}</p>
      <br />
      <Button
        onclick={() => {
          goto(`/plugins/${plugin.details.id}`)
        }}>Details</Button
      >
    </Section>
  {/each}

  {#each global.skippedPlugins as plugin}
    <Section>
      <h2>
        {plugin.name}
        {#if global.devServers.map((it) => it.pluginID).includes(plugin.id)}
          DEV
        {/if}
        SKIPPED
      </h2>
      <p>{plugin.id}</p>
      <p>{plugin.author}</p>
    </Section>
  {/each}
</div>

<style>
  h2,
  p {
    margin: 0;
  }
  p {
    margin: 0.25rem;
  }
  div {
    display: grid;
    gap: 1rem;
    grid-template-columns: repeat(auto-fill, minmax(300px, 1fr));
  }
</style>
