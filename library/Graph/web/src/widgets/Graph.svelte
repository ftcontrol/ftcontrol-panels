<script lang="ts">
  import { type PluginInfo } from "ftc-panels"
  import type Manager from "../manager.js"
  import { onMount } from "svelte"
  import { Chart } from "chart.js/auto"

  let { info, manager }: { info: PluginInfo; manager: Manager } = $props()

  type ParsedVar = { name: string; value: number }
  type Pt = { x: number; y: number }

  const RE =
    /\s*([^:]+?)\s*:\s*([+-]?(?:\d+(?:\.\d+)?|\.\d+)(?:[eE][+-]?\d+)?)/g
  let timeWindowS = $state(15)

  const TARGET_SAMPLES = 1000
  let SAMPLE_MS = $derived(
    Math.max(10, Math.floor((timeWindowS * 1000) / TARGET_SAMPLES))
  )

  let vars: ParsedVar[][] = $state([])
  let chartCanvas: HTMLCanvasElement
  let chart: Chart | null = null

  const series = new Map<string, Pt[]>()
  const windowOptions = [
    { label: "1s", value: 1 },
    { label: "5s", value: 5 },
    { label: "15s", value: 15 },
    { label: "30s", value: 30 },
    { label: "1m", value: 60 },
    { label: "5m", value: 300 },
    { label: "10m", value: 600 },
  ]

  let allVarNames = $state<string[]>([])
  let selectedVars = $state<string[]>([])

  let t0: number | null = null
  const nowSec = () => {
    if (t0 == null) t0 = performance.now()
    return (performance.now() - t0) / 1000
  }

  const colorFor = (name: string) => {
    let h = 0
    for (let i = 0; i < name.length; i++)
      h = (h * 31 + name.charCodeAt(i)) >>> 0
    return `hsl(${h % 360},70%,50%)`
  }

  function appendSnapshot(snapshot: ParsedVar[]) {
    const t = +nowSec().toFixed(3)
    for (const { name, value } of snapshot) {
      if (!series.has(name)) {
        series.set(name, [{ x: 0, y: value }])
        if (!allVarNames.includes(name)) allVarNames = [...allVarNames, name]
      }
      series.get(name)!.push({ x: t, y: value })
    }
  }

  function trimToWindow() {
    const cutoff = nowSec() - timeWindowS
    for (const [, arr] of series)
      while (arr.length && arr[0].x < cutoff) arr.shift()
  }

  function toDisplay(arr: Pt[]) {
    const now = nowSec()
    const Wsec = timeWindowS
    const out: Pt[] = []

    if (now < Wsec) {
      for (const p of arr) {
        if (p.x < 0 || p.x > now) continue
        out.push({ x: p.x, y: p.y })
      }
    } else {
      const offs = now - Wsec
      for (const p of arr) {
        if (p.x < offs || p.x > now) continue
        out.push({ x: p.x - offs, y: p.y })
      }
    }
    return out
  }

  function rebuildDatasets() {
    if (!chart) return
    chart.data.datasets = selectedVars.map((name) => ({
      label: name,
      data: toDisplay(series.get(name) ?? [{ x: 0, y: 0 }]),
      parsing: false,
      borderColor: colorFor(name),
      backgroundColor: colorFor(name),
      tension: 0,
      cubicInterpolationMode: "default",
      spanGaps: true,
      pointRadius: 0,
    })) as any
  }

  function applyXAxisWindow() {
    if (!chart) return
    chart.options.scales!.x!.min = 0
    chart.options.scales!.x!.max = timeWindowS
  }

  function requestChartUpdate() {
    if (!chart) return
    applyXAxisWindow()
    chart.update("none")
  }

  function setAll(on: boolean) {
    selectedVars = on ? [...allVarNames] : []
  }

  function poll() {
    const mgr =
      manager?.socket?.socket?.pluginManagers?.["com.bylazar.telemetry"]
    const raw = mgr?.state?.get?.("packets") as string[] | undefined
    if (!Array.isArray(raw)) return

    const snapshot: ParsedVar[] = []
    for (const line of raw) {
      if (typeof line !== "string") continue
      RE.lastIndex = 0
      let m: RegExpExecArray | null
      while ((m = RE.exec(line)) !== null) {
        const name = m[1].trim()
        const value = Number(m[2])
        if (!Number.isFinite(value)) continue
        snapshot.push({ name, value })
      }
    }
    if (snapshot.length) vars.push(snapshot)
  }

  let pollTimer: number | null = null

  function startPoll(ms: number) {
    stopPoll()
    pollTimer = window.setInterval(poll, ms)
  }

  function stopPoll() {
    if (pollTimer != null) {
      clearInterval(pollTimer)
      pollTimer = null
    }
  }

  $effect(() => {
    const ms = SAMPLE_MS
    startPoll(ms)
    return () => stopPoll()
  })

  onMount(() => {
    const ctx = chartCanvas.getContext("2d")
    if (ctx) {
      chart = new Chart(ctx, {
        type: "line",
        data: { datasets: [] },
        options: {
          animation: false,
          responsive: true,
          normalized: true,
          interaction: { mode: "nearest", intersect: false },
          plugins: {
            legend: { position: "bottom" },
            tooltip: { enabled: true },
          },
          scales: {
            x: {
              type: "linear",
              bounds: "ticks",
              title: { display: true, text: `Time (s) in window` },
              ticks: {
                autoSkip: false,
                callback: (v) => `${Number(v).toFixed(2)}s`,
              },
              min: 0,
              max: timeWindowS,
            },
            y: { beginAtZero: false },
          },
          elements: { point: { radius: 0 }, line: { borderWidth: 2 } },
        },
      })
      applyXAxisWindow()
    }

    return () => {
      stopPoll()
      chart?.destroy()
      chart = null
    }
  })

  $effect(() => {
    const latest = vars[vars.length - 1]
    if (!latest || !chart) return
    appendSnapshot(latest)
    trimToWindow()
    rebuildDatasets()
    requestChartUpdate()
  })

  $effect(() => {
    selectedVars
    rebuildDatasets()
    requestChartUpdate()
  })

  $effect(() => {
    timeWindowS
    trimToWindow()
    rebuildDatasets()
    requestChartUpdate()
  })
</script>

<div class="controls">
  <div class="picker">
    <div class="label">Variables</div>
    <div class="buttons">
      <button type="button" onclick={() => setAll(true)}>All</button>
      <button type="button" onclick={() => setAll(false)}>None</button>
    </div>
    <div class="vars">
      {#each allVarNames as name (name)}
        <label class="var">
          <input type="checkbox" value={name} bind:group={selectedVars} />
          <span class="swatch" style={`background:${colorFor(name)}`}></span>
          <span>{name}</span>
        </label>
      {/each}
    </div>
  </div>

  <div class="window">
    <div class="label">Time window</div>
    <select bind:value={timeWindowS}>
      {#each windowOptions as o}
        <option value={o.value}>{o.label}</option>
      {/each}
    </select>
    <div class="hint">Sampling {SAMPLE_MS} ms</div>
    <div class="hint">X-axis fixed: 0 → {timeWindowS}s</div>
  </div>
</div>

<details style="margin-top:0.5rem;">
  <summary>Parsed variables (raw)</summary>
  <pre>{JSON.stringify(vars[vars.length - 1], null, 2)}</pre>
</details>

<canvas bind:this={chartCanvas} id="myChart"></canvas>

<style>
  .controls {
    display: flex;
    gap: 1rem;
    align-items: flex-start;
    flex-wrap: wrap;
    margin-bottom: 0.75rem;
  }
  .label {
    font-weight: 600;
    margin-bottom: 0.25rem;
  }
  .picker .buttons {
    display: flex;
    gap: 0.5rem;
    margin-bottom: 0.25rem;
  }
  .vars {
    display: grid;
    grid-template-columns: repeat(auto-fill, minmax(220px, 1fr));
    gap: 0.25rem;
    max-height: 220px;
    overflow: auto;
    padding: 0.25rem;
    border: 1px solid #ddd;
    border-radius: 4px;
  }
  .var {
    display: flex;
    gap: 0.5rem;
    align-items: center;
  }
  .swatch {
    width: 10px;
    height: 10px;
    border-radius: 2px;
    display: inline-block;
  }
  .hint {
    font-size: 0.85rem;
    opacity: 0.7;
    margin-top: 0.25rem;
  }
</style>
