<script lang="ts">
  import { global } from "$lib"
  import type { PanelsWidget, PluginInfo } from "ftc-panels"
  import type { PageProps } from "./$types"
  import DocsPage from "$lib/DocsPage.svelte"

  let { data }: PageProps = $props()
  let plugin = $derived(
    global.plugins.find((it) => it.details.id == data.id) as PluginInfo
  )
  let page = $derived(
    plugin.details.docs.chapters.find(
      (it) => it.name == data.page
    ) as PanelsWidget
  )
</script>

<DocsPage pluginID={plugin.details.id} widgetID={page.name} />

<style>
</style>
