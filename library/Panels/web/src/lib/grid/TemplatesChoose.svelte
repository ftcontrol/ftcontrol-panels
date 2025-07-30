<script lang="ts">
  import { global } from "$lib"
  import { Button, Overlay } from "ftc-panels"
  import CanvasRender from "./CanvasRender.svelte"

  let { set }: { set: (pID: string, tID: string) => void } = $props()
</script>

<Overlay triggerStyle={"flex-grow: 1;"}>
  {#snippet trigger()}
    <Button style={"width: 100%;"}>Templates</Button>
  {/snippet}
  {#snippet overlay({ close }: { close: () => void })}
    <div class="possibilities">
      {#each global.plugins as p}
        {#each p.details.templates as t}
          <button
            class="choose"
            onclick={() => {
              close()
              set(p.details.id, t.name)
            }}
          >
            <h4>{p.details.name}</h4>
            <p>{t.name}</p>
            <CanvasRender {t} pID={p.details.id} />
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
</style>
