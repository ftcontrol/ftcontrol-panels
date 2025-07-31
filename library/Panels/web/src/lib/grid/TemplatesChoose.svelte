<script lang="ts">
  import { Button, Overlay } from "ftc-panels"
  import CanvasRender from "./CanvasRender.svelte"
  import type { Template } from "ftc-panels"
  import { global } from "$lib"

  let { set }: { set: (t: Template) => void } = $props()
</script>

<Overlay triggerStyle={"flex-grow: 1;"}>
  {#snippet trigger()}
    <Button style={"width: 100%;"}>Templates</Button>
  {/snippet}
  {#snippet overlay({ close }: { close: () => void })}
    <div class="possibilities">
      {#each global.allTemplates as t}
        <button
          class="choose"
          disabled={t.missingPlugins.length > 0}
          onclick={() => {
            close()
            set(t)
          }}
        >
          <h4>
            {global.plugins.find((it) => it.details.id == t.pluginID)?.details
              .name}
          </h4>
          <p>{t.name}</p>
          <CanvasRender {t} pID={t.pluginID} />
        </button>
      {/each}
    </div>
  {/snippet}
</Overlay>

<style>
  h4,
  p {
    margin: 0;
    text-align: center;
  }
  p {
    margin-bottom: var(--padding);
  }
  .possibilities {
    width: 750px;
    display: grid;
    grid-template-columns: repeat(auto-fit, minmax(200px, auto));
    gap: 1rem;
    padding: var(--padding);
  }
  button.choose {
    all: unset;
    cursor: pointer;
    background-color: var(--bgLight);
    padding: 0.5em;
    border-radius: 0.8rem;
  }
  button:disabled {
    opacity: 0.5;
  }
</style>
