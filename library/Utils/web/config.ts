import type { PluginConfig } from "ftc-panels"

export const config: PluginConfig = {
  id: "com.bylazar.utils",
  name: "Utils",
  letterName: "U",
  description: "Utils for Panels",
  websiteURL: "https://panels.bylazar.com/docs/com.bylazar.utils/",
  version: "0.0.10",
  pluginsCoreVersion: "1.1.26",
  author: "Lazar",
  manager: "src/manager.ts",
  components: [
    {
      type: "docs",
      id: "Homepage",
      filepath: "src/docs/Homepage.svelte",
    },
    {
      type: "docs",
      id: "Loop Timer",
      filepath: "src/docs/LoopTimer.svelte",
    },
    {
      type: "docs",
      id: "Moving Average",
      filepath: "src/docs/MovingAverage.svelte",
    },
  ],
  templates: [],
  includedPluginsIDs: [],
}
