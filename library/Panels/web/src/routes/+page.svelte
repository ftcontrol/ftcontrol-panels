<script lang="ts">
  import { onDestroy } from "svelte"
  import "./global.css"
  import { DynamicComponent, Overlay } from "ftc-panels"
  import ThemeGenerator from "$lib/ThemeGenerator.svelte"
  import Topbar from "$lib/Topbar.svelte"
  import { global } from "$lib"

  onDestroy(() => {
    global.close()
  })
</script>

<section>
  <Topbar />

  {#await global.init()}
    <p>Loading</p>
  {:then}
    <h1>Hi!</h1>
    {#each global.plugins as plugin}
      <div
        style="background-color:var(--bgLight);padding:0.5rem;margin-bottom:1rem;"
      >
        <h2>{plugin.details.name}</h2>
        <p>{plugin.details.id}</p>
        <p>{plugin.details.author}</p>
      </div>
      {#each plugin.details.widgets as widget}
        <DynamicComponent
          globalSocket={global.socket}
          textContent={widget.textContent || ""}
          id={plugin.details.id}
        />
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
    </Overlay>
  {:catch error}
    <p>{error}</p>
  {/await}
</section>

<style>
  section {
    overflow-y: auto;
  }
</style>
