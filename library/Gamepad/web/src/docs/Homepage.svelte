<script lang="ts">
  import type { PluginConfig } from "ftc-panels"
  import { Title, Paragraph, CodeBlock, AutoplayVideo } from "ftc-panels/docs"
  let {
    info,
  }: {
    info: PluginConfig
  } = $props()

  import opmode from "@examples/gamepad/TestGamepad.kt?raw"
  import video from "./video.mp4"
</script>

<Title level={1}>Introduction</Title>

<AutoplayVideo
  src={video}
  alt={"Driving a robot with Limelight Proxy and Gamepad"}
/>

<Paragraph>
  Panels supports up to two FTC gamepads, giving Driver 1 and Driver 2 full
  wireless control of the robot. Both controllers are mapped and ready to use
  right out of the box.
</Paragraph>

<Title level={1}>Example Usage</Title>
<CodeBlock language={"kotlin"} code={opmode} />
