<script lang="ts">
  import type { PluginInfo } from "ftc-panels"
  import { Title, Paragraph, Accent, Link } from "ftc-panels/docs"
  let {
    info,
  }: {
    info: PluginInfo
  } = $props()
</script>

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

<Paragraph>Stay tuned for more features as Panels evolves!</Paragraph>
