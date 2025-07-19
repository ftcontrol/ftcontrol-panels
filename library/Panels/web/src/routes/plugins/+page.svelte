<script lang="ts">
  import { global } from "$lib"
  import Section from "$lib/Section.svelte"
</script>

<h1>Plugins</h1>
<div>
  {#each global.plugins as plugin}
    <Section>
      <h2>
        {plugin.details.name}
        {#if global.devServers
          .map((it) => it.pluginID)
          .includes(plugin.details.id)}
          DEV
        {/if}
      </h2>
      <p>{plugin.details.id}</p>
      <p>{plugin.details.author}</p>
      <a href="/plugins/{plugin.details.id}">Details</a>
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
  div {
    display: grid;
    gap: 1rem;
    grid-template-columns: repeat(auto-fill, minmax(300px, 1fr));
  }
</style>
