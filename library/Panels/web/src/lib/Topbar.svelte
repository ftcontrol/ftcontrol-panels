<script>
  import Panels from "$lib/Panels.svelte"
</script>

<nav>
  <Panels />

  <button>Plugins</button>
</nav>

<style>
    nav{
        background-color: var(--bgMedium);
        padding: var(--padding);

        display: flex;
        justify-content: space-between;
        align-items: center;
    }
</style>