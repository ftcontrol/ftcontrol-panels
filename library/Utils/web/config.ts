import type { PluginConfig } from "ftc-panels"

export const config: PluginConfig = {
  id: "com.bylazar.utils",
  name: "Utils",
  letterName: "U",
  description: "Utils for Panels",
  websiteURL: "",
  version: "0.0.8",
  pluginsCoreVersion: "1.1.18",
  author: "Lazar",
  widgets: [],
  navlets: [],
  manager: {
    name: "Manager",
    filepath: "src/manager.ts",
  },
  docs: {
    homepage: {
      name: "Homepage",
      filepath: "src/docs/Homepage.svelte",
    },
    chapters: [
      {
        name: "Loop Timer",
        filepath: "src/docs/LoopTimer.svelte",
      },
      {
        name: "Moving Average",
        filepath: "src/docs/MovingAverage.svelte",
      },
    ],
  },
  templates: [],
}
