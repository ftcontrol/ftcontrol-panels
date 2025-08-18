<script lang="ts">
  import { global } from "$lib"
  import type { PluginInfo } from "ftc-panels"
  import type { PageProps } from "./$types"
  import DocsPage from "$lib/DocsPage.svelte"
  import PluginsWidget from "$lib/plugins/PluginsWidget.svelte";

  let { data }: PageProps = $props()
  let plugin = $derived(
    global.plugins.find((it) => it.details.id == data.id) as PluginInfo
  )
</script>


<DocsPage
  pluginID={plugin.details.id}
  widgetID={plugin.details.components.filter(it => it.type =="docs")[0].id}
>
    <section>
        <PluginsWidget plugin={plugin} showDetailsButton={false} transparent={true} />
    </section>
</DocsPage>

<style>
    section {
        background-color: var(--bgLight);
        border-radius: calc(var(--padding) / 2);
    }
</style>
