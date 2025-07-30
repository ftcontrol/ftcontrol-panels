<script lang="ts">
  import Options from "$lib/icons/Options.svelte"
  import { Overlay } from "ftc-panels"
  import { manager } from "./manager.svelte"
  import NavletContent from "$lib/navlets/NavletContent.svelte"
  import { global } from "$lib"
  import NavletsChoose from "./NavletsChoose.svelte"
  import { onMount } from "svelte"

  onMount(() => {
    manager.load()
  })
</script>

<section>
  <div class="main">
    {#if global.isConnected}
      {#each manager.navlets as navlet}
        {#if manager.isValid(navlet.pluginID, navlet.navletID)}
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
              {#if manager.isValid(navlet.pluginID, navlet.navletID)}
                <p>{navlet.pluginID} / {navlet.navletID}</p>

                <button
                  onclick={() => manager.removeNavlet(index)}
                  title="Remove navlet">✕</button
                >
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

          <button
            onclick={() => {
              manager.addNavlet()
            }}
            style="margin-top: 1rem;"
          >
            + Add Navlet
          </button>
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
