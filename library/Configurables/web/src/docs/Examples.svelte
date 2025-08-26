<script lang="ts">
  import type { PluginInfo } from "ftc-panels"
  import { Title, Paragraph, CodeBlock } from "ftc-panels/docs"
  let {
    info,
  }: {
    info: PluginInfo
  } = $props()

  import javaClass from "@examples/configurables/TestJavaClass.java?raw"
  import kotlinClass from "@examples/configurables/TestKotlinClass.kt?raw"
  import kotlinObject from "@examples/configurables/TestKotlinObject.kt?raw"
</script>

<Title level={1}>Examples</Title>

<Title level={2}>Kotlin Object</Title>
<CodeBlock code={kotlinObject} language="kotlin"></CodeBlock>

<Title level={2}>Kotlin Class</Title>
<CodeBlock code={kotlinClass} language="kotlin"></CodeBlock>

<Title level={2}>Java Class</Title>
<CodeBlock code={javaClass} language="java"></CodeBlock>
