import { PluginConfig } from "ftc-panels"

export const config: PluginConfig = {
  id: "com.bylazar.configurables",
  name: "Configurables",
  letterName: "C",
  description: "Configurable variables for Panels",
  version: "0.1.0",
  panelsVersion: "0.0.1",
  pluginsCoreVersion: "0.0.1",
  author: "Lazar",
  widgets: [
    {
      name: "Configurables",
      filepath: "src/widgets/Configurables.svelte",
    },
  ],
  manager: {
    name: "Manager",
    filepath: "src/manager.ts",
  },
}
