<script lang="ts">
  import { global } from "$lib"
  import type { PluginInfo } from "ftc-panels"
  import type { PageProps } from "./$types"
  import DocsPage from "$lib/DocsPage.svelte"
  import { PluginDetails, ChangeLog } from "ftc-panels/docs"

  let { data }: PageProps = $props()
  let plugin = $derived(
    global.plugins.find((it) => it.details.id == data.id) as PluginInfo
  )
</script>

<DocsPage
  pluginID={plugin.details.id}
  widgetID={plugin.details.components.filter((it) => it.type == "docs")[0]
    ?.id || ""}
>
  <PluginDetails plugin={plugin.details} />
  <ChangeLog changelog={plugin.details.changelog} />
</DocsPage>
