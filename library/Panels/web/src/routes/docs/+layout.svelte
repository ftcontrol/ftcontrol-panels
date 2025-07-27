<script lang="ts">
  import { onMount, type Snippet } from "svelte"
  import { global } from "$lib"
  import { Toggle } from "ftc-panels"
  import Arrow from "$lib/icons/Arrow.svelte"

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
      <Toggle defaultOpen={plugin.details.id == "com.bylazar.docs"}>
        {#snippet trigger({ isOpen }: { isOpen: boolean })}
          <p>
            {plugin.details.name}
            <Arrow {isOpen} />
          </p>
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
      <div class="divider"></div>
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
    overflow-y: auto;
    height: 100%;
  }
  p {
    display: flex;
    align-items: center;
    justify-content: space-between;
    gap: var(--padding);
    font-weight: 700;
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
    border-radius: 1rem;
    min-width: 200px;
  }
  .content {
    background-color: var(--bgMedium);
    border-radius: 1rem;
    padding: var(--padding);
    width: 100%;
  }
  p.disabled {
    opacity: 0.5;
  }
  .divider {
    width: 100%;
    height: 1px;
    background-color: currentColor;
    margin-block: 1rem;
    opacity: 0.5;
  }
</style>
