<script lang="ts">
  import type { PluginConfig } from "ftc-panels"
  import { Title, Paragraph } from "ftc-panels/docs"
  let {
    config,
  }: {
    config: PluginConfig
  } = $props()
</script>

<Title>Example</Title>
<Paragraph>This is an example page.</Paragraph>
