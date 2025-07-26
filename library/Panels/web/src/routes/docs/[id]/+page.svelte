<script lang="ts">
  import { global } from "$lib"
  import type { PluginInfo } from "ftc-panels"
  import type { PageProps } from "./$types"

  let { data }: PageProps = $props()
  let plugin = $derived(
    global.plugins.find((it) => it.details.id == data.id) as PluginInfo
  )
</script>

<h3>Plugin {plugin.details.name}</h3>
homepage

<style>
</style>
