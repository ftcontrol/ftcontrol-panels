<script lang="ts">
  import { global } from "$lib"
</script>

<h1>Docs</h1>
<div>
  <!-- {#each global.skippedPlugins as plugin}
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
  {/each} -->
</div>

<style>
  p {
    margin: 0;
  }
  p {
    margin: 0.25rem;
  }
</style>
