<script lang="ts">
  import { global } from "$lib"
  import { Button, Overlay } from "ftc-panels"
  import PreviewBox from "../PreviewBox.svelte"
  import WidgetContent from "../WidgetContent.svelte"

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
            }}
          >
            <p>{p.details.name}</p>
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
  .possibilities {
    display: flex;
    gap: 1rem;
  }
  button.choose {
    all: unset;
    cursor: pointer;
    background-color: var(--bgLight);
    padding: 0.5em;
    border-radius: 0.8rem;
  }
</style>
