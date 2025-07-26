<script lang="ts">
  import type { PluginConfig } from "ftc-panels"
  import { Title, Paragraph } from "ftc-panels/docs"
  let {
    config,
  }: {
    config: PluginConfig
  } = $props()
</script>

<Title>{config.name}</Title>
<Paragraph>{config.description}</Paragraph>
