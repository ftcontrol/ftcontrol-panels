<script lang="ts">
  import type { PluginConfig } from "ftc-panels"
  import { Title, Paragraph, Accent, ListItem } from "ftc-panels/docs"
  let {
    info,
  }: {
    info: PluginConfig
  } = $props()
</script>

<Title level={1}>Utils</Title>
<Paragraph>Some utilities from the old FTControl version.</Paragraph>