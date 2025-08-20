import { PluginManager } from "ftc-panels"
import type { GraphEntry } from "./types"

export default class Manager extends PluginManager {
  GRAPH_PACKET_KEY = "graphPacket"

  private readonly perIdMax = 150
  private readonly overallMax: number | null = null

  override onInit(): void {
    this.state.update(this.GRAPH_PACKET_KEY, [])

    this.socket.addMessageHandler(
      this.GRAPH_PACKET_KEY,
      (data: GraphEntry[]) => {
        this.state.mutate(this.GRAPH_PACKET_KEY, (current: GraphEntry[]) => {
          const merged = current.concat(data)

          const now = Date.now()
          const cutoff = now - 60_000
          const recent = merged.filter((e) => e.timestamp >= cutoff)

          recent.sort((a, b) => a.timestamp - b.timestamp)

          const perIdSampled = downsamplePerId(recent, this.perIdMax)

          const finalEntries = this.overallMax
            ? proportionalGlobalCap(perIdSampled, this.overallMax)
            : perIdSampled

          finalEntries.sort((a, b) => a.timestamp - b.timestamp)
          return finalEntries
        })
      }
    )
  }

  static async getNewVersion(): Promise<string> {
    return ""
  }
}

function downsamplePerId(
  entries: GraphEntry[],
  perIdMax: number
): GraphEntry[] {
  const byId = new Map<string, GraphEntry[]>()
  for (const e of entries) {
    const arr = byId.get(e.id)
    if (arr) arr.push(e)
    else byId.set(e.id, [e])
  }

  const out: GraphEntry[] = []
  for (const [id, group] of byId) {
    group.sort((a, b) => a.timestamp - b.timestamp)
    if (group.length <= perIdMax) {
      out.push(...group)
      continue
    }
    out.push(...uniformPickNearest(group, perIdMax))
  }
  return out
}

function uniformPickNearest(
  group: GraphEntry[],
  maxCount: number
): GraphEntry[] {
  const n = group.length
  if (n <= maxCount) return group.slice()

  const t0 = group[0].timestamp
  const t1 = group[n - 1].timestamp
  if (t1 <= t0) return group.slice(-maxCount)

  const step = (t1 - t0) / (maxCount - 1)
  const result: GraphEntry[] = []

  let j = 0
  const used = new Set<number>()
  for (let i = 0; i < maxCount; i++) {
    const targetT = Math.round(t0 + i * step)

    while (j < n - 1 && group[j].timestamp < targetT) j++

    const cand: number[] = []
    if (j > 0) cand.push(j - 1)
    cand.push(j)

    let bestIdx = cand[0]
    let bestDist = Math.abs(group[bestIdx].timestamp - targetT)
    for (let k = 1; k < cand.length; k++) {
      const idx = cand[k]
      const dist = Math.abs(group[idx].timestamp - targetT)
      if (
        dist < bestDist ||
        (dist === bestDist && group[idx].timestamp < group[bestIdx].timestamp)
      ) {
        bestIdx = idx
        bestDist = dist
      }
    }
    if (used.has(bestIdx)) {
      let left = bestIdx - 1
      let right = bestIdx + 1
      while (left >= 0 || right < n) {
        let pick = -1
        const leftDist =
          left >= 0 ? Math.abs(group[left].timestamp - targetT) : Infinity
        const rightDist =
          right < n ? Math.abs(group[right].timestamp - targetT) : Infinity
        if (leftDist <= rightDist && left >= 0 && !used.has(left)) pick = left
        else if (right < n && !used.has(right)) pick = right

        if (pick !== -1) {
          bestIdx = pick
          break
        }
        if (left >= 0) left--
        if (right < n) right++
      }
    }

    used.add(bestIdx)
    result.push(group[bestIdx])
  }

  result.sort((a, b) => a.timestamp - b.timestamp)
  return result
}

function proportionalGlobalCap(
  entries: GraphEntry[],
  overallMax: number
): GraphEntry[] {
  if (entries.length <= overallMax) return entries

  const byId = new Map<string, GraphEntry[]>()
  for (const e of entries) {
    const arr = byId.get(e.id)
    if (arr) arr.push(e)
    else byId.set(e.id, [e])
  }

  const ids = Array.from(byId.keys())
  const counts = ids.map((id) => byId.get(id)!.length)
  const total = counts.reduce((a, b) => a + b, 0)

  let quotas = counts.map((c) =>
    Math.max(1, Math.floor((c / total) * overallMax))
  )

  let diff = overallMax - quotas.reduce((a, b) => a + b, 0)
  const fracs = counts.map((c, i) => (c / total) * overallMax - quotas[i])
  const order = fracs
    .map((f, i) => ({ i, f }))
    .sort((a, b) => b.f - a.f)
    .map((o) => o.i)

  let k = 0
  while (diff !== 0) {
    const idx = order[k % order.length]
    const newQuota = quotas[idx] + Math.sign(diff)
    quotas[idx] = Math.max(1, Math.min(newQuota, counts[idx]))
    diff -= Math.sign(diff)
    k++
  }

  const out: GraphEntry[] = []
  for (let m = 0; m < ids.length; m++) {
    const id = ids[m]
    const group = byId
      .get(id)!
      .slice()
      .sort((a, b) => a.timestamp - b.timestamp)
    const q = quotas[m]
    out.push(...uniformPickNearest(group, q))
  }

  out.sort((a, b) => a.timestamp - b.timestamp)
  return out
}
