import { PluginConfig } from "ftc-panels"

export const config: PluginConfig = {
  id: "com.bylazar.exampleplugin",
  name: "Example Plugin",
  letterName: "EP",
  description: "Example Plugin for Panels",
  websiteURL: "",
  devURL: "",
  version: "0.1.0",
  pluginsCoreVersion: "0.0.1",
  author: "Lazar",
  widgets: [
    {
      name: "Counter",
      filepath: "src/widgets/Counter.svelte",
    },
  ],
  navlets: [],
  manager: {
    name: "Manager",
    filepath: "src/manager.ts",
  },
  docs: {
    homepage: {
      name: "Overview",
      filepath: "src/docs/Overview.svelte",
    },
    chapters: [
      {
        name: "DocsPage1",
        filepath: "src/docs/DocsPage1.svelte",
      },
    ],
  },
}
