<script lang="ts">
  import { onMount } from 'svelte';
  import tinycolor from 'tinycolor2';

  let baseColor = $state('#040606');
  let textColor = $state('#ffffff');
  let brightnessOffset = $state(10);
  let tintColor = $state('#ff0000');
  let generatedColors: string[] = $state([]);

  function applyTint(color: string, tint: string): string {
    return tinycolor.mix(color, tint, 30).toHexString();
  }

  function adjustBrightness(color: string, offset: number): string {
    return tinycolor(color).brighten(offset).toHexString();
  }

  function generateColors(brightnessOffset: number, baseColor: string, tintColor: string) {
    generatedColors = Array.from({ length: 3 }, (_, i) => {
      let brightened = adjustBrightness(baseColor, i * brightnessOffset);
      return applyTint(brightened, tintColor);
    });
  }

 $effect(()=>{
    generateColors(brightnessOffset, baseColor, tintColor)
 })
</script>

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
</div>

<div style="margin-top: 16px;">
  {#each generatedColors as color}
    <div class="color-box" style="background-color: {color}" title={color}></div>
  {/each}
    <div class="color-box" style="background-color: {textColor}" title={textColor}></div>
</div>
