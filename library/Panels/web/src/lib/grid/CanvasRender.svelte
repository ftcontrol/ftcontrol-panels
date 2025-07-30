<script lang="ts">
  import { onMount, setContext, tick, type Snippet } from "svelte"
  import html2canvas from "html2canvas"
  import Layout from "./Layout.svelte"
  import { Manager } from "./widgets.svelte"
  import type { Template } from "ftc-panels"

  import Topbar from "$lib/Topbar.svelte"

  let { t }: { t: Template } = $props()

  let m = $state(new Manager(t))

  setContext("manager", m)

  let targetElement: HTMLDivElement
  let imageDataUrl: string = $state("")

  async function renderAsImage() {
    if (!targetElement) return

    await tick()

    const renderedCanvas = await html2canvas(targetElement, {
      backgroundColor: null,
    })
    imageDataUrl = renderedCanvas.toDataURL("image/png")
    targetElement.style.display = "none"
  }

  async function waitForStableRender(ticks = 3) {
    for (let i = 0; i < ticks; i++) {
      await tick()
    }
  }

  onMount(async () => {
    await waitForStableRender(3)
    await renderAsImage()
  })
</script>

<div
  bind:this={targetElement}
  style="width: 1400px; height: 600px; position: absolute; top: -9999px; left: -9999px;"
>
  <section>
    <Topbar />

    <Layout bind:manager={m} enableInteractions={false} />
  </section>
</div>

{#if imageDataUrl}
  <img src={imageDataUrl} alt="Rendered Component" />
{/if}

<style>
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
  }
  section {
    display: flex;
    flex-direction: column;
    height: 100%;
    width: 100%;
  }
  img {
    width: 100%;
    height: auto;
  }
</style>
