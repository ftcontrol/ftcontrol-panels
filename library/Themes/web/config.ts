import { PluginConfig } from "ftc-panels"

export const config: PluginConfig = {
  id: "com.bylazar.themes",
  name: "Themes",
  letterName: "T",
  description: "Plugin for Panels that implements custom theming with saving and preset sharing.",
  websiteURL: "",
  version: "0.0.6",
  pluginsCoreVersion: "0.0.1",
  author: "Lazar",
  widgets: [
    {
      name: "Theme Manager",
      filepath: "src/widgets/Color.svelte",
    },
  ],
  navlets: [
    {
      name: "Colors Preview",
      filepath: "src/navlets/Preview.svelte",
    }
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
        name: "Theming Widget",
        filepath: "src/docs/ThemingWidget.svelte",
      },
      {
        name: "Colors Navlet",
        filepath: "src/docs/ColorsNavlet.svelte",
      },
      {
        name: "Other",
        filepath: "src/docs/Other.svelte",
      },
    ],
  },
  templates: [],
}
