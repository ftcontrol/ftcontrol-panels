<script lang="ts">
  import type { PluginConfig } from "ftc-panels"
  import { Title, Paragraph, Video } from "ftc-panels/docs"

  let {
    info,
  }: {
    info: PluginConfig
  } = $props()

  import drain from "./drain.mp4"
</script>


<Title>Battery Navlet</Title>

<Video src={drain} controls={false} alt={"Battery Navlet"}></Video>

<Paragraph>
    This shows the current battery voltage, providing a quick color coded visual reference.
</Paragraph>