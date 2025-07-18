<script lang="ts">
  import { FieldWithValues } from "../../types"
  import Field from "./Field.svelte"

  let { item, indent = 0 }: { item: FieldWithValues; indent: number } = $props()
</script>

<Field {item} {indent} />
