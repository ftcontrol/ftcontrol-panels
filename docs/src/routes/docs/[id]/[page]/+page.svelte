<script lang="ts">
    import type {PanelsWidget, PluginConfig, PluginInfo} from "ftc-panels"
    import {SimpleDynamicComponent} from "ftc-panels"
    import type {PageProps} from "./$types"
    import {modules} from "$lib/data"


    let {data}: PageProps = $props()
    let plugin = $derived(
        modules.find((it) => it.id == data.id) as PluginConfig
    )
</script>

{#key data}
    <SimpleDynamicComponent
            info={plugin}
            textContent={plugin.docs.chapters.find(it => it.name === data.page).textContent}
    />
{/key}

<style>
</style>
