<script lang="ts">
  import type { PluginConfig } from "ftc-panels"
  import { Title, Paragraph, AutoplayVideo, CodeBlock } from "ftc-panels/docs"
  let {
    info,
  }: {
    info: PluginConfig
  } = $props()

  import drawBezier from "@examples/field/BezierFollower.kt?raw"
  import drawing from "@examples/field/Drawing.kt?raw"
  import video from "./video.mp4"
</script>

<Title level={1}>Field</Title>
<AutoplayVideo src={video} alt={"Field Showcase"} />
<Paragraph>
  A drawable canvas of the game field, render any shape or path with mapped
  coordinates and support for popular pathing libraries.
</Paragraph>

<Title level={1}>Guide</Title>
<Paragraph>An example class for drawing.</Paragraph>

<CodeBlock language={"kotlin"} code={drawing} />

<Title level={1}>Example Bezier Drawing</Title>
<Paragraph>The OpMode used for the example clip</Paragraph>

<CodeBlock language={"kotlin"} code={drawBezier} />
