<script lang="ts">
  import { global } from "$lib"
  import type { PluginInfo } from "ftc-panels"
  import type { PageProps } from "./$types"
  import WidgetContent from "$lib/grid/WidgetContent.svelte"
  import Section from "$lib/Section.svelte"
  import NavletContent from "$lib/navlets/NavletContent.svelte"
  import DocsPage from "$lib/DocsPage.svelte"
  import PluginsWidget from "$lib/plugins/PluginsWidget.svelte"
  import CanvasRender from "$lib/grid/CanvasRender.svelte"

  let { data }: PageProps = $props()
  let plugin = $derived(
    global.plugins.find((it) => it.details.id == data.id) as PluginInfo
  )
</script>

<PluginsWidget {plugin} showDetailsButton={false} />

<h3>Widgets</h3>
{#each plugin.details.widgets as widget}
  <Section margin={true}>
    <h4>{widget.name}</h4>
    <WidgetContent pluginID={plugin.details.id} widgetID={widget.name} />
  </Section>
{:else}
  <p>No widgets found</p>
{/each}

<h3>Navlets</h3>
{#each plugin.details.navlets as navlet}
  <Section margin={true}>
    <h4>{navlet.name}</h4>
    <NavletContent pluginID={plugin.details.id} widgetID={navlet.name} />
  </Section>
{:else}
  <p>No navlets found</p>
{/each}

<h3>Docs Homepage</h3>
<Section margin={true}>
  <h4>{plugin.details.docs.homepage.name}</h4>
  <DocsPage
    pluginID={plugin.details.id}
    widgetID={plugin.details.docs.homepage.name}
  />
</Section>

<h3>Docs Chapters</h3>
{#each plugin.details.docs.chapters as chapter}
  <Section margin={true}>
    <h4>{chapter.name}</h4>
    <DocsPage pluginID={plugin.details.id} widgetID={chapter.name} />
  </Section>
{:else}
  <p>No chapters found</p>
{/each}

<h3>Templates</h3>
{#each plugin.details.templates as t}
  <CanvasRender {t} />
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
