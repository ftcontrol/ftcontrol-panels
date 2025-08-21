<script lang="ts">
  import { global } from "$lib"
  import { SimpleDynamicComponent, type PluginInfo } from "ftc-panels"
  import type { Snippet } from "svelte"

  let {
    pluginID,
    widgetID,
    children,
  }: {
    pluginID: string
    widgetID: string
    children?: Snippet
  } = $props()

  const plugin = $derived(
    global.plugins.find((it) => it.details.id == pluginID) as PluginInfo
  )
</script>

<section>
  {@render children?.()}
  {#if widgetID}
    {#key `${pluginID}-${widgetID}-${global.reloadIndexes[pluginID]}`}
      <SimpleDynamicComponent
        info={plugin.config}
        loadFunction={async (host, props) => {
          const Selector = global.socket.pluginSelectors[plugin.details.id]
          Selector(widgetID, host, props)
        }}
      />
    {/key}
  {/if}
</section>

<style>
  section {
    overflow-y: auto;
    overflow-x: none;
    max-height: 100%;
    max-width: 100%;
  }
</style>
