<script lang="ts">
    import type { PluginConfig } from "ftc-panels"
    import {Title, Paragraph, Accent, ListItem, CodeBlock} from "ftc-panels/docs"
    let {
        info,
    }: {
        info: PluginConfig
    } = $props()

    import code from "@examples/utils/MovingAverageExample.kt?raw"
</script>

<Title level={1}>Moving Average</Title>
<Paragraph>Calculates a sliding-window average for smoothing data streams.</Paragraph>

<Title level={2}>Constructor</Title>
<Paragraph><Accent>MovingAverageSmoother(windowSize: Int)</Accent></Paragraph>
<ListItem><Accent>windowSize:</Accent> Maximum number of values to track in the moving window.</ListItem>

<Title level={2}>Methods</Title>
<ListItem><Accent>addValue(value: Double): Double</Accent> Adds a value to the window (evicts oldest if full). Returns the current average.</ListItem>
<ListItem><Accent>reset()</Accent> Clears all values and resets the internal sum.</ListItem>

<Title level={2}>Usage Example</Title>

<CodeBlock code={code} language={"kotlin"} />