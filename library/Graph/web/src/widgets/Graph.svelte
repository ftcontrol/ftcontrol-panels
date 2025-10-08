<script lang="ts">
  import { type PluginInfo } from "ftc-panels"
  import type Manager from "../manager.js"
  import { onMount } from "svelte"
  import { Chart } from "chart.js/auto"
  import { Button, Overlay } from "ftc-panels"

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

  let latest: ParsedVar[] | null = $state(null)
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

  function colorFor(name: string, s = 65, l = 55): string {
    let h = 2166136261 >>> 0
    for (let i = 0; i < name.length; i++) {
      h ^= name.charCodeAt(i)
      h = Math.imul(h, 16777619)
    }
    return `hsl(${h % 360}, ${s}%, ${l}%)`
  }

  function appendSnapshot(snapshot: ParsedVar[]) {
    const t = +nowSec().toFixed(3)
    for (const { name, value } of snapshot) {
      let arr = series.get(name)
      if (!arr) {
        arr = [{ x: 0, y: value }]
        series.set(name, arr)
        if (!allVarNames.includes(name)) allVarNames = [...allVarNames, name]
      }
      const last = arr[arr.length - 1]
      if (last && last.x === t) {
        last.y = value
      } else {
        arr.push({ x: t, y: value })
      }
    }
  }

  function trimToWindow() {
    const cutoff = nowSec() - timeWindowS
    for (const [, arr] of series) {
      let i = 0
      while (i < arr.length && arr[i].x < cutoff) i++
      if (i > 1) {
        arr.splice(0, i - 1)
      }
    }
  }

  function toDisplay(arr: Pt[]) {
    const now = nowSec()
    const Wsec = timeWindowS
    const out: Pt[] = []
    if (!arr.length) return out

    if (now < Wsec) {
      for (const p of arr) {
        if (p.x >= 0 && p.x <= now) out.push(p)
      }
    } else {
      const offs = now - Wsec
      for (const p of arr) {
        if (p.x >= offs && p.x <= now) out.push({ x: p.x - offs, y: p.y })
      }
      if (out.length === 0) {
        const last = arr[arr.length - 1]
        if (last) out.push({ x: Wsec, y: last.y })
      }
    }
    return out
  }

  function rebuildDatasets() {
    if (!chart) return
    const wanted = new Set(selectedVars)
    const current = new Set(
      chart.data.datasets.map((d: any) => d.label as string)
    )

    for (const name of selectedVars) {
      if (!current.has(name)) {
        const lineColor = colorFor(name)
        chart.data.datasets.push({
          label: name,
          data: [],
          parsing: false,
          borderColor: lineColor,
          backgroundColor: lineColor,
          tension: 0,
          cubicInterpolationMode: "default",
          spanGaps: true,
          pointRadius: 0,
          stepped: true,
        } as any)
      }
    }
    chart.data.datasets = chart.data.datasets.filter((d: any) =>
      wanted.has(d.label as string)
    )
  }

  function applyXAxisWindow() {
    if (!chart) return
    chart.options.scales!.x!.min = 0
    chart.options.scales!.x!.max = timeWindowS
  }

  let rafId: number | null = null
  let lastPaint = 0
  const PAINT_INTERVAL_MS = 1000 / 30

  function paintLoop(ts: number) {
    if (!chart) return
    if (ts - lastPaint >= PAINT_INTERVAL_MS) {
      applyXAxisWindow()
      for (const ds of chart.data.datasets as any[]) {
        const arr = series.get(ds.label as string) ?? []
        ds.data = toDisplay(arr)
      }
      chart.update("none")
      lastPaint = ts
    }
    rafId = requestAnimationFrame(paintLoop)
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
    for (const line of raw ?? []) {
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
    if (snapshot.length) latest = snapshot
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
      rafId = requestAnimationFrame(paintLoop)
    }

    return () => {
      stopPoll()
      if (rafId != null) cancelAnimationFrame(rafId)
      rafId = null
      chart?.destroy()
      chart = null
    }
  })

  $effect(() => {
    if (!latest) return
    appendSnapshot(latest)
    trimToWindow()
  })

  $effect(() => {
    selectedVars
    rebuildDatasets()
  })

  $effect(() => {
    timeWindowS
    trimToWindow()
    rebuildDatasets()
    applyXAxisWindow()
  })
</script>

<canvas bind:this={chartCanvas}></canvas>

<div class="controls">
  <div class="picker">
    <div class="label">Variables</div>
    <div class="buttons">
      <Button
        onclick={() => setAll(true)}
        disabled={selectedVars.length == allVarNames.length}>All</Button
      >
      <Button onclick={() => setAll(false)} disabled={selectedVars.length == 0}
        >None</Button
      >
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
    <Overlay>
      {#snippet trigger()}
        <button style="border: 1px solid currentColor">{timeWindowS}s</button>
      {/snippet}
      {#snippet overlay({ close }: { close: () => void })}
        {#each windowOptions.filter((it) => it.value != timeWindowS) as s}
          <button
            onclick={() => {
              timeWindowS = s.value
              close()
            }}>{s.label}</button
          >
        {/each}
      {/snippet}
    </Overlay>
    <div class="hint">Sampling every: {SAMPLE_MS} ms</div>
    <div class="hint">X-axis: 0s-{timeWindowS}s</div>
  </div>
</div>

<details style="margin-top:0.5rem;cursor:pointer;">
  <summary>Parsed variables (raw)</summary>
  <pre>{JSON.stringify(latest, null, 2)}</pre>
</details>

<br />

<style>
  button {
    all: unset;
    cursor: pointer;
    padding: 0.25em 1em;
  }

  .controls {
    display: flex;
    gap: var(--padding);
    align-items: flex-start;
    flex-wrap: wrap;
    margin-bottom: var(--padding);
  }

  .picker .buttons {
    display: flex;
    gap: 0.5rem;
    margin-bottom: 0.25rem;
  }
  .vars {
    display: grid;
    grid-template-columns: repeat(auto-fill, minmax(320px, 1fr));
    gap: 0.25rem;
    max-height: 220px;
    overflow-y: auto;
    overflow-x: hidden;
  }
  .var {
    display: flex;
    gap: 0.5rem;
    align-items: center;
  }
  .label {
    margin-bottom: 0.25rem;
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
