<script lang="ts">
  import { global } from "$lib"
  import type { PanelsWidget, PluginInfo } from "ftc-panels"
  import type { PageProps } from "./$types"

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

<p>
  {data.id}
  {data.page}
</p>
<p>{page}</p>

<style>
</style>
