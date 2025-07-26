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
            <a href="/docs/{coreDocs.details.id}"
              >{coreDocs.details.docs.homepage.name}</a
            >
            {#each coreDocs.details.docs.chapters as c}
              <a href="/docs/{coreDocs.details.id}/{c.name}">{c.name}</a>
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
            <a href="/docs/{plugin.details.id}"
              >{plugin.details.docs.homepage.name}</a
            >
            {#each plugin.details.docs.chapters as c}
              <a href="/docs/{plugin.details.id}/{c.name}">{c.name}</a>
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
    font-weight: 600;
    margin-bottom: 0.25rem;
  }
  a {
    color: inherit;
    text-decoration: none;
    display: block;
  }
  nav {
    padding: var(--padding);
    overflow-y: auto;
    background-color: var(--bgMedium);
    border-radius: var();
    height: 80vh;
    width: fit-content;
    border-radius: 1rem;
  }
  section {
    margin: 0.5rem;
  }
  div {
    margin-left: calc(var(--padding));
  }
</style>
