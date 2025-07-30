<script lang="ts">
  import Layout from "$lib/grid/Layout.svelte"
  import { Manager, manager } from "$lib/grid/widgets.svelte"

  const m = new Manager()
</script>

<Layout bind:manager={manager.manager} enableInteractions={true} />
<!-- <Layout
  bind:manager={manager.manager}
  enableInteractions={false}
  defaultTemplate={{
    selected: 0,
    data: [
      {
        name: "Test",
        widgets: [],
        navlets: [],
      },
    ],
  }}
/> -->
