<script lang="ts">
  import type { GenericTypeJson } from "../types"
  import Field from "./Field.svelte"

  let { item, indent = 0 }: { item: GenericTypeJson; indent: number } = $props()
</script>

<Field {item} {indent} />
