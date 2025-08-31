<script lang="ts">
  import {
    type PluginConfig,
    PluginManager,
    SimpleDynamicComponent,
  } from "ftc-panels"
  import { PluginDetails, ChangeLog, Version } from "ftc-panels-docs"
  import type { PageProps } from "./$types"
  import { modules } from "$lib/data"
  import { importFromSource } from "ftc-panels"

  let { data }: PageProps = $props()
  let plugin = $derived(modules.find((it) => it.config.id == data.id)) as {
    config: PluginConfig
    svelte: string
  }

  async function fetchVersion(): Promise<string> {
    const mod = await importFromSource(plugin.svelte)

    const Selector = mod.default

    const Manager = Selector("Manager") as typeof PluginManager
    return await Manager.getNewVersion()
  }
</script>

<svelte:head>
  <title
    >Panels: {plugin.config.id == "com.bylazar.docs"
      ? "Core"
      : plugin.config.name}</title
  >
  <meta name="description" content={plugin.config.description} />
</svelte:head>

{#key data}
  <PluginDetails plugin={plugin.config} />
  <Version plugin={plugin.config} fetchFunction={fetchVersion} />
  <ChangeLog changelog={plugin.config.changelog} />
  {#if plugin.config.components.filter((it) => it.type === "docs").length > 0}
    <SimpleDynamicComponent
      info={plugin.config}
      loadFunction={async (host, props) => {
        const mod = await importFromSource(plugin.svelte)
        const Selector = mod.default
        Selector(
          plugin.config.components.filter((it) => it.type === "docs")[0].id,
          host,
          props
        )
      }}
    />
  {/if}
{/key}

<style>
</style>
