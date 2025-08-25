<script lang="ts">
  import { type Snippet } from "svelte"
  import { global } from "$lib"
  import { DocsPage, CodeFormatter } from "ftc-panels/docs"
  import { page } from "$app/state"

  let { children }: { children?: Snippet } = $props()
</script>

<DocsPage
  plugins={global.plugins.map((it) => it.details)}
  skippedPlugins={global.skippedPlugins}
  url={page.url.pathname}
>
  <CodeFormatter />
  {@render children?.()}
</DocsPage>
