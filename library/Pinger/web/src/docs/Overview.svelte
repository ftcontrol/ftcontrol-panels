<script lang="ts">
  import type { PluginConfig } from "ftc-panels"
  import {Title, Paragraph, Image} from "ftc-panels/docs"
  let {
    info,
  }: {
    info: PluginConfig
  } = $props()

  import navlet from "./navlet.png"
</script>

<Title level={1}>Pinger Navlet</Title>

<Image src={navlet} alt={"Ping Navlet"}></Image>

<Paragraph> The Pinger navlet shows you the current ping time in ms of the websocket server.</Paragraph>