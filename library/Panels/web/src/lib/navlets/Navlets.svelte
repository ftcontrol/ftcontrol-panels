<script lang="ts">
  import Options from "$lib/icons/Options.svelte"
  import { Overlay } from "ftc-panels"
  import { manager } from "./manager.svelte"
  import NavletContent from "$lib/navlets/NavletContent.svelte"
  import { global } from "$lib"
</script>

<section>
  <div class="main">
    {#if global.isConnected}
      {#each manager.navlets as navlet}
        <div class="navlet">
          <NavletContent
            pluginID={navlet.pluginID}
            widgetID={navlet.navletID}
          />
        </div>
      {/each}
    {/if}
  </div>

  <Overlay>
    {#snippet trigger()}
      <div class="options">
        <Options />
      </div>
    {/snippet}
    {#snippet overlay({ close }: { close: () => void })}
      <div
        style="display: flex;flex-direction: column; gap: var(--padding);padding: var(--padding);"
      >
        <h1>Hi!</h1>
      </div>
    {/snippet}
  </Overlay>
</section>

<style>
  .main {
    flex-grow: 1;
  }
  section {
    background-color: green;
    height: 100%;
    flex-grow: 1;

    display: flex;
  }
  .options {
    padding-inline: var(--padding);
    height: 100%;
  }
  .navlet {
    padding-inline: var(--padding);
  }
</style>
