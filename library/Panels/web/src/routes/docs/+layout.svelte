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

  let navShown = $state(false)
  let hoveringEdge = $state(false)
  let navHovered = $state(false)

  function closeNav() {
    navShown = false
  }

  function handleNavEnter() {
    navHovered = true
    navShown = true
  }

  function handleNavLeave(e: MouseEvent) {
    navHovered = false
    if (!hoveringEdge) {
      navShown = false
    }
  }

  onMount(() => {
    const onMouseMove = (e: MouseEvent) => {
      hoveringEdge = e.clientX <= 16
      if (hoveringEdge) {
        navShown = true
      } else if (!navHovered) {
        navShown = false
      }
    }

    const onMouseLeave = (e: MouseEvent) => {
      if (e.clientX <= 0) {
        navShown = true
      }
    }

    const onMouseEnter = (e: MouseEvent) => {
      if (e.clientX <= 16) {
        navShown = true
      }
    }

    window.addEventListener("mousemove", onMouseMove)
    window.addEventListener("mouseleave", onMouseLeave)
    window.addEventListener("mouseenter", onMouseEnter)

    return () => {
      window.removeEventListener("mousemove", onMouseMove)
      window.removeEventListener("mouseleave", onMouseLeave)
      window.removeEventListener("mouseenter", onMouseEnter)
    }
  })
</script>

<section>
  <nav
    class:shown={navShown}
    onmouseenter={handleNavEnter}
    onmouseleave={handleNavLeave}
  >
    {#each orderedPlugins as plugin}
      <Toggle defaultOpen={plugin.details.id == "com.bylazar.docs"}>
        {#snippet trigger({ isOpen }: { isOpen: boolean })}
          <p>{plugin.details.name}</p>
        {/snippet}
        {#snippet content({ close }: { close: () => void })}
          <div>
            <a href="/docs/{plugin.details.id}" onclick={closeNav}
              >{plugin.details.docs.homepage.name}</a
            >
            {#each plugin.details.docs.chapters as c}
              <a href="/docs/{plugin.details.id}/{c.name}" onclick={closeNav}
                >{c.name}</a
              >
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
    position: absolute;
    left: 0;
    top: 50%;
    transform: translateY(-50%) translateX(-100%);
    transition: transform 250ms;
    transition-delay: 250ms;
  }
  nav.shown {
    transform: translateY(-50%) translateX(0%);
    transition-delay: 0ms;
  }
  section {
    margin: 0.5rem;
  }
  div {
    margin-left: calc(var(--padding));
  }
</style>
