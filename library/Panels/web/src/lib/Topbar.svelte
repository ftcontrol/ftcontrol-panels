<script>
  import Panels from "$lib/Panels.svelte"
  import Overlay from "ftc-panels/src/core/ui/Overlay.svelte"
  import { global } from "$lib"
</script>

<nav>
  <Panels />

  <Overlay>
    {#snippet trigger({ isOpen })}
      Plugins
    {/snippet}
    {#snippet overlay({ close })}
      <div class="plugins-overlay">
        {#each global.plugins as plugin}
          <button class="plugin">{plugin.details.letterName}</button>
        {/each}
        {#each global.skippedPlugins as details}
          <button class="plugin" disabled>{details.letterName}</button>
        {/each}
      </div>
    {/snippet}
  </Overlay>
</nav>

<style>
  .plugins-overlay{
    min-width: 200px;
    min-height: 100px;
  }

  button.plugin{
    background-color: transparent;
    color: inherit;
    margin: 0;
    border: 1px solid blue;
    padding: calc(var(--padding) / 2);
    border-radius: 69vw;
    width: fit-content;
    aspect-ratio: 1 / 1;
  }
  nav {
    background-color: var(--bgMedium);
    padding: var(--padding);

    display: flex;
    justify-content: space-between;
    align-items: center;
  }
</style>
