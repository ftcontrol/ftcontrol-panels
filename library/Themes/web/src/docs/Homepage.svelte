<script lang="ts">
  import type { PluginConfig } from "ftc-panels"
  import { Title, Paragraph, Image, Accent, Video } from "ftc-panels/docs"

  let {
    info,
  }: {
    info: PluginConfig
  } = $props()

  import widget from "./widget.mp4"
  import navlet from "./navlet.png"

</script>

<Title>The Theming Widget</Title>

<Video src={widget} alt="Theming Widget Showcase" />

<Title>Accessing</Title>

<Paragraph>
    The <Accent>theming widget</Accent> allows you to change themes. You can access it by adding it to a widget group in your Panels Preset, or through the plugin settings where all widgets are shown.
</Paragraph>

<Title>Functions</Title>

<Paragraph>
    This widget lets you change the current theme, share a JSON representation of your theme with others, and import themes shared by other users.
</Paragraph>


<Title>Color Navlet</Title>

<Image src={navlet} alt="Color Navlet" />

<Paragraph>
    This topbar tool displays the currently active colors in your theme, providing a quick visual reference.
</Paragraph>

<Title>Resetting</Title>

<Paragraph>
    This plugin uses local storage to save data. When breaking changes are made to the internal data structure, it's recommended to delete the record named 'theme' from local storage to avoid issues.
</Paragraph>
