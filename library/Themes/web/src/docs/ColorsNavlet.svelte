<script lang="ts">
    import type { PluginConfig } from "ftc-panels"
    import { Title, Paragraph, Image } from "ftc-panels/docs"
    let {
        info,
    }: {
        info: PluginConfig
    } = $props()
    import navlet from "./navlet.png"
</script>

<Title>Color Navlet</Title>

<Image src={navlet} alt="Color Navlet" />

<Paragraph>
    This topbar tool displays the currently active colors in your theme, providing a quick visual reference.
</Paragraph>