<script lang="ts">
  import Options from "$lib/icons/Options.svelte"
  import { Button, Overlay } from "ftc-panels"
  import NavletContent from "$lib/navlets/NavletContent.svelte"
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

<div class="main">
  {#each manager.navlets as navlet}
    {#if manager.isValidNavlet(navlet.pluginID, navlet.navletID)}
      <div class="navlet">
        <NavletContent pluginID={navlet.pluginID} widgetID={navlet.navletID} />
      </div>
    {/if}
  {/each}

  <Overlay triggerStyle={"margin-top: 6px;"}>
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

                <Button onclick={() => manager.removeNavlet(index)}>✕</Button>
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
</div>

<style>
  .main {
    display: flex;
    align-items: center;
    gap: var(--padding);
    height: 100%;
    flex-grow: 1;
  }
</style>
