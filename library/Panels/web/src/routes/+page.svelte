<script lang="ts">
  import Layout from "$lib/grid/Layout.svelte"
  import { manager } from "$lib/grid/widgets.svelte"
</script>

<Layout bind:manager={manager.manager} />
