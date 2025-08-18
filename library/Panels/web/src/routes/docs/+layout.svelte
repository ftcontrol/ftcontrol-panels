<script lang="ts">
    import {type Snippet} from "svelte"
    import {global} from "$lib"
    import {DocsPage, CodeFormatter} from "ftc-panels/docs"

    let {children}: { children?: Snippet } = $props()
</script>

<DocsPage
        plugins={global.plugins.map(it => it.details)}
        skippedPlugins={global.skippedPlugins}
>
    <CodeFormatter />
    {@render children?.()}
</DocsPage>