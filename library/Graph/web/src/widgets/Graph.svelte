<script lang="ts">
  import { Button, type PluginInfo } from "ftc-panels"
  import type Manager from "../manager"

  let {
    info,
    manager,
  }: {
    info: PluginInfo
    manager: Manager
  } = $props()

  function increment(n: number) {
    return n + 1
  }

  let count: number = $state(0)
</script>

<p>{count}</p>
<Button
  onclick={() => {
    count = increment(count)
  }}
>
  Increment
</Button>

<style>
  p {
    color: red;
  }
</style>
