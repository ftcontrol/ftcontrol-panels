<script lang="ts">
  import { global } from "$lib"
  import {
    DynamicComponent,
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

  const plugin = global.plugins.find(
    (it) => it.details.id == pluginID
  ) as PluginInfo

  const docs = plugin.details.docs

  const isHomepage = widgetID === docs.homepage.name

  const content = isHomepage
    ? docs.homepage
    : (docs.chapters.find((it) => it.name == widgetID) as PanelsWidget)
</script>

{#key `${pluginID}-${widgetID}-${global.reloadIndexes[pluginID]}`}
  <DynamicComponent
    globalSocket={global.socket}
    info={plugin}
    textContent={content.textContent}
    id={pluginID}
  />
{/key}
