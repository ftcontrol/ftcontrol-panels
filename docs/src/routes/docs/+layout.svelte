<script lang="ts">
  import { type Snippet } from "svelte"
  import { DocsPage, CodeFormatter } from "ftc-panels/docs"
  import {simpleModules} from "$lib/simpleData";
  import {page} from "$app/state";

  let { children }: { children?: Snippet } = $props()
</script>

<DocsPage plugins={simpleModules} skippedPlugins={[]} url={page.url.pathname}>
  <CodeFormatter />
  {@render children?.()}
</DocsPage>
