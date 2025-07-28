<script lang="ts">
  import type { PluginInfo } from "ftc-panels"
  import { Title, Paragraph } from "ftc-panels/docs"
  import type Manager from "../manager"
  let {
    info,
    manager,
  }: {
    info: PluginInfo
    manager: Manager
  } = $props()
</script>

<Title>{info.details.name}</Title>
<Paragraph>{info.details.description}</Paragraph>
