<script lang="ts">
  import { global } from "$lib"
  import type { PluginInfo } from "ftc-panels"
  import type { PageProps } from "./$types"
  import WidgetContent from "$lib/grid/WidgetContent.svelte"
  import Section from "$lib/Section.svelte"
  import NavletContent from "$lib/NavletContent.svelte"

  let { data }: PageProps = $props()
  let plugin = $derived(
    global.plugins.find((it) => it.details.id == data.id) as PluginInfo
  )
</script>

<Section>
  <h2>{plugin.details.name}</h2>
  <h3>{plugin.details.id}</h3>
</Section>

<h3>Widgets</h3>
{#each plugin.details.widgets as widget}
  <Section>
    <h4>{widget.name}</h4>
    <WidgetContent pluginID={plugin.details.id} widgetID={widget.name} />
  </Section>
{/each}

<h3>Navlets</h3>
{#each plugin.details.navlets as navlet}
  <Section>
    <h4>{navlet.name}</h4>
    <NavletContent pluginID={plugin.details.id} widgetID={navlet.name} />
  </Section>
{/each}

<style>
  h4 {
    margin: 0;
  }
</style>
