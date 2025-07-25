import { PluginConfig } from "ftc-panels"

export const config: PluginConfig = {
  id: "com.bylazar.gamepad",
  name: "Gamepad",
  letterName: "G",
  description: "Gamepad Plugin for Panels",
  version: "0.1.0",
  panelsVersion: "0.0.1",
  pluginsCoreVersion: "0.0.1",
  author: "Lazar",
  widgets: [
    {
      name: "Gamepad",
      filepath: "src/widgets/Gamepad.svelte",
    },
  ],
  manager: {
    name: "Manager",
    filepath: "src/manager.ts",
  },
}
