import { PluginConfig } from "ftc-panels"

export const config: PluginConfig = {
  id: "com.bylazar.capture",
  name: "Capture",
  letterName: "C",
  description: "Capture Plugin for Panels",
  version: "0.1.0",
  panelsVersion: "0.0.1",
  pluginsCoreVersion: "0.0.1",
  author: "Lazar",
  widgets: [
    {
      name: "Capture",
      filepath: "src/widgets/Capture.svelte",
    },
  ],
  manager: {
    name: "Manager",
    filepath: "src/manager.ts",
  },
}
