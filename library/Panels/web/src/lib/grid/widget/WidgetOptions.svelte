<script lang="ts">
  import Options from "$lib/icons/Options.svelte"
  import { Button, Overlay } from "ftc-panels"
  import { manager, type Widget } from "../widgets.svelte"
  import { tick } from "svelte"

  let { widget = $bindable() }: { widget: Widget } = $props()
</script>

<Overlay>
  {#snippet trigger()}
    <button class="icon"><Options /></button>
  {/snippet}
  {#snippet overlay({ close }: { close: () => void })}
    <div
      style="display: flex;flex-direction: column; gap: var(--padding);padding: var(--padding);"
    >
      <Button
        onclick={() => {
          manager.removeWidget(widget.id)
          close()
        }}>Remove Group</Button
      >
      <Button
        onclick={() => {
          if (widget.selected >= 0 && widget.widgets.length > 0) {
            widget.widgets.splice(widget.selected, 1)
          }
          close()
        }}
        disabled={widget.selected < 0 || widget.widgets.length <= 0}
        >Remove Widget</Button
      >
      <Button
        onclick={() => {
          if (widget.selected >= 0 && widget.widgets.length > 0) {
            widget.widgets[widget.selected] = {
              isMoving: false,
              pluginID: "",
              widgetID: "",
            }
          }
          close()
        }}
        disabled={widget.selected < 0 || widget.widgets.length <= 0}
        >Clear Widget</Button
      >
      <Button
        onclick={async () => {
          widget.widgets.push({
            isMoving: false,
            pluginID: "",
            widgetID: "",
          })

          widget.selected = widget.widgets.length - 1

          await tick()

          const tab = document.querySelector(
            `[data-widget="${widget.id}"][data-index="${widget.selected}"]`
          ) as HTMLElement

          tab.scrollIntoView({
            behavior: "smooth",
            block: "nearest",
            inline: "center",
          })

          close()
        }}>Insert Widget</Button
      >
    </div>
  {/snippet}
</Overlay>

<style>
  .icon {
    all: unset;
    cursor: pointer;
    width: fit-content;
    display: flex;
  }
</style>
