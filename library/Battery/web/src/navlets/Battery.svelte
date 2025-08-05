<script lang="ts">
    import { onMount } from "svelte"
    import type Manager from "../manager"

    const isDev = false

    let { manager }: { manager: Manager } = $props()

    let battery = $state(-1)

    // Normalize battery level between 0 and 1
    function getBatteryLevel(voltage: number): number {
        const min = 4.0
        const max = 15.0
        return Math.max(0, Math.min(1, (voltage - min) / (max - min)))
    }

    let batteryLevel = $derived(getBatteryLevel(battery))
    let batteryClass = $derived(battery < 10.0 ? "low" : battery < 12.0 ? "medium" : "high")

    onMount(() => {
        manager.state.onChange(manager.BATTERY_KEY, (data: number) => {
            battery = data
        })

        if(!isDev) return
        battery = 14

        const drain = setInterval(() => {
            battery -= 0.2
            if (battery <= 4.0) battery = 14.0
        }, 200)

        return () => clearInterval(drain)
    })
</script>

<p>
    <span class="battery-container">
    <span
            class="battery-fill"
            class:low={batteryClass === "low"}
            class:medium={batteryClass === "medium"}
            class:high={batteryClass === "high"}
            style="width: {batteryLevel * 100}%"
    />
    <span
            class="battery-empty"
            style="width: {100 - batteryLevel * 100}%"
    />
    <span class="extra"></span>
    <span class="battery-text">{battery.toFixed(2)}V</span>
  </span>
</p>

<style>
    p {
        white-space: nowrap;
        margin: 0;
    }

    .battery-container {
        display: inline-block;
        padding: 4px 8px;
        border-radius: 4px;
        position: relative;
    }

    .battery-fill,
    .battery-empty {
        position: absolute;
        top: 0;
        height: 100%;
        transition: width 0.3s ease;
    }

    .battery-fill.low {
        background-color: red;
    }

    .battery-fill.medium {
        background-color: orange;
    }

    .battery-fill.high {
        background-color: green;
    }

    .battery-fill {
        left: 0;
        z-index: 1;
    }

    .battery-empty {
        right: 0;
        background-color: var(--bgLight);
        z-index: 0;
    }
    .extra {
        position: absolute;
        right: -6px;
        top: 50%;
        transform: translateY(-50%);
        background-color: var(--bgLight);
        z-index: 0;
        width: 6px;
        height: 16px;
    }
    .battery-text {
        position: relative;
        z-index: 2;
        padding-left: 6px;
        font-weight: bold;
    }
</style>
