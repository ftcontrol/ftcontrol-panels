<script lang="ts">
  import { global } from "$lib"
  import PluginsWidget from "$lib/plugins/PluginsWidget.svelte"
  import Section from "$lib/Section.svelte"
</script>

<h1>Plugins</h1>
<section>
  {#each global.plugins as plugin}
    <PluginsWidget {plugin} isDev={global.devPlugins.includes(plugin.details.id)} />
  {/each}

  {#each global.skippedPlugins as plugin}
    <Section>
      <h2>
        {plugin.name}
        SKIPPED
      </h2>
      <p>{plugin.id}</p>
      <p>{plugin.author}</p>
      {#if plugin.websiteURL}
        <a href={plugin.websiteURL}>Website: {plugin.websiteURL}</a>
      {:else}
        <a>No website</a>
      {/if}
      <p>{plugin.description}</p>
    </Section>
  {/each}
</section>

<style>
  h2,
  p {
    margin: 0;
  }
  p {
    margin: 0.25rem;
  }
  section {
    display: grid;
    gap: 1rem;
    grid-template-columns: repeat(auto-fill, minmax(300px, 1fr));
  }
</style>
