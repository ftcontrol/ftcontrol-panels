<script lang="ts">
    import type {PluginConfig} from "ftc-panels"
    import {Title, Paragraph, AutoplayVideo, Accent, CodeBlock} from "ftc-panels/docs"

    let {
        info,
    }: {
        info: PluginConfig
    } = $props()

    import drain from "./drain.mp4"
    import code from "@examples/battery/BatteryExample.kt?raw"
</script>

<Title level={2}>Battery Navlet</Title>

<AutoplayVideo src={drain} alt={"Battery Navlet"}></AutoplayVideo>

<Paragraph>
    This shows the current <Accent>battery voltage</Accent>, providing a quick color coded visual reference.
</Paragraph>

<Title level={2}>Battery Reading</Title>

<Paragraph>
    You can also read the current voltage for other battery related tasks.
</Paragraph>

<CodeBlock code={code}></CodeBlock>

