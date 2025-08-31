<script lang="ts">
  import type { PluginConfig } from "ftc-panels"
  import {
    Title,
    Image,
    Paragraph,
    OrderedList,
    ListItem,
  } from "ftc-panels/docs"

  let {
    info,
  }: {
    info: PluginConfig
  } = $props()

  import image from "./image.png"
</script>

<Title level={1}>What's FullPanels?</Title>

<Paragraph>
  This plugin conveniently bundles all the essential core plugins, simplifying
  installation and setup.
</Paragraph>

<Title level={1}>Templates</Title>

<Paragraph>
  It also includes core templates alongside the essential plugins, so you don’t
  have to start from scratch when designing your UI.
</Paragraph>

<Image src={image} alt={"Templates"} />
