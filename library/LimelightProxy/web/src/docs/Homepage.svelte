<script lang="ts">
  import type { PluginConfig } from "ftc-panels"
  import { Title, Paragraph, Image } from "ftc-panels/docs"
  let {
    info,
  }: {
    info: PluginConfig
  } = $props()

  import dash from "./dash.png"
</script>

<Title level={1}>Introduction</Title>
<Paragraph>
  Panels gives you full access to the Limelight 3A dashboard, with built-in
  widgets for streaming video, tracking camera stats, and logging telemetry,
  keeping your vision system seamlessly integrated with the rest of your
  dashboard.
</Paragraph>

<Image src={dash} alt={"Limelight Dashboard"} />

<Title level={1}>Accessing the Limelight Dashboard</Title>

<Paragraph>
  You can either use the provided widgets from the Plugin Details page, or go to
  port :5801.
</Paragraph>
