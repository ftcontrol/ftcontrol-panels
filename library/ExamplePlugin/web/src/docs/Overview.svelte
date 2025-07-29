<script lang="ts">
  import type { PluginConfig } from "ftc-panels"
  import { Title, Paragraph } from "ftc-panels/docs"
  let {
    info,
  }: {
    info: PluginConfig
  } = $props()
</script>

<Title>{info.name}</Title>
<Paragraph>{info.description}</Paragraph>
