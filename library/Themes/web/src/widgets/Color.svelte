<script lang="ts">
  import { Button } from "ftc-panels"
  import type Manager from "../manager"

  let { manager }: { manager: Manager } = $props()

  let baseColor = $state(manager.theme.baseColor)
  let textColor = $state(manager.theme.textColor)
  let brightnessOffset = $state(manager.theme.brightnessOffset)
  let padding = $state(manager.theme.padding)
  let tintColor = $state(manager.theme.tintColor)
  let generatedColors: string[] = $state([])

  $effect(() => {
    manager.theme = {
      baseColor: baseColor,
      textColor: textColor,
      brightnessOffset: brightnessOffset,
      padding: padding,
      tintColor: tintColor,
    }
    manager.saveToCookies()
    manager.apply()
    generatedColors = manager.generatedColors
  })
</script>

<div>
  <label>
    Base Color:
    <input type="color" bind:value={baseColor} />
  </label>

  <label>
    Brightness Offset:
    <input type="number" bind:value={brightnessOffset} />
  </label>

  <label>
    Tint Color:
    <input type="color" bind:value={tintColor} />
  </label>
  <label>
    Text:
    <input type="color" bind:value={textColor} />
  </label>

  <label>
    Padding:
    <input type="number" bind:value={padding} />
  </label>
</div>

<div style="margin-top: 16px;">
  {#each generatedColors as color}
    <div
      class="color-box"
      style="background-color: {color}"
      title={color}
    ></div>
  {/each}
  <div
    class="color-box"
    style="background-color: {textColor}"
    title={textColor}
  ></div>
</div>

<Button
  onclick={() => {
    manager.reset()
    baseColor = manager.theme.baseColor
    textColor = manager.theme.textColor
    brightnessOffset = manager.theme.brightnessOffset
    padding = manager.theme.padding
    tintColor = manager.theme.tintColor
    generatedColors = manager.generatedColors
  }}
>
  Reset
</Button>

<style>
  .color-box {
    width: 100px;
    height: 100px;
    display: inline-block;
    margin: 8px;
    border-radius: 8px;
    border: 2px solid #333;
  }
</style>
