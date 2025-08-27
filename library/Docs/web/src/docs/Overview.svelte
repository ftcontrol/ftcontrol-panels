<script lang="ts">
  import type { PluginInfo } from "ftc-panels"
  import {
    Title,
    Paragraph,
    Accent,
    Link,
    ListItem,
    AccentedParagraph,
  } from "ftc-panels/docs"
  let {
    info,
  }: {
    info: PluginInfo
  } = $props()
</script>

<AccentedParagraph tone="danger">
  This is just the version of the Docs Plugin, for full install of Panels please
  read the entire Prerequisites section.
</AccentedParagraph>

<Title>FTControl Panels</Title>
<Paragraph>
  <Accent>FTControl Panels</Accent> is an all-in-one dashboard for your FTC robot,
  developed by <Link href="https://bylazar.com">Lazar</Link> from team
  <Link href="https://byteforce.ro">19234 ByteForce</Link>. It provides a
  powerful set of features with minimal boilerplate, using <Accent
    >Kotlin</Accent
  > for cleaner syntax (also works in <Accent>Java</Accent>).
</Paragraph>

<Title>Features</Title>

<Paragraph>
  <Accent>Panels</Accent> is a powerful dashboard for your FTC robot that includes:
</Paragraph>

<ListItem>
  <Accent>OpMode control</Accent> - Just like the Driver Hub.
</ListItem>
<ListItem>
  <Accent>Real-time telemetry</Accent> - See all your robot data live.
</ListItem>
<ListItem>
  <Accent>Field view</Accent> - A canvas-like interface for drawing on the field.
</ListItem>
<ListItem>
  <Accent>Graph view</Accent> - A graphing tool for tuning and debugging.
</ListItem>
<ListItem>
  <Accent>Capture mode</Accent> - Record and replay matches for debugging.
</ListItem>
<ListItem>
  <Accent>Configurables</Accent> - Tune your robot in real time without uploading
  code again.
</ListItem>
<ListItem>
  <Accent>Limelight Support</Accent> - Use your Limelight 3A without USB connection.
</ListItem>
<ListItem>
  <Accent>Plugins</Accent> - Extend Panels using custom plugins.
</ListItem>

<Paragraph>Stay tuned for more features as Panels evolves!</Paragraph>
