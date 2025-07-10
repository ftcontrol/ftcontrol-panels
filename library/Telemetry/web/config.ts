import { PluginConfig } from "ftc-panels"

export const config: PluginConfig = {
  id: "com.bylazar.telemetry",
  name: "Telemetry",
  letterName: "T",
  description: "Text Telemetry",
  version: "0.1.0",
  panelsVersion: "0.0.1",
  pluginsCoreVersion: "0.0.1",
  author: "Lazar",
  widgets: [
    {
      name: "Telemetry",
      filepath: "src/widgets/Telemetry.svelte",
    },
  ],
  manager: {
    name: "Manager",
    filepath: "src/manager.ts",
  },
}
