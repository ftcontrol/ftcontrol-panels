<script lang="ts">
  import { onMount, type Snippet } from "svelte"
  import { global } from "$lib"
  import { Toggle } from "ftc-panels"

  let { children }: { children?: Snippet } = $props()

  let orderedPlugins = $derived.by(() => {
    const docsPlugin = global.plugins.find(
      (it) => it.details.id === "com.bylazar.docs"
    )
    const otherPlugins = global.plugins.filter(
      (it) => it.details.id !== "com.bylazar.docs"
    )

    if (!docsPlugin) {
      return otherPlugins
    }

    const renamedDocsPlugin = {
      ...docsPlugin,
      details: {
        ...docsPlugin.details,
        name: "Core",
      },
    }

    return [renamedDocsPlugin, ...otherPlugins]
  })
</script>

<section>
  <nav>
    {#each orderedPlugins as plugin}
      <Toggle defaultOpen={true}>
        {#snippet trigger({ isOpen }: { isOpen: boolean })}
          <p>{plugin.details.name}</p>
        {/snippet}
        {#snippet content({ close }: { close: () => void })}
          <div class="item">
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
    {#each global.skippedPlugins as plugin}
      <p class="disabled">{plugin.name}</p>
    {/each}
  </nav>
  <div class="content">
    {@render children?.()}
  </div>
</section>

<style>
  section {
    display: flex;
    gap: calc(var(--padding) / 2);
    margin: 0.5rem;
    height: 100%;
  }
  p {
    margin: 0;
    font-weight: 600;
    margin-bottom: 0.25rem;
    margin-top: 0.5rem;
  }
  a {
    color: inherit;
    text-decoration: none;
    display: block;
    margin-bottom: 0.25rem;
  }
  nav {
    padding: var(--padding);
    overflow-y: auto;
    background-color: var(--bgMedium);
    border-radius: var();
    width: fit-content;
    border-radius: 1rem;
  }
  .item {
    margin-left: calc(var(--padding));
  }
  p.disabled {
    opacity: 0.5;
  }
</style>
