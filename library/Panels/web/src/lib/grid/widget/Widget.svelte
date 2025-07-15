<script lang="ts">
  import { Button, Overlay } from "ftc-panels"
  import { manager, type Widget } from "../widgets.svelte"
  import { global } from "$lib"
  import WidgetContent from "../WidgetContent.svelte"
  import PreviewBox from "../PreviewBox.svelte"
  import WidgetTopBar from "./WidgetTopBar.svelte"
  import WidgetResize from "./WidgetResize.svelte"
  import WidgetChoose from "./WidgetChoose.svelte"

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

    <section>
      {#if widget.widgets.length > 0}
        {#each widget.widgets, index}
          <div
            style={index == widget.selected
              ? "display:block;"
              : "display:none;"}
          >
            {#if widget.widgets[index].pluginID != "" && widget.widgets[index].widgetID != "" && manager.exists(widget.widgets[index].pluginID, widget.widgets[index].widgetID)}
              <WidgetContent
                pluginID={widget.widgets[index].pluginID}
                widgetID={widget.widgets[index].widgetID}
              />
            {:else}
              <WidgetChoose
                set={(pID, wID) => {
                  widget.widgets[index].pluginID = pID
                  widget.widgets[index].widgetID = wID
                }}
              />
            {/if}
          </div>
        {/each}
      {:else}
        <WidgetChoose
          set={(pID, wID) => {
            widget.widgets.push({
              isMoving: false,
              pluginID: pID,
              widgetID: wID,
            })
          }}
        />
      {/if}
    </section>
    <WidgetResize bind:widget bind:isPossible />
  </div>
</div>

<style>
  section {
    padding: var(--padding);
    overflow: auto;
    flex-grow: 1;
  }
  .item {
    position: absolute;
    top: calc(var(--y) * var(--height) + var(--yMove));
    left: calc(var(--x) * var(--width) + var(--xMove));
    height: calc(var(--h) * var(--height) + var(--yOffset));
    width: calc(var(--w) * var(--width) + var(--xOffset));
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
</style>
