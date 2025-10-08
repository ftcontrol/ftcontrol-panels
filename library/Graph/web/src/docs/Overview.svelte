<script lang="ts">
  import type { PluginConfig } from "ftc-panels"
  import { Title, Paragraph, AutoplayVideo, CodeBlock } from "ftc-panels/docs"
  let {
    info,
  }: {
    info: PluginConfig
  } = $props()
  import video from "./video.mp4"
  import code from "@examples/graph/TestGraph.kt?raw"
</script>

<Title level={1}>Graph</Title>
<AutoplayVideo src={video} alt={"Graph Showcase"} />

<Paragraph>
  Visualize live data over time. Perfect for tuning PIDs and validating changes
  at a glance.
</Paragraph>

<Paragraph>Requires Telemetry plugin.</Paragraph>

<Title level={1}>Example Usage</Title>

<CodeBlock language={"kotlin"} {code} />
