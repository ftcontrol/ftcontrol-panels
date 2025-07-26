<script lang="ts">
  import { goto } from "$app/navigation"
  import { global } from "$lib"
  import Section from "$lib/Section.svelte"
  import { Button } from "ftc-panels"
</script>

<h1>Docs</h1>
<div>
  {#each global.plugins as plugin}
    <p>{plugin.details.name}</p>
    <p>{plugin.details.docs.homepage.name}</p>
    {#each plugin.details.docs.chapters as c}
      <p>{c.name}</p>
    {/each}
    <p>---</p>
  {/each}

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
