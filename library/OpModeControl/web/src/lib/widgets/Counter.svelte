<script lang="ts">
  import { increment } from ".."
  let count: number = $state(0)
</script>

<p>{count}</p>
<button
  onclick={() => {
    count = increment(count)
  }}>Increment</button
>

<style>
  p {
    color: red;
  }
</style>
