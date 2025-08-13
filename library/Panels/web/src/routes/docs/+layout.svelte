<script lang="ts">
    import {type Snippet} from "svelte"
    import {global} from "$lib"
    import {DocsPage} from "ftc-panels/docs"

    let {children}: { children?: Snippet } = $props()
</script>

<DocsPage
        plugins={global.plugins.map(it => it.details)}
        skippedPlugins={global.skippedPlugins}
>
    {@render children?.()}
</DocsPage>