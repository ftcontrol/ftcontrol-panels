<script lang="ts">
    import type { PluginConfig } from "ftc-panels"
    import { Title, Paragraph, Accent, ListItem, Table, CodeBlock } from "ftc-panels/docs"
    let {
        info,
    }: {
        info: PluginConfig
    } = $props()

    import code from "@examples/utils/LoopTimerExample.kt?raw"
</script>

<Title level={1}>Loop Timer</Title>
<Paragraph>Measures time intervals and calculates smoothed frequency (Hz).</Paragraph>

<Title level={2}>Constructor</Title>
<Paragraph><Accent>LoopTimer(smootherWindow: Int = 5)</Accent></Paragraph>
<ListItem><Accent>smootherWindow:</Accent> Window size for smoothing Hz values (default: 5).</ListItem>

<Title level={2}>Properties</Title>
<Table data={
[
    ["Name", "Type", "Description"],
    ["ms", "Long", "Last measured interval (ms)\n"],
    ["hz", "Double", "Smoothed frequency (Hz)"],
    ["startTime", "Long", "Timestamp of start() call\n"],
    ["endTime", "Long", "Timestamp of end() call\n"],
]
}
hasHeader={true}
}></Table>

<Title level={2}>Methods</Title>
<ListItem><Accent>start()</Accent> Start timing.</ListItem>
<ListItem><Accent>end()</Accent> End timing, updates ms and hz.</ListItem>

<Title level={2}>Usage Example</Title>

<CodeBlock code={code} language={"kotlin"} />