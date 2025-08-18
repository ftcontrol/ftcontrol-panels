<script lang="ts">
  import type { PluginConfig } from "ftc-panels"
  import {
    Title,
    Paragraph,
    CodeBlock,
    InlineCode,
    UnorderedList,
    ListItem,
  } from "ftc-panels/docs"

  let {
    info,
  }: {
    info: PluginConfig
  } = $props()
  import code from "@examples/configurables/copysemantics/lambda/Example.kt?raw"
  import javaRobotCOnstants from "@examples/configurables/JavaRobotConstants.java?raw"
  import kotlinRobotCOnstants from "@examples/configurables/KotlinRobotConstants.kt?raw"
</script>

<Title level={1}>{info.name}</Title>

<Paragraph>
  Configurables are runtime-modifiable variables that make testing, tuning, and
  debugging robot behavior easier without needing to recompile or reupload your
  code. You can think of them as live sliders or inputs exposed to your Panels
  Dashboard.
</Paragraph>

TODO video

<Title level={2}>What is a Configurable?</Title>

<Paragraph>
  A Configurable is a <InlineCode>public static (Java)</InlineCode> or
  <InlineCode>@JvmField @JvmStatic (Kotlin)</InlineCode> variable marked in a class
  annotated with <InlineCode>@Configurable</InlineCode>. These variables are
  exposed in the Panels UI and can be changed while the robot is running.
</Paragraph>

<Paragraph>Useful for things like:</Paragraph>
<UnorderedList>
  <ListItem>PID tuning</ListItem>
  <ListItem>Autonomous positions and paths</ListItem>
  <ListItem>Behavioral toggles (e.g., enabling/disabling subsystems)</ListItem>
  <ListItem>Testing new constants on-the-fly</ListItem>
</UnorderedList>

<Title level={2}>Annotating Classes with @Configurable</Title>

<Paragraph>To make a class’s fields configurable:</Paragraph>
<CodeBlock code={javaRobotCOnstants} lang="java"></CodeBlock>
<Paragraph>In Kotlin:</Paragraph>
<CodeBlock code={kotlinRobotCOnstants} lang="kotlin"></CodeBlock>

<CodeBlock {code}></CodeBlock>
