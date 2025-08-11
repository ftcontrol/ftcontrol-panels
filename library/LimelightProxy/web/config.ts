import { PluginConfig } from "ftc-panels"

export const config: PluginConfig = {
  id: "com.bylazar.limelightproxy",
  name: "Limelight Proxy",
  letterName: "LLP",
  description: "Limelight Proxy for Panels",
  websiteURL: "",
  version: "0.0.8",
  pluginsCoreVersion: "0.0.1",
  author: "Lazar",
  widgets: [
    {
      name: "CameraStream",
      filepath: "src/widgets/CameraStream.svelte",
    },
    {
      name: "Dashboard",
      filepath: "src/widgets/Dashboard.svelte",
    },
    {
      name: "Stats",
      filepath: "src/widgets/Stats.svelte",
    },
  ],
  navlets: [
    {
      name: "Temperature",
      filepath: "src/navlets/Temperature.svelte",
    },
  ],
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
        name: "DocsPage1",
        filepath: "src/docs/DocsPage1.svelte",
      },
    ],
  },
  templates: [],
}
