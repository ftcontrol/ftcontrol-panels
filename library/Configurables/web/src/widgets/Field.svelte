<script lang="ts">
  import { type GenericTypeJson } from "../types"
  import FieldHelper from "./FieldHelper.svelte"

  let { item, indent = 0 }: { item: GenericTypeJson; indent?: number } =
    $props()
</script>

<p style="--indent: {indent};">{JSON.stringify(item)}</p>
<p style="--indent: {indent};">{item.fieldName} {item.valueString}</p>
{#if item.customValues}
  {#each item.customValues as value}
    <FieldHelper item={value} indent={indent + 1} />
  {/each}
{/if}

<style>
  p {
    margin-left: calc(var(--indent) * 16px);
  }
</style>
