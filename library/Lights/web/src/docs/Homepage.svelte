<script lang="ts">
  import type { PluginConfig } from "ftc-panels"
  import {
    Title,
    Paragraph,
    CodeBlock,
    InlineCode,
    Image,
  } from "ftc-panels/docs"
  let {
    info,
  }: {
    info: PluginConfig
  } = $props()

  import example from "@examples/lights/TestLights.kt?raw"
  import lights from "./lights.png"
</script>

<Title level={1}>Introduction</Title>

<Paragraph>
  Mimics goBILDA-style RGB indicators to provide fast, glanceable feedback
  during development. Comes with a headlight that is a <InlineCode
    >Boolean</InlineCode
  > and with an RGB Indicator that is a <InlineCode>Double PWM range</InlineCode
  >. You can either use manual input, or one of the provided color presets.
</Paragraph>

<Image src={lights} alt={"Lights Widget"} />

<Title level={1}>Example Usage</Title>
<CodeBlock language={"kotlin"} code={example} />
