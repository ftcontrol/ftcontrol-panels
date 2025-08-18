<script lang="ts">
  import { global } from "$lib"
  import type { PanelsWidget, PluginInfo } from "ftc-panels"
  import type { PageProps } from "./$types"
  import DocsPage from "$lib/DocsPage.svelte"

  let { data }: PageProps = $props()
</script>

<DocsPage pluginID={data.id} widgetID={data.page} />

<style>
</style>
