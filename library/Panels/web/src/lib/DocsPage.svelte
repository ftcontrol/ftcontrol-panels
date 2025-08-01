<script lang="ts">
  import { global } from "$lib"
  import {
    SimpleDynamicComponent,
    type PanelsWidget,
    type PluginInfo,
  } from "ftc-panels"

  let {
    pluginID,
    widgetID,
  }: {
    pluginID: string
    widgetID: string
  } = $props()

  const plugin = $derived(
    global.plugins.find((it) => it.details.id == pluginID) as PluginInfo
  )

  const docs = $derived(plugin.details.docs)

  const isHomepage = $derived(widgetID == docs.homepage.name)

  const content = $derived(
    isHomepage
      ? docs.homepage
      : (docs.chapters.find((it) => it.name == widgetID) as PanelsWidget)
  )
</script>

<section>
  {#key `${pluginID}-${widgetID}-${global.reloadIndexes[pluginID]}`}
    <SimpleDynamicComponent
      info={plugin.details}
      textContent={content.textContent}
    />
  {/key}
</section>

<style>
  section {
    overflow-y: auto;
    overflow-x: none;
    max-height: 100%;
    max-width: 100%;
  }
</style>
