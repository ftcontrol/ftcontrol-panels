<script lang="ts">
    import {Button, type PluginConfig, type PluginInfo, SimpleDynamicComponent} from "ftc-panels"
    import type {PageProps} from "./$types"
    import {modules} from "$lib/data"
    import {goto} from "$app/navigation";

    let {data}: PageProps = $props()
    let plugin = $derived(
        modules.find((it) => it.id == data.id) as PluginConfig
    )
</script>

{#key data}
    <section>
        <p>{plugin.id}</p>
        <h2>
            {plugin.name} v{plugin.version}
        </h2>
        <p>by {plugin.author}</p>
        {#if plugin.websiteURL}
            <a href={plugin.websiteURL}>Website: {plugin.websiteURL}</a>
        {:else}
            <a>No website</a>
        {/if}
        <p>{plugin.description}</p>
    </section>
    <SimpleDynamicComponent
            info={plugin}
            textContent={plugin.docs.homepage.textContent}
    />
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
