import { PluginConfig } from "ftc-panels/src/core/types"

export const config: PluginConfig = {
  id: "com.bylazar.opmodecontrol",
  name: "OpMode Control",
  letterName: "OC",
  description: "OpMode Control for FTC Robots",
  version: "0.1.0",
  panelsVersion: "0.0.1",
  author: "Lazar",
  widgets: [
    {
      name: "Counter",
      filepath: "src/widgets/Counter.svelte",
    },
    {
      name: "OpModes Control",
      filepath: "src/control/ControlWidget.svelte",
    },
    {
      name: "Send",
      filepath: "src/Counter.svelte",
    },
  ],
  manager: {
    name: "Manager",
    filepath: "src/manager.ts",
  },
}
