<script lang="ts">
    import type Manager from "../manager"
    import { onMount } from "svelte"

    let { manager }: { manager: Manager } = $props()

    let generatedColors = $state([])

    onMount(()=>{
        manager.state.onChange(manager.GENERATED_COLORS_KEY, (v) => {
            generatedColors = v
        })
    })
</script>

<div class="colors-preview">
    {#each generatedColors as color}
        <div class="color-box" style="background-color: {color}" title={color}></div>
    {/each}
</div>

<style>
    .colors-preview{
        display: flex;
        border: 1px solid currentColor;
        width: fit-content;
    }
    .color-box {
        min-width: calc(var(--padding) / 2);
        height: var(--padding);
    }
</style>
