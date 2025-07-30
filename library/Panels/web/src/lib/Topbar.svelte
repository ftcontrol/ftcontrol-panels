<script lang="ts">
  import Panels from "$lib/Panels.svelte"
  import { Button, Overlay, TextInput } from "ftc-panels"
  import { global } from "$lib"
  import { goto } from "$app/navigation"
  import Navlets from "./navlets/Navlets.svelte"

  import Options from "./icons/Options.svelte"

  import { getContext } from "svelte"
  import type { Manager } from "./grid/widgets.svelte"
  import TemplatesChoose from "./grid/TemplatesChoose.svelte"
  var manager = getContext("manager") as Manager
</script>

<nav>
  <a href="/">
    <Panels />
  </a>

  <p>
    {global.isPrepared ? "Connected" : "Waiting"}
  </p>

  {#if global.isPrepared}
    <Navlets bind:manager />
  {:else}
    <section style="flex-grow: 1;"></section>
  {/if}

  <a href="/docs">Docs</a>

  <Overlay>
    {#snippet trigger({ isOpen }: { isOpen: boolean })}
      Presets
    {/snippet}
    {#snippet overlay({ close }: { close: () => void })}
      <div class="presets-overlay">
        {#each manager.presets.data as preset, index}
          <div class="preset">
            <Button
              selected={manager.presets.selected == index}
              onclick={() => {
                manager.change(index)
              }}
            >
              {preset.name}
            </Button>
            <Overlay>
              {#snippet trigger()}
                <Options />
              {/snippet}
              {#snippet overlay({ close }: { close: () => void })}
                <TextInput
                  bind:value={manager.presets.data[index].name}
                  oninput={() => {
                    manager.save()
                  }}
                />
                <Button
                  disabled={manager.presets.data.length == 1}
                  onclick={() => {
                    manager.deletePreset(index)
                    close()
                  }}
                >
                  x
                </Button>
                <Button
                  disabled={manager.presets.data.length == 1}
                  onclick={() => {
                    manager.save()
                    const temp = manager.unprocessTemplate(
                      manager.presets.data[index]
                    )
                    alert(JSON.stringify(temp))
                    close()
                  }}
                >
                  Copy
                </Button>
              {/snippet}
            </Overlay>
          </div>
        {/each}
        <TemplatesChoose set={() => {}} />
        <Button
          onclick={() => {
            manager.newPreset()
          }}
        >
          +
        </Button>
      </div>
    {/snippet}
  </Overlay>

  <Overlay>
    {#snippet trigger({ isOpen }: { isOpen: boolean })}
      Plugins
    {/snippet}
    {#snippet overlay({ close }: { close: () => void })}
      <div class="plugins-overlay">
        <Button
          onclick={() => {
            goto("/plugins")
            close()
          }}>Details</Button
        >
        <div class="grid">
          {#each global.plugins as plugin}
            <button
              class="plugin"
              onclick={() => {
                goto(`/plugins/${plugin.details.id}`)
                close()
              }}
            >
              {plugin.details.letterName}
            </button>
          {/each}
        </div>
        {#each global.skippedPlugins as details}
          <button class="plugin" disabled onclick={close}>
            {details.letterName}
          </button>
        {/each}
      </div>
    {/snippet}
  </Overlay>
</nav>

<style>
  a {
    color: inherit;
    text-decoration: none;
  }
  .plugins-overlay {
    min-width: 200px;
    min-height: 100px;
    max-height: 400px;

    overflow-y: auto;

    padding: calc(var(--padding) / 2);
  }
  .presets-overlay {
    min-width: 200px;
    min-height: 100px;
    max-height: 400px;

    overflow-y: auto;

    display: flex;
    flex-direction: column;

    padding: calc(var(--padding) / 2);
    gap: calc(var(--padding) / 2);
  }
  .preset {
    display: flex;
    gap: calc(var(--padding) / 2);
  }
  .grid {
    margin-top: 0.5rem;

    display: grid;
    grid-template-columns: repeat(auto-fit, minmax(32px, auto));
    gap: calc(var(--padding) / 2);
  }

  button.plugin {
    background-color: transparent;
    color: inherit;
    margin: 0;
    border: 1px solid var(--bgLight);
    padding: calc(var(--padding) / 2);
    border-radius: 69vw;
    width: 32px;
    height: 32px;
    display: grid;
    place-content: center;
    cursor: pointer;
  }
  nav {
    background-color: var(--bgMedium);
    padding: 0 var(--padding);

    display: flex;
    justify-content: space-between;
    align-items: center;

    border-radius: 1rem;
    margin: 0.5rem;
    margin-bottom: 0;

    gap: var(--padding);
    max-width: 100%;

    overflow-x: auto;
    overflow-y: hidden;
    min-height: 48px;
  }
</style>
