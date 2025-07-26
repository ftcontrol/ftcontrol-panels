<script lang="ts">
  import type { Snippet } from "svelte"
  import { global } from "$lib"
  import { Toggle } from "ftc-panels"

  let { children }: { children?: Snippet } = $props()

  let coreDocs = $derived(
    global.plugins.find((it) => it.details.id != "com.bylazar.docs")
  )
</script>

<section>
  <nav>
    {#if coreDocs != undefined}
      <Toggle defaultOpen={true}>
        {#snippet trigger({ isOpen }: { isOpen: boolean })}
          <p>Core</p>
        {/snippet}
        {#snippet content({ close }: { close: () => void })}
          <div>
            <p>{coreDocs.details.docs.homepage.name}</p>
            {#each coreDocs.details.docs.chapters as c}
              <p>{c.name}</p>
            {/each}
          </div>
        {/snippet}
      </Toggle>
    {/if}
    {#each global.plugins.filter((it) => it.details.id != "com.bylazar.docs") as plugin}
      <Toggle>
        {#snippet trigger({ isOpen }: { isOpen: boolean })}
          <p>{plugin.details.name}</p>
        {/snippet}
        {#snippet content({ close }: { close: () => void })}
          <div>
            <p>{plugin.details.docs.homepage.name}</p>
            {#each plugin.details.docs.chapters as c}
              <p>{c.name}</p>
            {/each}
          </div>
        {/snippet}
      </Toggle>
    {/each}
  </nav>
  {@render children?.()}
</section>

<style>
  p {
    margin: 0;
  }
  nav {
    padding: var(--padding);
    overflow-y: auto;
    background-color: red;
    height: 80vh;
    width: fit-content;
  }
  section {
    margin: 0.5rem;
  }
  div {
    margin-left: calc(var(--padding));
  }
</style>
