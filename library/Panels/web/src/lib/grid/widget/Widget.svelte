<script lang="ts">
  import { Button, DynamicComponent, Overlay } from "ftc-panels"
  import { manager, type Widget } from "../widgets.svelte"
  import { global } from "$lib"
  import WidgetContent from "../WidgetContent.svelte"
  import PreviewBox from "../PreviewBox.svelte"
  import WidgetTopBar from "./WidgetTopBar.svelte"
  import WidgetResize from "./WidgetResize.svelte"

  let {
    widget = $bindable(),
    isPossible,
  }: { widget: Widget; isPossible: boolean } = $props()
  let isSmall = $derived(widget.w == 1 && widget.h == 1)
</script>

<div
  class="item"
  class:transparent={widget.isMoving && !isPossible}
  style="--x:{widget.x};--y:{widget.y};--w:{widget.w};--h:{widget.h};--xOffset:{widget
    .offset.x}px;--yOffset:{widget.offset.y}px;--xMove:{widget.move
    .x}px;--yMove:{widget.move.y}px;"
>
  <div
    class="content"
    class:invalid={!manager.isValid &&
      !isPossible &&
      manager.isMoving &&
      manager.placeStart == null}
  >
    <WidgetTopBar bind:widget bind:isPossible />

    <section class="i">
      {#if widget.widgets.length > 0}
        {#each widget.widgets, index}
          <div class="w" class:selected={index == widget.selected}>
            {#if widget.widgets[index].pluginID != "" && widget.widgets[index].widgetID != ""}
              <WidgetContent
                pluginID={widget.widgets[index].pluginID}
                widgetID={widget.widgets[index].widgetID}
              />
            {:else}
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
                            widget.widgets[index].pluginID = p.details.id
                            widget.widgets[index].widgetID = w.name
                          }}
                        >
                          <p>{p.details.name}</p>
                          <p>{w.name}</p>
                          <PreviewBox scale={0.5}>
                            <WidgetContent
                              pluginID={p.details.id}
                              widgetID={w.name}
                            />
                          </PreviewBox>
                        </button>
                      {/each}
                    {/each}
                  </div>
                {/snippet}
              </Overlay>
            {/if}
          </div>
        {/each}
      {:else}
        <p>No widgets found</p>

        <Button
          onclick={() => {
            widget.widgets.push({
              isMoving: false,
              pluginID: "",
              widgetID: "",
            })
          }}>Insert Empty Widget</Button
        >
      {/if}
    </section>
    <WidgetResize bind:widget bind:isPossible />
  </div>
</div>

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

  .i {
    padding: 0 var(--padding) var(--padding) var(--padding);
    overflow: auto;
    flex-grow: 1;
  }
  .w {
    display: none;
  }

  .w.selected {
    display: block;
  }
  .item {
    position: absolute;
    top: calc(var(--y) * var(--height) + var(--yMove));
    left: calc(var(--x) * var(--width) + var(--xMove));
    height: calc(var(--h) * var(--height) + var(--yOffset));
    width: calc(var(--w) * var(--width) + var(--xOffset));
  }
  .item {
    display: flex;
    flex-direction: column;
    padding: var(--spacing);
  }
  .content {
    position: relative;
    flex-grow: 1;
    background-color: var(--bgMedium);
    overflow: hidden;

    display: flex;
    flex-direction: column;

    border-radius: 1rem;
  }

  .content.invalid {
    background-color: red;
  }

  .transparent {
    opacity: 0.5;
  }
  p {
    margin: 0;
  }
</style>
