<script lang="ts">
  import { global } from "$lib"
  import { Button, Overlay } from "ftc-panels"
  import PreviewBox from "../PreviewBox.svelte"
  import WidgetContent from "../WidgetContent.svelte"
  import { getContext } from "svelte"
  import type { Manager } from "../widgets.svelte"
  const manager = getContext("manager") as Manager

  let { set }: { set: (pID: string, wID: string) => void } = $props()
</script>

<Overlay>
  {#snippet trigger()}
    <Button>Choose</Button>
  {/snippet}
  {#snippet overlay({ close }: { close: () => void })}
    <div class="possibilities">
      {#each global.plugins as p}
        {#each p.details.widgets as w}
          <button
            class="choose"
            onclick={() => {
              close()
              set(p.details.id, w.name)
              manager.save()
            }}
          >
            <h4>{p.details.name}</h4>
            <p>{w.name}</p>
            <PreviewBox scale={0.5}>
              <WidgetContent pluginID={p.details.id} widgetID={w.name} />
            </PreviewBox>
          </button>
        {/each}
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
    width: 500px;
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
</style>
