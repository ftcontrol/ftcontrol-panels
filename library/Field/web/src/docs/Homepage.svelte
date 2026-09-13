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
  import field from "@examples/field/CustomPresets.kt?raw"
  import video from "./video.mp4"
</script>

<Title level={1}>Field</Title>
<AutoplayVideo src={video} alt={"Field Showcase"} />
<Paragraph>
  A drawable canvas of the game field, render any shape or path with mapped
  coordinates and support for popular pathing libraries.
</Paragraph>

<Title level={1}>Field Images</Title>
<Paragraph>
  BIOBUZZ field images are available through <code>PanelsField.images.BIOBUZZ</code>.
  Images created by Team Juice 16236 and shared in the
  <a
    href="https://www.reddit.com/r/FTC/comments/1weleaj/biobuzz_custom_field_images_meepmeep_compatible/"
  >original FTC community post</a>.
</Paragraph>

<Title level={1}>Guide</Title>
<Paragraph>An example class for drawing.</Paragraph>

<CodeBlock language={"kotlin"} code={drawing} />

<Paragraph>An example config for custom Field presets.</Paragraph>

<CodeBlock language={"kotlin"} code={field} />

<Title level={1}>Example Bezier Drawing</Title>
<Paragraph>The OpMode used for the example clip</Paragraph>

<CodeBlock language={"kotlin"} code={drawBezier} />
