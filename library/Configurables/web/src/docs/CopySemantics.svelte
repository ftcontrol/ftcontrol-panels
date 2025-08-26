<script lang="ts">
  import type { PluginInfo } from "ftc-panels"
  import { Title, Paragraph, CodeBlock } from "ftc-panels/docs"
  let {
    info,
  }: {
    info: PluginInfo
  } = $props()

  import bad from "@examples/configurables/copysemantics/bad/Example.kt?raw"
  import fixed from "@examples/configurables/copysemantics/fixed/Example.kt?raw"
  import lambda from "@examples/configurables/copysemantics/lambda/Example.kt?raw"
</script>

<Title level={1}>The Common Pitfall: Copy Semantics</Title>
<Paragraph
  >In Kotlin and Java, when you assign an object to a new variable, you copy the
  reference to the object, not the object itself. This means both variables
  point to the same memory location. However, when dealing with primitives (like
  int, double, etc.) or immutable objects, the value is copied directly. This
  can lead to unexpected behavior if one variable is modified, as the other
  variable won't reflect the change.</Paragraph
>

<Title level={1}>Example: Implementing a configurable Claw System</Title>
<Title level={2}>Bad Example</Title>
<CodeBlock code={bad} language="kotlin"></CodeBlock>
<Paragraph>
  In this example, the offset variable is passed as a value to the RobotClaw
  constructor during initialization. Since Double is a primitive type, its value
  is copied into the RobotClaw object. If offset is changed later during the
  OpMode (e.g., via Panels interface), the RobotClaw instance will not reflect
  the updated value. This leads to inconsistent behavior because the RobotClaw
  continues to use the old offset value.
</Paragraph>

<Title level={2}>Solution 1: Use a Mutable Shared State</Title>
<Paragraph>
  One way to solve this issue is to make the clawOffset a mutable shared state
  that can be updated dynamically. Instead of passing the value directly, we
  store it in a shared location (e.g., a companion object) and reference it
  within the RobotClaw.
</Paragraph>
<CodeBlock code={fixed} language="kotlin"></CodeBlock>

<Title level={2}>Solution 2: Use a Lambda Function</Title>
<Paragraph>
  Another elegant solution is to pass a lambda function that retrieves the
  current value of offset. This ensures that the RobotClaw always queries the
  latest value of offset whenever it needs it.
</Paragraph>
<CodeBlock code={lambda} language="kotlin"></CodeBlock>
