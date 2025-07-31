<script lang="ts">
  import WidgetTopBar from "./WidgetTopBar.svelte"
  import WidgetResize from "./WidgetResize.svelte"
  import WidgetChoose from "./WidgetChoose.svelte"
  import { getContext } from "svelte"
  import type { ExtendedWidgetGroup, Manager } from "../widgets.svelte"
  import WidgetContent from "../WidgetContent.svelte"
  const manager = getContext("manager") as Manager

  let {
    widget = $bindable(),
    isPossible,
  }: { widget: ExtendedWidgetGroup; isPossible: boolean } = $props()
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
            class="wrapper"
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
  .wrapper {
    height: 100%;
  }
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
