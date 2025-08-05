<script lang="ts">
    import type { PluginConfig } from "ftc-panels"
    import { Title, Paragraph } from "ftc-panels/docs"
    let {
        info,
    }: {
        info: PluginConfig
    } = $props()
</script>

<Title>Resetting</Title>

<Paragraph>
    This plugin uses local storage to save data. When breaking changes are made to the internal data structure, it's recommended to delete the record named 'theme' from local storage to avoid issues.
</Paragraph>
