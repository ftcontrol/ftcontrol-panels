<script lang="ts">
  import { ExtendedType } from "../../types"
  import Field from "./Field.svelte"

  let { item, indent = 0 }: { item: ExtendedType; indent: number } = $props()
</script>

<Field {item} {indent} />
