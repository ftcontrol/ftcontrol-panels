import { PluginConfig } from "ftc-panels"

export const config: PluginConfig = {
  id: "com.bylazar.notifier",
  name: "Notifier Plugin",
  letterName: "EP",
  description: "Release Notifier for Panels",
  version: "0.1.0",
  panelsVersion: "0.0.1",
  pluginsCoreVersion: "0.0.1",
  author: "Lazar",
  widgets: [],
  navlets: [
    {
      name: "Version Checker",
      filepath: "src/navlets/Version.ts",
    },
  ],
  manager: {
    name: "Manager",
    filepath: "src/manager.ts",
  },
  docs: {
    homepage: {
      name: "Overview",
      filepath: "src/docs/Overview.svelte",
    },
    chapters: [],
  },
}
