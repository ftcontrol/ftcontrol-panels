<script lang="ts">
    import {Button, Overlay, TextInput} from "ftc-panels"
    import type Manager from "../manager"

    let {manager}: { manager: Manager } = $props()

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
                            .then(() => {
                                manager.notifications.add(
                                    "Text copied to clipboard"
                                )
                            })
                            .catch((err) => {
                                manager.notifications.add(
                                    "Failed to copy"
                                )
                            })
                    },
                },
                {
                    text: "Close",
                    task: () => {
                    },
                },
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
        }catch (e) {
            manager.notifications.add("Failed to import theme")
        }
        jsonPreset = ""
    }

    let jsonPreset = $state("")
</script>

<div>
    <label>
        Base Color:
        <input type="color" bind:value={baseColor}/>
    </label>

    <label>
        Brightness Offset:
        <input type="number" bind:value={brightnessOffset} min="0" max="20"/>
    </label>

    <label>
        Tint Color:
        <input type="color" bind:value={tintColor}/>
    </label>
    <label>
        Text:
        <input type="color" bind:value={textColor}/>
    </label>

    <label>
        Padding:
        <input type="number" bind:value={padding} min="0.1" max="2.5"/>
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

<div class="buttons">

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
    <Button
            onclick={() => {
   copyTemplate()
  }}
    >
        Share
    </Button>
    <Overlay
            triggerStyle="flex-grow: 1;"
            onStateChange={(isOpen) => {
            if (isOpen) {
              jsonPreset = ""
            }
          }}
    >
        {#snippet trigger()}
            <Button style="width: 100%;">Import</Button>
        {/snippet}
        {#snippet overlay({close}: { close: () => void })}
            <div class="new-menu">
                <TextInput bind:value={jsonPreset} placeholder={"JSON Preset"}/>
                <Button
                        style="width: 100%;"
                        onclick={() => {
                  setTheme()
                  close()
                }}>Import
                </Button
                >
            </div>
        {/snippet}
    </Overlay>
</div>

<style>
    .color-box {
        width: 100px;
        height: 100px;
        display: inline-block;
        margin: 8px;
        border-radius: 8px;
        border: 2px solid #333;
    }

    .buttons {
        display: flex;
        gap: var(--padding);
    }

    .new-menu {
        display: flex;
        flex-direction: column;
        align-items: center;
        gap: calc(var(--padding) / 2);
        padding: calc(var(--padding) / 2);
        max-height: 600px;
    }
</style>
