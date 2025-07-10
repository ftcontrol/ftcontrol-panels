<script lang="ts">
    import Manager from "./manager"

  let {
    manager,
  }: {
    manager: Manager
  } = $props()
</script>

<button
  onclick={() => {
    manager.socket.sendMessage("example", null)
  }}>Send message</button
>

<style>
  p {
    color: red;
  }
</style>
