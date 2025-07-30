<script lang="ts">
  import Options from "$lib/icons/Options.svelte"
  import { Button, Overlay } from "ftc-panels"
  import NavletContent from "$lib/navlets/NavletContent.svelte"
  import { global } from "$lib"
  import NavletsChoose from "./NavletsChoose.svelte"
  import { setContext } from "svelte"
  import type { Manager } from "$lib/grid/widgets.svelte"

  let {
    manager = $bindable(),
  }: {
    manager: Manager
  } = $props()

  setContext("manager", manager)
</script>

<section>
  <div class="main">
    {#if global.isConnected}
      {#each manager.navlets as navlet}
        {#if manager.isValidNavlet(navlet.pluginID, navlet.navletID)}
          <div class="navlet">
            <NavletContent
              pluginID={navlet.pluginID}
              widgetID={navlet.navletID}
            />
          </div>
        {/if}
      {/each}
    {/if}
  </div>
  {#if global.isConnected}
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
          {#each manager.navlets as navlet, index}
            <div style="display: flex; gap: 0.5rem; align-items: center;">
              {#if manager.isValidNavlet(navlet.pluginID, navlet.navletID)}
                <div style="display: flex; gap: 1rem;">
                  <Button>{navlet.pluginID} / {navlet.navletID}</Button>

                  <Button
                    onclick={() => manager.removeNavlet(index)}
                    title="Remove navlet">✕</Button
                  >
                </div>
              {:else}
                <NavletsChoose
                  set={(pID, wID) => {
                    navlet.pluginID = pID
                    navlet.navletID = wID
                  }}
                />
              {/if}
            </div>
          {/each}

          <Button
            onclick={() => {
              manager.addNavlet()
            }}
          >
            +
          </Button>
        </div>
      {/snippet}
    </Overlay>
  {/if}
</section>

<style>
  .main {
    display: flex;
    flex-grow: 1;
    max-width: calc(100vw - var(--padding) - 410px);
    overflow-x: auto;
  }
  section {
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
    flex: 0 0 auto;
  }
</style>
