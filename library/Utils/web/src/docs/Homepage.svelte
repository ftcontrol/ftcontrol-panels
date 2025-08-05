<script lang="ts">
  import type { PluginConfig } from "ftc-panels"
  import { Title, Paragraph, Accent, ListItem } from "ftc-panels/docs"
  let {
    info,
  }: {
    info: PluginConfig
  } = $props()
</script>

<Title>Utils</Title>
<Paragraph>Some utilities from the old FTControl release.</Paragraph>

<Title>Loop Timer</Title>
<Paragraph>Measures time intervals and calculates smoothed frequency (Hz).</Paragraph>

<Title>Constructor</Title>
<Paragraph><Accent>LoopTimer(smootherWindow: Int = 5)</Accent></Paragraph>
<ListItem><Accent>smootherWindow:</Accent> Window size for smoothing Hz values (default: 5).</ListItem>

<Title>Properties</Title>
<ListItem><Accent>ms</Accent> Last measured interval (ms)</ListItem>
<ListItem><Accent>hz</Accent> Smoothed frequency (Hz)</ListItem>
<ListItem><Accent>startTime</Accent> Timestamp of start() call</ListItem>
<ListItem><Accent>endTime</Accent> Timestamp of end() call</ListItem>

<Title>Methods</Title>
<ListItem><Accent>start()</Accent> Start timing.</ListItem>
<ListItem><Accent>end()</Accent> End timing, updates ms and hz.</ListItem>

<Title>Usage Example</Title>

<p>
<!--    val timer = LoopTimer()-->
<!--    timer.start()-->
<!--    // Simulate work-->
<!--    Thread.sleep(100)-->
<!--    timer.end()-->
<!--    println("Time: ${timer.ms}ms, Freq: ${timer.hz}Hz")-->
</p>

<Title>Moving Average</Title>
<Paragraph>Calculates a sliding-window average for smoothing data streams.</Paragraph>

<Title>Constructor</Title>
<Paragraph><Accent>MovingAverageSmoother(windowSize: Int)</Accent></Paragraph>
<ListItem><Accent>windowSize:</Accent> Maximum number of values to track in the moving window.</ListItem>

<Title>Methods</Title>
<ListItem><Accent>addValue(value: Double): Double</Accent> Adds a value to the window (evicts oldest if full). Returns the current average.</ListItem>
<ListItem><Accent>reset()</Accent> Clears all values and resets the internal sum.</ListItem>

<Title>Usage Example</Title>

<p>
<!--    val smoother = MovingAverageSmoother(3)-->
<!--    smoother.addValue(10.0) // 10.0-->
<!--    smoother.addValue(20.0) // 15.0-->
<!--    smoother.addValue(30.0) // 20.0-->
<!--    smoother.addValue(40.0) // 30.0 (oldest value 10.0 removed)-->
</p>
