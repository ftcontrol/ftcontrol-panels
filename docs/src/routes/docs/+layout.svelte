<script lang="ts">
    import {type Snippet} from "svelte"
    import {DocsPage} from "ftc-panels/docs"
    import { modules } from "$lib/data"

    let {children}: { children?: Snippet } = $props()
</script>

<DocsPage
        plugins={modules}
        skippedPlugins={[]}
>
    {@render children()}
</DocsPage>