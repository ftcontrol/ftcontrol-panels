<script lang="ts">
  import { FieldWithValues, Types } from "../../types"
  import FieldHelper from "./FieldHelper.svelte"
  import Arrow from "../Arrow.svelte"

  let { item, indent = 0 }: { item: FieldWithValues; indent?: number } =
    $props()
</script>

{#if [Types.INT, Types.LONG, Types.DOUBLE, Types.FLOAT, Types.STRING].includes(item.type)}
  <div style="--indent: {indent};">
    <p style="margin-top: 0.75rem;">
      {item.fieldName}
      {item.initialValue}
      {item.currentValue}
    </p>
  </div>
{/if}
{#if item.possibleValues != undefined && [Types.BOOLEAN, Types.ENUM].includes(item.type)}
  <div style="--indent: {indent};">
    <p>{item.fieldName} {item.initialValue} {item.currentValue}</p>
  </div>
{/if}

{#if item.customValues}
  {#if item.customValues.length}
    <div style="--indent: {indent};">
      <p><Arrow isOpen={true} /> {item.fieldName}</p>
    </div>
    <div class="container">
      {#if item.customValues}
        {#each item.customValues as value}
          <FieldHelper item={value} indent={indent + 1} />
        {/each}
      {/if}
    </div>
  {/if}
{/if}

<style>
  p {
    margin: 0;
    margin-top: 0.25rem;
  }
  div {
    margin-left: calc(var(--indent) * 16px + 24px);
    gap: 0.5rem;
    display: grid;
    grid-template-columns: 1fr 1fr 32px;
  }
  .container {
    display: flex;
    flex-direction: column;
    gap: var(--padding);
  }
</style>
