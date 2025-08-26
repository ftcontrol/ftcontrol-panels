<script lang="ts">
  import type { PluginConfig } from "ftc-panels"
  import { Title, Paragraph, CodeBlock } from "ftc-panels/docs"
  let {
    info,
  }: {
    info: PluginConfig
  } = $props()

  import code from "@examples/telemetry/TestTelemetry.kt?raw"
</script>

<Title level={1}>Telemetry</Title>
<Paragraph>
  Text-based telemetry that can mirror Driver Hub telemetry, so data stays
  consistent across your dashboard and the field.
</Paragraph>

<Title level={1}>Example Usage</Title>

<CodeBlock language={"kotlin"} {code} />
