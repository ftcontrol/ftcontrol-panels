<script lang="ts">
  import { global } from "$lib"
  import PreviewBox from "$lib/grid/PreviewBox.svelte"
  import { Button, Overlay } from "ftc-panels"
  import NavletContent from "./NavletContent.svelte"
  import { getContext } from "svelte"
  import type { Manager } from "$lib/grid/widgets.svelte"
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
        {#each p.details.components.filter(it => it.type === "navlet") as w}
          <button
            class="choose"
            onclick={() => {
              close()
              set(p.details.id, w.id)
              manager.save()
            }}
          >
            <p>{p.details.name}</p>
            <h4>{w.id}</h4>
            <PreviewBox scale={1.75}>
              <NavletContent pluginID={p.details.id} widgetID={w.id} />
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
  h4 {
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
