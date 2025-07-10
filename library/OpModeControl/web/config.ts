import { PluginConfig } from "ftc-panels/src/core/types"

export const config: PluginConfig = {
  id: "com.bylazar.opmodecontrol",
  name: "My Panels Plugin",
  description: "Starter plugin template",
  version: "0.1.0",
  panelsVersion: "0.0.1",
  author: "Unknown",
  widgets: [
    {
      name: "Counter",
      filepath: "src/widgets/Counter.svelte",
    },
    {
      name: "OpModes",
      filepath: "src/widgets/OpModes.svelte",
    },
    {
      name: "Counter2",
      filepath: "src/Counter.svelte",
    },
  ],
  manager: {
    name: "Manager",
    filepath: "src/manager.ts",
  },
}
