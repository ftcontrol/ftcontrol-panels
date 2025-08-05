<script lang="ts">
    import { Button, Overlay, TextInput, NumberInput, ColorInput } from "ftc-panels"
    import type Manager from "../manager"

    let { manager }: { manager: Manager } = $props()

    let baseColor = $state(manager.theme.baseColor)
    let textColor = $state(manager.theme.textColor)
    let brightnessOffset = $state(manager.theme.brightnessOffset)
    let padding = $state(manager.theme.padding)
    let tintColor = $state(manager.theme.tintColor)
    let generatedColors: string[] = $state([])

    let jsonPreset = $state("")

    $effect(() => {
        manager.theme = {
            baseColor,
            textColor,
            brightnessOffset,
            padding,
            tintColor,
        }
        manager.saveToLocalStorage()
        manager.apply()
        generatedColors = manager.generatedColors
    })

    function copyTemplate() {
        const temp = manager.theme
        manager.notifications.addAction(
            JSON.stringify(temp),
            [
                {
                    text: "Copy",
                    task: () => {
                        navigator.clipboard
                            .writeText(JSON.stringify(temp))
                            .then(() => manager.notifications.add("Text copied to clipboard"))
                            .catch(() => manager.notifications.add("Failed to copy"))
                    },
                },
                { text: "Close", task: () => {} },
            ]
        )
    }

    function setTheme() {
        try {
            manager.theme = JSON.parse(jsonPreset)
            manager.saveToLocalStorage()
            manager.apply()
            baseColor = manager.theme.baseColor
            textColor = manager.theme.textColor
            brightnessOffset = manager.theme.brightnessOffset
            padding = manager.theme.padding
            tintColor = manager.theme.tintColor
            generatedColors = manager.generatedColors
        } catch {
            manager.notifications.add("Failed to import theme")
        }
        jsonPreset = ""
    }
</script>

<div class="theme-settings">
    <div class="form-group">
        <label>
            <span>Base Color</span>
            <ColorInput bind:value={baseColor} />
        </label>
        <label>
            <span>Brightness Offset</span>
            <NumberInput bind:value={brightnessOffset} min="0" max="20" />
        </label>
        <label>
            <span>Tint Color</span>
            <ColorInput bind:value={tintColor} />
        </label>
        <label>
            <span>Text Color</span>
            <ColorInput bind:value={textColor} />
        </label>
        <label>
            <span>Padding</span>
            <NumberInput bind:value={padding} step="0.1" min="0.1" max="2.5" />
        </label>
    </div>

    <div class="colors-preview">
        {#each generatedColors as color}
            <div class="color-box" style="background-color: {color}" title={color}></div>
        {/each}
        <div class="color-box" style="background-color: {textColor}" title={textColor}></div>
    </div>

    <div class="buttons">
        <Button onclick={() => {
            manager.reset()
            baseColor = manager.theme.baseColor
            textColor = manager.theme.textColor
            brightnessOffset = manager.theme.brightnessOffset
            padding = manager.theme.padding
            tintColor = manager.theme.tintColor
            generatedColors = manager.generatedColors
        }}>Reset</Button>

        <Button onclick={copyTemplate}>Share</Button>

        <Overlay triggerStyle="flex-grow: 1;" onStateChange={(isOpen) => { if (isOpen) jsonPreset = "" }}>
            {#snippet trigger()}
                <Button style="width: 100%;">Import</Button>
            {/snippet}
            {#snippet overlay({ close }: { close: () => void })}
                <div class="new-menu">
                    <TextInput bind:value={jsonPreset} placeholder="JSON Preset" />
                    <Button style="width: 100%;" onclick={() => { setTheme(); close(); }}>Import</Button>
                </div>
            {/snippet}
        </Overlay>
    </div>
</div>

<style>
    .theme-settings {
        display: flex;
        flex-direction: column;
        gap: var(--padding);
        padding: var(--padding);
        max-width: 500px;
        margin: 0 auto;
    }
    .form-group {
        display: flex;
        flex-direction: column;
        gap: 12px;
    }

    .form-group label {
        display: flex;
        justify-content: space-between;
        align-items: center;
        gap: 12px;
        font-size: 0.95rem;
    }

    .form-group input[type="color"],
    .form-group input[type="number"] {
        flex: 1;
        padding: 4px;
    }

    .colors-preview {
        display: flex;
        flex-wrap: wrap;
        gap: var(--padding);
        margin-top: 8px;
    }

    .color-box {
        height: 64px;
        border-radius: calc(var(--padding) / 2);
        border: 2px solid #444;
        box-shadow: 0 1px 4px rgba(0, 0, 0, 0.1);
        flex-grow: 1;
    }

    .buttons {
        display: flex;
        gap: 12px;
        flex-wrap: wrap;
    }

    .new-menu {
        display: flex;
        flex-direction: column;
        gap: 8px;
        padding: 12px;
        min-width: 240px;
    }
</style>
