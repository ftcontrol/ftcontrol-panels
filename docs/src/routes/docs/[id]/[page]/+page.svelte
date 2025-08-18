<script lang="ts">
    import type {PanelsWidget, PluginConfig, PluginInfo} from "ftc-panels"
    import {SimpleDynamicComponent} from "ftc-panels"
    import type {PageProps} from "./$types"
    import {modules} from "$lib/data"
    import {importFromSource} from "../../../../../../../ftcontrol-plugins/cli/core/socket/source";


    let {data}: PageProps = $props()
    let plugin = $derived(
        modules.find((it) => it.config.id == data.id) as { config: PluginConfig, svelte: string }
    )
</script>

{#key data}
    <SimpleDynamicComponent
            info={plugin.config}
            loadFunction={async(host, props) => {
const mod = await importFromSource(plugin.svelte)
          const Selector = mod.default
          Selector(data.page, host, props)
        }}
    />
{/key}

<style>
</style>
