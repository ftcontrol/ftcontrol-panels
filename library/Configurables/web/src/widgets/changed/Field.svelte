<script lang="ts">
  import { FieldWithValues, Types } from "../../types"
  import FieldHelper from "./FieldHelper.svelte"
  import Arrow from "../Arrow.svelte"
  import Copy from "./Copy.svelte"
  import TypesBox from "./TypesBox.svelte"

  let { item, indent = 0 }: { item: FieldWithValues; indent?: number } =
    $props()

  let wasCopied = $state(false)
</script>

{#if item.customValues && item.customValues.length}
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
{:else}
  <div style="--indent: {indent};">
    <p>{item.fieldName}</p>
    <TypesBox value={item.currentValue} type={item.initialValue} />
    <button
      onclick={() => {
        if (wasCopied) return
        wasCopied = true
        navigator.clipboard.writeText(item.currentValue)
        setTimeout(() => {
          wasCopied = false
        }, 400)
      }}
      disabled={wasCopied}
    >
      <Copy />
    </button>
  </div>
{/if}

<style>
  button {
    all: unset;
  }
  button:disabled {
    opacity: 0.5;
  }
  p {
    margin: 0;
    margin-top: 0.25rem;
  }
  div {
    margin-left: calc(var(--indent) * 16px + 24px);
    margin-top: 0.5rem;
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
