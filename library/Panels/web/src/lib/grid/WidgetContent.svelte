<script lang="ts">
    import {global} from "$lib"
    import {
        DynamicComponent,
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
</script>

{#key `${pluginID}-${widgetID}-${global.reloadIndexes[pluginID]}`}
    <DynamicComponent
            globalSocket={global.socket}
            info={plugin}
            id={pluginID}
            loadFunction={(host, props)=>{
    global.socket.pluginSelectors[pluginID](widgetID, host, props)
    }}
    />
{/key}
