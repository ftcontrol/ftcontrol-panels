<script lang="ts">
    import type { PluginConfig } from "ftc-panels"
    import { Title, Paragraph, Image, Accent } from "ftc-panels/docs"
    let {
        info,
    }: {
        info: PluginConfig
    } = $props()

    import widget from "./widget.png"
</script>

<Title>The Theming Widget</Title>

<Image src={widget} alt="Theming Widget" />

<Title>Accessing</Title>

<Paragraph>
    The <Accent>theming widget</Accent> allows you to change themes. You can access it by adding it to a widget group in your Panels Preset, or through the plugin settings where all widgets are shown.
</Paragraph>

<Title>Functions</Title>

<Paragraph>
    This widget lets you change the current theme, share a JSON representation of your theme with others, and import themes shared by other users.
</Paragraph>