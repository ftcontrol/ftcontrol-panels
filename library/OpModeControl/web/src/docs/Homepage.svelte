<script lang="ts">
  import type { PluginConfig } from "ftc-panels"
  import { Title, Paragraph, Video, AutoplayVideo } from "ftc-panels/docs"
  let {
    info,
  }: {
    info: PluginConfig
  } = $props()
  import control from "./control.mp4"
  import timer from "./timer.mp4"
</script>

<Title level={1}>OpMode Control</Title>

<AutoplayVideo src={control} alt={"OpMode Control Showcase"}></AutoplayVideo>

<Paragraph>
  OpMode Control replicates driver station functionality, with separate
  Autonomous and TeleOp selectors.
</Paragraph>

<Title level={1}>OpMode Timer</Title>

<AutoplayVideo src={timer} alt={"OpMode Timer Showcase"}></AutoplayVideo>

<Paragraph>
  You can use the timer to automatically stop your autonomous.
</Paragraph>
