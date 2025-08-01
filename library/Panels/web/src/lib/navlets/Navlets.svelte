<script lang="ts">
  import Options from "$lib/icons/Options.svelte"
  import { Button, Overlay } from "ftc-panels"
  import NavletContent from "$lib/navlets/NavletContent.svelte"
  import NavletsChoose from "./NavletsChoose.svelte"
  import { setContext } from "svelte"
  import type { Manager } from "$lib/grid/widgets.svelte"
  import Add from "$lib/icons/Add.svelte"
  import Delete from "$lib/icons/Delete.svelte"

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
      <div class="menu">
        {#each manager.navlets as navlet, index}
          {#if manager.isValidNavlet(navlet.pluginID, navlet.navletID)}
            <Button>{navlet.pluginID} / {navlet.navletID}</Button>

            <Button
              transparent={true}
              onclick={() => manager.removeNavlet(index)}
            >
              <Delete />
            </Button>
          {:else}
            <NavletsChoose
              set={(pID, wID) => {
                navlet.pluginID = pID
                navlet.navletID = wID
              }}
            />
          {/if}
        {/each}

        <Button
          style="grid-column-start: span 2;"
          onclick={() => {
            manager.addNavlet()
          }}
        >
          <Add />
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
  .menu {
    display: grid;
    gap: calc(var(--padding) / 2);
    padding: calc(var(--padding) / 2);
    grid-template-columns: auto 32px;
  }
</style>
