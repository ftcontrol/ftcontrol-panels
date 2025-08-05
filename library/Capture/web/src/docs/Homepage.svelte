<script lang="ts">
  import type { PluginConfig } from "ftc-panels"
  import {Title, Paragraph, Accent, Video, Image, ListItem} from "ftc-panels/docs"
  let {
    info,
  }: {
    info: PluginConfig
  } = $props()

  import capture from "./capture.mp4"
  import image from "./image.png"
</script>

<Title>Capture Mode</Title>
<Paragraph>It is a <Accent>powerful tool</Accent> designed to record and replay matches for debugging purposes. This feature allows users to analyze robot performance, identify issues, and optimize strategies by reviewing recorded match data. It supports Telemetry, Graph, Field Recordings and any other Plugin that respects its protocol.</Paragraph>

<Video src={capture} alt={"Capture Function"}></Video>

<Title>Controls</Title>

<Image src={image} alt={"Capture Interface"}></Image>

<ListItem>
    <Accent>Start:</Accent> Begins capturing data points and telemetry information during the match.
</ListItem>
<ListItem>
    <Accent>Stop:</Accent> Stops the data capturing process. This button should be clicked once the desired events have been recorded.
</ListItem>
<ListItem>
    <Accent>Reset:</Accent> Clears all previously captured data, resetting the capture session for a new recording.
</ListItem>
<ListItem>
    <Accent>Enable Visual Sync:</Accent> Synchronizes the visual field display and the telemetry data, ensuring that the replay accurately reflects the robot's movements and interactions.
</ListItem>
<ListItem>
    <Accent>Disable Visual Sync:</Accent> Disables the synchronization between the visual field and telemetry data. Returns to viewing realtime data.
</ListItem>
<ListItem>
    <Accent>Start Playing:</Accent> Initiates the playback of the recorded data, allowing users to review and analyze the match.
</ListItem>
<ListItem>
    <Accent>Stop Playing:</Accent> Pauses the playback at any point, enabling detailed inspection of specific moments within the recorded data.
</ListItem>
<ListItem>
    <Accent>Animation Speed Control:</Accent> Adjusts the playback speed of the recorded match. Useful for slowing down fast-paced actions or speeding through less critical phases.
</ListItem>