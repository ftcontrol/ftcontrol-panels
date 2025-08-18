<script lang="ts">
    import {
        Button,
        type PluginConfig,
        type PluginInfo,
        SimpleDynamicComponent,
    } from "ftc-panels"
    import type {PageProps} from "./$types"
    import {modules} from "$lib/data"
    import {goto} from "$app/navigation"
    import {importFromSource} from "../../../../../../ftcontrol-plugins/cli/core/socket/source";

    let {data}: PageProps = $props()
    let plugin = $derived(modules.find((it) => it.config.id == data.id)) as { config: PluginConfig, svelte: string }

    function processWebsiteURL(url: string) {
        try {
            const parsed = new URL(url)
            return parsed.hostname
        } catch (e) {
            try {
                const parsed = new URL("http://" + url)
                return parsed.hostname
            } catch {
                return url
            }
        }
    }
</script>

{#key data}
    <section>
        <p>{plugin.config.id}</p>
        <h2>
            {plugin.config.name} v{plugin.config.version}
        </h2>
        <p>by {plugin.config.author}</p>
        {#if plugin.config.websiteURL}
            <a href={plugin.config.websiteURL}
            >Website: {processWebsiteURL(plugin.config.websiteURL)}</a
            >
        {:else}
            <a>No website</a>
        {/if}
        <br/>
        {#if plugin.config.includedPluginsIDs.length > 0}
            <p>Included Plugins</p>
            {#each plugin.config.includedPluginsIDs as id}
                <p>{id}</p>
            {/each}
        {/if}
        <p>{plugin.config.description}</p>
    </section>
    {#if plugin.config.components.filter(it => it.type == "docs").length > 0}
        <SimpleDynamicComponent
                info={plugin.config}
                loadFunction={async(host, props) => {
const mod = await importFromSource(plugin.svelte)
          const Selector = mod.default
          Selector(plugin.config.components.filter(it => it.type == "docs")[0].id, host, props)
        }}
        />
    {/if}
{/key}

<style>
    section {
        background-color: var(--bgLight);
        padding: var(--padding);
        border-radius: 1rem;
        overflow: auto;
    }

    h2,
    p {
        margin: 0;
    }

    p,
    a {
        margin: 0.25rem;
    }
</style>
