<script lang="ts">
  import { global } from "$lib"
  import type { PluginInfo, PluginManager } from "ftc-panels"
  import type { PageProps } from "./$types"
  import WidgetContent from "$lib/grid/WidgetContent.svelte"
  import Section from "$lib/Section.svelte"
  import NavletContent from "$lib/navlets/NavletContent.svelte"
  import DocsPage from "$lib/DocsPage.svelte"
  import CanvasRender from "$lib/grid/CanvasRender.svelte"
  import { PluginDetails, Version } from "ftc-panels/docs"

  let { data }: PageProps = $props()
  let plugin = $derived(
    global.plugins.find((it) => it.details.id == data.id) as PluginInfo
  )
</script>

<PluginDetails plugin={plugin.details} />

<Version
  plugin={plugin.details}
  fetchFunction={(
    global.socket.pluginManagers[plugin.details.id]
      .constructor as typeof PluginManager
  ).getNewVersion}
/>

<h3>Settings</h3>
<Section margin={true}>
  <pre>{JSON.stringify(plugin.config, null, 2)}</pre>
</Section>
<h3>Widgets</h3>
{#each plugin.details.components.filter((it) => it.type === "widget") as widget}
  <Section margin={true}>
    <h4>{widget.id}</h4>
    <WidgetContent pluginID={plugin.details.id} widgetID={widget.id} />
  </Section>
{:else}
  <p>No widgets found</p>
{/each}

<h3>Navlets</h3>
{#each plugin.details.components.filter((it) => it.type === "navlet") as navlet}
  <Section margin={true}>
    <h4>{navlet.id}</h4>
    <NavletContent pluginID={plugin.details.id} widgetID={navlet.id} />
  </Section>
{:else}
  <p>No navlets found</p>
{/each}

<h3>Docs Chapters</h3>
{#each plugin.details.components.filter((it) => it.type === "docs") as chapter}
  <Section margin={true}>
    <h4>{chapter.id}</h4>
    <DocsPage pluginID={plugin.details.id} widgetID={chapter.id} />
  </Section>
{:else}
  <p>No chapters found</p>
{/each}

<h3>Templates</h3>
{#each plugin.details.templates as t}
  <CanvasRender {t} pID={plugin.details.id} />
  <Section margin={true}>
    <pre>{JSON.stringify(t, null, 2)}</pre>
  </Section>
{:else}
  <p>No templates found</p>
{/each}

<style>
  h4 {
    margin: 0;
  }
</style>
