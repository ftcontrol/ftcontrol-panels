<script lang="ts">
  import { Button } from "ftc-panels"

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
