<script lang="ts">
  import type { PluginConfig } from "ftc-panels"
  import {Title, Paragraph, Video} from "ftc-panels/docs"
  let {
    info,
  }: {
    info: PluginConfig
  } = $props()
  import control from "./control.mp4"
</script>

<Title level={1}>OpMode Control</Title>

<Video src={control} controls={false} alt={"OpMode Control Showcase"}></Video>

<Paragraph>
    OpMode Control replicates driver station functionality, with separate Autonomous and TeleOp selectors.
</Paragraph>
