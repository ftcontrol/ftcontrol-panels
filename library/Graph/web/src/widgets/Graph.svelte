<script lang="ts">
  import { type PluginInfo } from "ftc-panels"
  import type Manager from "../manager"
  import { onMount } from "svelte"
  import type { GraphEntry } from "../types"

  let {
    info,
    manager,
  }: {
    info: PluginInfo
    manager: Manager
  } = $props()

  let entries: GraphEntry[] = $state([])
  let timeWindow = $state(10)
  const colors = ["red", "lime", "cyan", "yellow", "magenta", "orange"]

  const selectedKeys = $state<Record<string, boolean>>({})

  onMount(() => {
    manager.state.onChange(manager.GRAPH_PACKET_KEY, (data: GraphEntry[]) => {
      entries = Array.isArray(data) ? data : []
      for (const id of new Set(entries.map((e) => e.id))) {
        if (selectedKeys[id] === undefined) selectedKeys[id] = false
      }
    })
  })

  function toggleKey(key: string) {
    selectedKeys[key] = !selectedKeys[key]
  }

  interface Point {
    x: number
    y: number
  }
  interface GraphPoint {
    timestamp: number
    value: number
  }

  function normalize(
    values: number[],
    min: number,
    max: number,
    range: [number, number] = [0, 1]
  ): number[] {
    if (values.length === 0) return []
    if (max === min) return values.map(() => (range[0] + range[1]) / 2)
    const [r0, r1] = range
    return values.map((v) => ((v - min) / (max - min)) * (r1 - r0) + r0)
  }

  function getWindowedEntries(entries: GraphEntry[]): GraphEntry[] {
    const now = Date.now()
    const start = now - timeWindow * 1000
    return entries.filter(
      (e) => typeof e.timestamp === "number" && e.timestamp >= start
    )
  }

  function getGroupedWindowed(
    entries: GraphEntry[]
  ): Record<string, GraphPoint[]> {
    const grouped: Record<string, GraphPoint[]> = {}
    for (const e of getWindowedEntries(entries)) {
      const v = typeof e.value === "number" ? e.value : parseFloat(e.value)
      if (!Number.isFinite(v)) continue
      if (!grouped[e.id]) grouped[e.id] = []
      grouped[e.id].push({ timestamp: e.timestamp, value: v })
    }
    for (const key of Object.keys(grouped)) {
      grouped[key].sort((a, b) => a.timestamp - b.timestamp)
    }
    return grouped
  }

  let grouped = $derived(getGroupedWindowed(entries))

  let active = $derived(
    Object.fromEntries(Object.entries(grouped).filter(([k]) => selectedKeys[k]))
  )

  function getSelectedValueRange(
    selectedGraphs: Record<string, GraphPoint[]>
  ): [number, number] {
    const values = Object.values(selectedGraphs)
      .flat()
      .map((p) => p.value)
    if (values.length === 0) return [0, 1]
    return [Math.min(...values), Math.max(...values)]
  }

  function getNormalizedGraphPoints(
    selectedGraphs: Record<string, GraphPoint[]>
  ): Record<string, Point[]> {
    const all = Object.values(selectedGraphs).flat()
    if (all.length === 0) return {}

    const now = Date.now()
    const timeStart = now - timeWindow * 1000
    const timeEnd = now

    const globalDataMin = Math.min(...all.map((p) => p.value))
    const globalDataMax = Math.max(...all.map((p) => p.value))

    const normalized: Record<string, Point[]> = {}
    for (const [key, list] of Object.entries(selectedGraphs)) {
      const xs = normalize(
        list.map((p) => p.timestamp),
        timeStart,
        timeEnd,
        [0, 100]
      )
      const ys = normalize(
        list.map((p) => p.value),
        globalDataMin,
        globalDataMax,
        [0, 100]
      )
      normalized[key] = list.map((_, i) => ({
        x: Math.min(100, Math.max(0, xs[i] ?? 0)),
        y: 100 - Math.min(100, Math.max(0, ys[i] ?? 50)),
      }))
    }
    return normalized
  }

  function getAllKeys(): string[] {
    return Array.from(new Set(entries.map((e) => e.id))).sort()
  }

  function getSeriesStats(id: string): {
    count: number
    lastValue: number | null
  } {
    const list = entries.filter((e) => e.id === id)
    const last = list[list.length - 1]
    const v = last
      ? typeof last.value === "number"
        ? last.value
        : parseFloat(last.value)
      : NaN
    return { count: list.length, lastValue: Number.isFinite(v) ? v : null }
  }
</script>

<div class="flex">
  <input
    type="range"
    min="1"
    max="60"
    bind:value={timeWindow}
    aria-label="Time window"
  />
  <p>(last {timeWindow}s)</p>
</div>

<ul>
  {#each getAllKeys() as key, i}
    {@const stats = getSeriesStats(key)}
    <li>
      <button
        onclick={() => toggleKey(key)}
        class:selected={selectedKeys[key]}
        aria-pressed={selectedKeys[key]}
      >
        {key}
      </button>
      – {stats.count} entries{#if stats.lastValue !== null}
        / {stats.lastValue.toFixed(3)}{/if}
    </li>
  {/each}
</ul>

<div class="graph">
  <svg viewBox="-10 -10 120 120" preserveAspectRatio="none">
    <!-- <rect x="0" y="0" width="100" height="100" fill="var(--bgLight)" /> -->

    {#each [0, 25, 50, 75, 100] as x}
      <text
        {x}
        y="-2"
        font-size="2.5"
        fill="white"
        stroke="currentColor"
        stroke-width="0.1"
        text-anchor="middle"
      >
        {Math.round((x / 100) * timeWindow)}s
      </text>
    {/each}

    {#if Object.keys(active).length > 0}
      {@const [min, max] = getSelectedValueRange(active)}
      {#each [0, 25, 50, 75, 100] as y}
        <text
          x="-2"
          y={100 - y}
          font-size="2.5"
          fill="white"
          stroke="currentColor"
          stroke-width="0.1"
          text-anchor="end"
        >
          {(min + (y / 100) * (max - min)).toFixed(2)}
        </text>
      {/each}
    {/if}

    {#each [25, 50, 75] as value}
      <line
        x1="0"
        y1={value}
        x2="100"
        y2={value}
        stroke="currentColor"
        stroke-width="0.2"
      />
      <line
        x1={value}
        y1="0"
        x2={value}
        y2="100"
        stroke="currentColor"
        stroke-width="0.2"
      />
    {/each}

    {#if Object.keys(active).length > 0}
      {@const pointsMap = getNormalizedGraphPoints(active)}
      {#each Object.entries(pointsMap) as [key, points], index}
        <polyline
          fill="none"
          stroke={colors[index % colors.length]}
          stroke-width="0.6"
          points={points.map((p) => `${p.x},${p.y}`).join(" ")}
        />
      {/each}
    {/if}
  </svg>
</div>

<style>
  p {
    margin: 0;
    margin-block: calc(var(--padding) / 2);
  }
  .flex {
    display: flex;
    align-items: center;
    gap: 0.5rem;
  }
  .graph {
    width: 100%;
    aspect-ratio: 1 / 1;
    position: relative;
    border: 1px solid currentColor;
    margin-top: var(--padding);
    background-color: var(--bgDark);
    margin-bottom: var(--padding);
  }
  ul {
    list-style: none;
    padding: 0;
    margin: 0.5rem 0;
  }
  li {
    display: flex;
    align-items: center;
    gap: 0.5rem;
    margin: 0.25rem 0;
  }
  button {
    background: transparent;
    border: 1px solid currentColor;
    color: inherit;
    border-radius: 0.25rem;
    padding: 0.2rem 0.5rem;
    cursor: pointer;
    font-family: monospace;
    display: inline-flex;
    align-items: center;
    gap: 0.25rem;
    opacity: 0.5;
  }
  button.selected {
    opacity: 1;
  }
</style>
