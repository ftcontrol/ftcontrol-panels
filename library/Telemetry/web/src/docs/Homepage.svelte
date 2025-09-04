<script lang="ts">
  import type { PluginConfig } from "ftc-panels"
  import { Title, Paragraph, CodeBlock } from "ftc-panels/docs"
  let {
    info,
  }: {
    info: PluginConfig
  } = $props()

  import panelsStyle from "@examples/telemetry/TestTelemetryPanelsStyle.kt?raw"
  import ftcStyle from "@examples/telemetry/TestTelemetryFTCStyle.kt?raw"
  import joined from "@examples/telemetry/TestJoinedTelemetry.kt?raw"
</script>

<Title level={1}>Telemetry</Title>
<Paragraph>
  Text-based telemetry that can mirror Driver Hub telemetry, so data stays
  consistent across your dashboard and the field.
</Paragraph>

<Title level={1}>Example Usage</Title>
<Title level={2}>Panels Style</Title>
<Paragraph>The Panels telemetry interface.</Paragraph>

<CodeBlock language={"kotlin"} code={panelsStyle} />

<Title level={2}>FTC Style</Title>
<Paragraph>The FTC telemetry interface wrapper.</Paragraph>

<CodeBlock language={"kotlin"} code={ftcStyle} />

<Title level={2}>Joined Wrapper</Title>
<Paragraph>A class for joining multiple telemetries.</Paragraph>

<CodeBlock language={"kotlin"} code={joined} />
