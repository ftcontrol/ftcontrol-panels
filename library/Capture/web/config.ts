import type { PluginConfig } from "ftc-panels"

export const config: PluginConfig = {
  id: "com.bylazar.capture",
  name: "Capture",
  letterName: "C",
  description: "Capture Plugin for Panels",
  websiteURL: "",
  version: "0.0.7",
  pluginsCoreVersion: "1.1.14",
  author: "Lazar",
  widgets: [
    {
      name: "Capture",
      filepath: "src/widgets/Capture.svelte",
    },
  ],
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
    chapters: [],
  },
  templates: [],
}
