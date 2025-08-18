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

  const navlet = $derived(
    plugin.details.components.find((it) => it.id == widgetID) as PanelsWidget
  )
</script>

{#key `${pluginID}-${widgetID}-${global.reloadIndexes[pluginID]}`}
  <DynamicComponent
    globalSocket={global.socket}
    info={plugin}
    id={pluginID}
  />
{/key}
