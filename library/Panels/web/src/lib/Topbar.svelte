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
  import Delete from "./icons/Delete.svelte"
  import Add from "./icons/Add.svelte"
  import Copy from "./icons/Copy.svelte"
  import Plugins from "./icons/Plugins.svelte"
  import Docs from "./icons/Docs.svelte"
  import Presets from "./icons/Presets.svelte"
  import Bell from "./icons/Bell.svelte"
  import AllNotifications from "./AllNotifications.svelte"
  var manager = getContext("manager") as Manager

  let jsonPreset = $state("")

  import { getFirstPlugin } from "ftc-panels-docs"

  const first = getFirstPlugin(global.plugins.map((it) => it.details))

  const url = first ? `/docs/${first}` : "/docs"
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

  <Overlay
    triggerStyle={"display: flex;justify-content: center;align-items: center;"}
  >
    {#snippet trigger({ isOpen })}
      <Bell />
    {/snippet}
    {#snippet overlay()}
      <div class="bell-menu">
        <h1>Notifications</h1>

        {#each global.notifications}
          <AllNotifications />
        {:else}
          <p>No notifications</p>
        {/each}
      </div>
    {/snippet}
  </Overlay>

  <a href={url}>
    <Docs />
  </a>

  <Overlay
    triggerStyle={"display: flex;justify-content: center;align-items: center;"}
  >
    {#snippet trigger({ isOpen })}
      <Presets />
    {/snippet}
    {#snippet overlay({ close }: { close: () => void })}
      <div class="presets-overlay">
        <h1>Presets</h1>
        {#each manager.presets.data as preset, index}
          <div class="preset">
            <Button
              selected={manager.presets.selected === index}
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
                <div class="menu">
                  <TextInput
                    bind:value={manager.presets.data[index].name}
                    oninput={() => {
                      manager.save()
                    }}
                  />
                  <Button
                    transparent={true}
                    disabled={manager.presets.data.length === 1}
                    onclick={() => {
                      manager.deletePreset(index)
                      close()
                    }}
                  >
                    <Delete />
                  </Button>
                  <Button
                    transparent={true}
                    onclick={() => {
                      manager.save()
                      const temp = manager.unprocessTemplate(
                        manager.presets.data[index]
                      )
                      close()
                      global.notificationsManager.addAction(
                        JSON.stringify(temp),
                        [
                          {
                            text: "Copy",
                            task: () => {
                              navigator.clipboard
                                .writeText(JSON.stringify(temp))
                                .then(() => {
                                  global.notificationsManager.add(
                                    "Text copied to clipboard"
                                  )
                                })
                                .catch((err) => {
                                  global.notificationsManager.add(
                                    "Failed to copy"
                                  )
                                })
                            },
                          },
                          {
                            text: "Close",
                            task: () => {},
                          },
                        ]
                      )
                    }}
                  >
                    <Copy />
                  </Button>
                </div>
              {/snippet}
            </Overlay>
          </div>
        {/each}
        <TemplatesChoose
          set={(t) => {
            manager.addTemplate(t)
            close()
          }}
        />
        <Overlay
          onStateChange={(isOpen) => {
            if (isOpen) {
              jsonPreset = ""
            }
          }}
        >
          {#snippet trigger()}
            <Button style="width: 100%;">Import</Button>
          {/snippet}
          {#snippet overlay({ close }: { close: () => void })}
            <div class="new-menu">
              <TextInput bind:value={jsonPreset} placeholder={"JSON Preset"} />
              <Button
                style="width: 100%;"
                onclick={() => {
                  manager.addTemplate(JSON.parse(jsonPreset))
                  close()
                }}>Create</Button
              >
            </div>
          {/snippet}
        </Overlay>
        <Button
          onclick={() => {
            manager.newPreset()
            close()
          }}
        >
          <Add />
        </Button>
      </div>
    {/snippet}
  </Overlay>

  <Overlay
    triggerStyle={"display: flex;justify-content: center;align-items: center;"}
  >
    {#snippet trigger({ isOpen })}
      <Plugins />
    {/snippet}
    {#snippet overlay({ close }: { close: () => void })}
      <div class="plugins-overlay">
        <h1>Plugins</h1>

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
          {#each global.skippedPlugins as details}
            <button class="plugin" disabled onclick={close}>
              {details.letterName}
            </button>
          {/each}
        </div>

        <Button
          style="margin-top: 0.5rem"
          onclick={() => {
            goto("/plugins")
            close()
          }}>Details</Button
        >
      </div>
    {/snippet}
  </Overlay>
</nav>

<style>
  h1 {
    margin: 0.5rem;
    font-size: 1.5rem;
  }
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
    align-items: center;
    gap: calc(var(--padding) / 2);
  }
  .menu {
    display: flex;
    align-items: center;
    gap: calc(var(--padding) / 2);
    padding: calc(var(--padding) / 2);
    max-height: 600px;
  }

  .new-menu {
    display: flex;
    flex-direction: column;
    align-items: center;
    gap: calc(var(--padding) / 2);
    padding: calc(var(--padding) / 2);
    max-height: 600px;
  }

  .bell-menu {
    display: flex;
    flex-direction: column;
    align-items: center;
    gap: calc(var(--padding) / 2);
    padding: calc(var(--padding) / 2);
    max-height: 800px;
    min-width: 300px;
    max-width: 400px;
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
    max-height: 64px;
    min-height: 64px;
  }
</style>
