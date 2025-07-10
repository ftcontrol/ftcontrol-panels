<script lang="ts">
  import Panels from "$lib/Panels.svelte"
  import { Overlay } from "ftc-panels"
  import { global } from "$lib"
</script>

<nav>
  <Panels />

  <p>
    {global.isConnected ? "Connected" : "Waiting"}
  </p>

  <Overlay>
    {#snippet trigger({ isOpen }: { isOpen: boolean })}
      Plugins
    {/snippet}
    {#snippet overlay({ close }: { close: void })}
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
  .plugins-overlay {
    min-width: 200px;
    min-height: 100px;
    display: flex;
    gap: calc(var(--padding) / 2);
  }

  button.plugin {
    background-color: transparent;
    color: inherit;
    margin: 0;
    border: 1px solid blue;
    padding: calc(var(--padding) / 2);
    border-radius: 69vw;
    width: 32px;
    height: 32px;
    display: grid;
    place-content: center;
  }
  nav {
    background-color: var(--bgMedium);
    padding: var(--padding);

    display: flex;
    justify-content: space-between;
    align-items: center;
  }
</style>
