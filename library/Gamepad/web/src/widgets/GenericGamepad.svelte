<script lang="ts">
    import {onMount} from "svelte"
    import type Manager from "../manager"
    import GamepadDrawing from "./GamepadDrawing.svelte"
    import type {GamepadData, GamepadTimestamps} from "../types"

    let {
        manager,
        gamepadIndex = 0,
    }: {
        manager: Manager
        defaultGamepadIndex: number
    } = $props()

    let selectedGamepadIndex = $state(gamepadIndex)

    function hasChange(lastSentGamepad: GamepadData): boolean {
        if (!lastSentGamepad) return true

        if (Math.abs(lastSentGamepad.leftStick.x) < 0.1)
            lastSentGamepad.leftStick.x = 0.0
        if (Math.abs(lastSentGamepad.leftStick.y) < 0.1)
            lastSentGamepad.leftStick.y = 0.0
        if (Math.abs(lastSentGamepad.rightStick.x) < 0.1)
            lastSentGamepad.rightStick.x = 0.0
        if (Math.abs(lastSentGamepad.rightStick.y) < 0.1)
            lastSentGamepad.rightStick.y = 0.0

        return (
            JSON.stringify({
                l1: false,
                l2: 0.0,
                r1: false,
                r2: 0.0,
                leftStick: {
                    x: 0,
                    y: 0,
                    value: false,
                },
                rightStick: {
                    x: 0,
                    y: 0,
                    value: false,
                },
                cross: false,
                circle: false,
                square: false,
                triangle: false,

                dpad_up: false,
                dpad_left: false,
                dpad_right: false,
                dpad_down: false,

                touchpad: false,
                options: false,
                share: false,
                ps: false,
            }) !== JSON.stringify(lastSentGamepad)
        )
    }

    function getCombinedGamepad(g1: GamepadData, g2: GamepadData): GamepadData {
        return {
            l1: g1.l1 || g2.l1,
            l2: g1.l2 !== 0 ? g1.l2 : g2.l2,
            r1: g1.r1 || g2.r1,
            r2: g1.r2 !== 0 ? g1.r2 : g2.r2,

            leftStick: {
                x: g1.leftStick.x !== 0 ? g1.leftStick.x : g2.leftStick.x,
                y: g1.leftStick.y !== 0 ? g1.leftStick.y : g2.leftStick.y,
                value: g1.leftStick.value || g2.leftStick.value,
            },
            rightStick: {
                x: g1.rightStick.x !== 0 ? g1.rightStick.x : g2.rightStick.x,
                y: g1.rightStick.y !== 0 ? g1.rightStick.y : g2.rightStick.y,
                value: g1.rightStick.value || g2.rightStick.value,
            },

            cross: g1.cross || g2.cross,
            circle: g1.circle || g2.circle,
            square: g1.square || g2.square,
            triangle: g1.triangle || g2.triangle,

            dpad_up: g1.dpad_up || g2.dpad_up,
            dpad_left: g1.dpad_left || g2.dpad_left,
            dpad_right: g1.dpad_right || g2.dpad_right,
            dpad_down: g1.dpad_down || g2.dpad_down,

            touchpad: g1.touchpad || g2.touchpad,
            options: g1.options || g2.options,
            share: g1.share || g2.share,
            ps: g1.ps || g2.ps,
        }
    }

    onMount(() => {
        const interval = setInterval(() => {
            now = performance.now()
            if (!hasChange(combinedGamepad)) return
            manager.socket.sendMessage("gamepad" + gamepadIndex, combinedGamepad)
        }, 50)

        let firstLoad = false

        var KEY = manager.FIRST_GAMEPAD_KEY

        if (gamepadIndex == 1) {
            KEY = manager.SECOND_GAMEPAD_KEY
        }

        manager.state.onChange(KEY, (data) => {
            externalGamepad = data

            if (!firstLoad) {
                firstLoad = true
                return
            }

            const currentTime = performance.now()

            externalGamepadTimestamps.l1 = currentTime
            externalGamepadTimestamps.l2 = currentTime
            externalGamepadTimestamps.r1 = currentTime
            externalGamepadTimestamps.r2 = currentTime
            externalGamepadTimestamps.leftStick.x = currentTime
            externalGamepadTimestamps.leftStick.y = currentTime
            externalGamepadTimestamps.leftStick.value = currentTime
            externalGamepadTimestamps.rightStick.x = currentTime
            externalGamepadTimestamps.rightStick.y = currentTime
            externalGamepadTimestamps.rightStick.value = currentTime
            externalGamepadTimestamps.cross = currentTime
            externalGamepadTimestamps.circle = currentTime
            externalGamepadTimestamps.square = currentTime
            externalGamepadTimestamps.triangle = currentTime

            externalGamepadTimestamps.dpad_up = currentTime
            externalGamepadTimestamps.dpad_left = currentTime
            externalGamepadTimestamps.dpad_right = currentTime
            externalGamepadTimestamps.dpad_down = currentTime

            externalGamepadTimestamps.touchpad = currentTime
            externalGamepadTimestamps.options = currentTime
            externalGamepadTimestamps.share = currentTime
            externalGamepadTimestamps.ps = currentTime
        })

        return () => clearInterval(interval)
    })

    $effect(() => {
        if (!hasChange(combinedGamepad)) return
        manager.socket.sendMessage("gamepad" + gamepadIndex, combinedGamepad)
    })

    let physicalGamepad: GamepadData = $state({
        l1: false,
        l2: 0.0,
        r1: false,
        r2: 0.0,
        leftStick: {
            x: 0,
            y: 0,
            value: false,
        },
        rightStick: {
            x: 0,
            y: 0,
            value: false,
        },
        cross: false,
        circle: false,
        square: false,
        triangle: false,

        dpad_up: false,
        dpad_left: false,
        dpad_right: false,
        dpad_down: false,

        touchpad: false,
        options: false,
        share: false,
        ps: false,
    })

    const THRESHOLD_MS = 600

    let now = $state(0)

    function filterGamepadByTimestamp(
        gamepad: GamepadData,
        timestamps: GamepadTimestamps,
        now: number
    ): GamepadData {
        const delta = (time: number) => performance.now() - time <= THRESHOLD_MS

        return {
            l1: delta(timestamps.l1) ? gamepad.l1 : false,
            l2: delta(timestamps.l2) ? gamepad.l2 : 0.0,
            r1: delta(timestamps.r1) ? gamepad.r1 : false,
            r2: delta(timestamps.r2) ? gamepad.r2 : 0.0,

            leftStick: {
                x: delta(timestamps.leftStick.x) ? gamepad.leftStick.x : 0,
                y: delta(timestamps.leftStick.y) ? gamepad.leftStick.y : 0,
                value: delta(timestamps.leftStick.value)
                    ? gamepad.leftStick.value
                    : false,
            },
            rightStick: {
                x: delta(timestamps.rightStick.x) ? gamepad.rightStick.x : 0,
                y: delta(timestamps.rightStick.y) ? gamepad.rightStick.y : 0,
                value: delta(timestamps.rightStick.value)
                    ? gamepad.rightStick.value
                    : false,
            },

            cross: delta(timestamps.cross) ? gamepad.cross : false,
            circle: delta(timestamps.circle) ? gamepad.circle : false,
            square: delta(timestamps.square) ? gamepad.square : false,
            triangle: delta(timestamps.triangle) ? gamepad.triangle : false,

            dpad_up: delta(timestamps.dpad_up) ? gamepad.dpad_up : false,
            dpad_left: delta(timestamps.dpad_left) ? gamepad.dpad_left : false,
            dpad_right: delta(timestamps.dpad_right) ? gamepad.dpad_right : false,
            dpad_down: delta(timestamps.dpad_down) ? gamepad.dpad_down : false,

            touchpad: delta(timestamps.touchpad) ? gamepad.touchpad : false,
            options: delta(timestamps.options) ? gamepad.options : false,
            share: delta(timestamps.share) ? gamepad.share : false,
            ps: delta(timestamps.ps) ? gamepad.ps : false,
        }
    }

    let externalGamepad: GamepadData = $state({
        l1: false,
        l2: 0.0,
        r1: false,
        r2: 0.0,
        leftStick: {
            x: 0,
            y: 0,
            value: false,
        },
        rightStick: {
            x: 0,
            y: 0,
            value: false,
        },
        cross: false,
        circle: false,
        square: false,
        triangle: false,

        dpad_up: false,
        dpad_left: false,
        dpad_right: false,
        dpad_down: false,

        touchpad: false,
        options: false,
        share: false,
        ps: false,
    })

    let externalGamepadTimestamps: GamepadTimestamps = $state({
        l1: 0.0,
        l2: 0.0,
        r1: 0.0,
        r2: 0.0,
        leftStick: {
            x: 0,
            y: 0,
            value: 0.0,
        },
        rightStick: {
            x: 0,
            y: 0,
            value: 0.0,
        },
        cross: 0.0,
        circle: 0.0,
        square: 0.0,
        triangle: 0.0,

        dpad_up: 0.0,
        dpad_left: 0.0,
        dpad_right: 0.0,
        dpad_down: 0.0,

        touchpad: 0.0,
        options: 0.0,
        share: 0.0,
        ps: 0.0,
    })

    let virtualGamepad = $state({
        l1: false,
        l2: 0.0,
        r1: false,
        r2: 0.0,
        leftStick: {
            x: 0,
            y: 0,
            value: false,
        },
        rightStick: {
            x: 0,
            y: 0,
            value: false,
        },
        cross: false,
        circle: false,
        square: false,
        triangle: false,

        dpad_up: false,
        dpad_left: false,
        dpad_right: false,
        dpad_down: false,

        touchpad: false,
        options: false,
        share: false,
        ps: false,
    })

    let combinedGamepad = $derived(
        getCombinedGamepad(physicalGamepad, virtualGamepad)
    )

    let gamepads = $state<Gamepad[]>([])
    let current = $state<Gamepad | null>(null)

    function updateGamepads() {
        gamepads = navigator.getGamepads
            ? (Array.from(navigator.getGamepads()).filter((g) => g) as Gamepad[])
            : []

        if (gamepads.length && selectedGamepadIndex != -1 && gamepads.length >= selectedGamepadIndex + 1) {
            current = gamepads[selectedGamepadIndex]

            physicalGamepad = {
                l1: current.buttons[4].value >= 1.0,
                l2: current.buttons[6].value,
                r1: current.buttons[5].value >= 1.0,
                r2: current.buttons[7].value,
                leftStick: {
                    x: current.axes[0],
                    y: current.axes[1],
                    value: current.buttons[10].value >= 1.0,
                },
                rightStick: {
                    x: current.axes[2],
                    y: current.axes[3],
                    value: current.buttons[11].value >= 1.0,
                },
                cross: current.buttons[0].value >= 1.0,
                circle: current.buttons[1].value >= 1.0,
                square: current.buttons[2].value >= 1.0,
                triangle: current.buttons[3].value >= 1.0,

                dpad_up: current.buttons[12].value >= 1.0,
                dpad_left: current.buttons[14].value >= 1.0,
                dpad_right: current.buttons[15].value >= 1.0,
                dpad_down: current.buttons[13].value >= 1.0,

                touchpad: current.buttons[17].value >= 1.0,
                options: current.buttons[9].value >= 1.0,
                share: current.buttons[8].value >= 1.0,
                ps: current.buttons[16].value >= 1.0,
            }
        } else {
            current = null
            selectedGamepadIndex = -1
        }
    }

    function plugIn(e: GamepadEvent) {
        console.log("Gamepad connected:", e.gamepad)
        selectedGamepadIndex = gamepadIndex
        updateGamepads()
    }

    function unPlug(e: GamepadEvent) {
        console.log("Gamepad disconnected:", e.gamepad)
        updateGamepads()
    }

    function pollGamepads() {
        updateGamepads()
        requestAnimationFrame(pollGamepads)
    }

    onMount(() => {
        window.addEventListener("gamepadconnected", plugIn)
        window.addEventListener("gamepaddisconnected", unPlug)

        pollGamepads()

        return () => {
            window.removeEventListener("gamepadconnected", plugIn)
            window.removeEventListener("gamepaddisconnected", unPlug)
        }
    })

    import {Overlay} from "ftc-panels"

    let gamepadsOptions = $derived.by(() => {
        const data = []
        if (-1 != selectedGamepadIndex) data.push(-1)
        for (let i = 0; i < gamepads.length; i++) {
            if (i != selectedGamepadIndex) data.push(i)
        }
        return data
    })

    function formatIndex(index: number): string {
        if (index < 0) return "NONE"
        return `Gamepad ${index}`
    }
</script>

<section class="gamepad { gamepadIndex?'second':'first' }">
    {#if gamepads.length}
        <section class="top-overlay">
            <Overlay>
                {#snippet trigger()}
                    <button class="item">{formatIndex(selectedGamepadIndex)}</button>
                {/snippet}
                {#snippet overlay({close})}
                    {#each gamepadsOptions as option}
                        <button class="item" onclick={()=>{
                            selectedGamepadIndex = option
                            close()
                        }}>{formatIndex(option)}</button>
                    {/each}
                {/snippet}
            </Overlay>
        </section>
    {/if}

    <GamepadDrawing
            bind:writeGamepad={virtualGamepad}
            showGamepad={getCombinedGamepad(
      physicalGamepad,
      filterGamepadByTimestamp(externalGamepad, externalGamepadTimestamps, now)
    )}
    />

    <section class="overlay">
        {#if current != null && selectedGamepadIndex !== -1}
            <div>Physical</div>
        {/if}
        {#if combinedGamepad.l1}
            <div>L1</div>
        {/if}
        {#if combinedGamepad.l2}
            <div>L2</div>
        {/if}
        {#if combinedGamepad.r1}
            <div>R1</div>
        {/if}
        {#if combinedGamepad.r2}
            <div>R2</div>
        {/if}

        {#if combinedGamepad.dpad_left}
            <div>←</div>
        {/if}
        {#if combinedGamepad.dpad_right}
            <div>→</div>
        {/if}
        {#if combinedGamepad.dpad_up}
            <div>↑</div>
        {/if}
        {#if combinedGamepad.dpad_down}
            <div>↓</div>
        {/if}

        {#if combinedGamepad.circle}
            <div>●</div>
        {/if}
        {#if combinedGamepad.cross}
            <div>✚</div>
        {/if}
        {#if combinedGamepad.triangle}
            <div>▲</div>
        {/if}
        {#if combinedGamepad.square}
            <div>■</div>
        {/if}

        {#if combinedGamepad.share}
            <div>SHR</div>
        {/if}
        {#if combinedGamepad.options}
            <div>OPTS</div>
        {/if}
        {#if combinedGamepad.ps}
            <div>PS</div>
        {/if}
        {#if combinedGamepad.touchpad}
            <div>touchpad</div>
        {/if}

        {#if combinedGamepad.leftStick.value || Math.abs(combinedGamepad.leftStick.x) > 0.1 || Math.abs(combinedGamepad.leftStick.y) > 0.1}
            <div>L STICK</div>
        {/if}
        {#if combinedGamepad.rightStick.value || Math.abs(combinedGamepad.rightStick.x) > 0.1 || Math.abs(combinedGamepad.rightStick.y) > 0.1}
            <div>R STICK</div>
        {/if}
    </section>
</section>

<style>
    div {
        outline: 1px solid currentColor;
        padding: 0.5em 0.75em;
    }

    .overlay {
        position: absolute;
        left: var(--padding);
        bottom: var(--padding);
        display: flex;
        gap: var(--padding);
    }

    .top-overlay {
        position: absolute;
        left: var(--padding);
        top: var(--padding);
        z-index: 2;
    }

    .item {
        all: unset;
        cursor: pointer;
        padding: 0.25em 0.5em;
        border: 1px solid currentColor;
        border-radius: 0.25rem;
        text-wrap: nowrap;
    }

    .gamepad.first {
        --primary: var(--primaryBlue)
    }

    .gamepad.second {
        --primary: var(--primaryRed)
    }

    .gamepad {
        flex-grow: 1;
        min-width: 300px;
        position: relative;
    }
</style>
