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

  const plugin = $derived(
    global.plugins.find((it) => it.details.id == pluginID) as PluginInfo
  )

  const widget = $derived(
    plugin.details.widgets.find((it) => it.name == widgetID) as PanelsWidget
  )
</script>

{#key `${pluginID}-${widgetID}-${global.reloadIndexes[pluginID]}`}
  <DynamicComponent
    globalSocket={global.socket}
    info={plugin}
    textContent={widget.textContent}
    id={pluginID}
  />
{/key}
