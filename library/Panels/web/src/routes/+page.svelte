<script lang="ts">
  import { Button, DynamicComponent, Overlay } from "ftc-panels"
  import ThemeGenerator from "$lib/ThemeGenerator.svelte"
  import { global } from "$lib"
  import Layout from "$lib/grid/Layout.svelte"
</script>

<Layout />

<!-- 
<h1>Hi!</h1>
{#each global.plugins as plugin}
  <div
    style="background-color:var(--bgLight);padding:var(--padding);margin-bottom:var(--padding);"
  >
    <h2>{plugin.details.name}</h2>
    <p>{plugin.details.id}</p>
    <p>{plugin.details.author}</p>
  </div>
  {#each plugin.details.widgets as widget}
    <div class="card">
      <DynamicComponent
        globalSocket={global.socket}
        textContent={widget.textContent || ""}
        id={plugin.details.id}
      />
    </div>
  {/each}
{/each}
<ThemeGenerator />

<Overlay>
  {#snippet trigger({ isOpen }: { isOpen: boolean })}
    Lazar: {isOpen}
  {/snippet}
  {#snippet overlay({ close }: { close: () => void })}
    <h1>Hi</h1>
    <button onclick={close}>Close</button>
  {/snippet}
</Overlay> -->

<style>
  .card {
    margin-bottom: var(--padding);
    background-color: var(--bgMedium);
    padding: var(--padding);
  }
</style>
