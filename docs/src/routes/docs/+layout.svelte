<script lang="ts">
  import { type Snippet } from "svelte"
  import { DocsPage, CodeFormatter } from "ftc-panels/docs"
  import {simpleModules} from "$lib/simpleData";

  let { children }: { children?: Snippet } = $props()
</script>

<DocsPage plugins={simpleModules} skippedPlugins={[]}>
  <CodeFormatter />
  {@render children?.()}
</DocsPage>
