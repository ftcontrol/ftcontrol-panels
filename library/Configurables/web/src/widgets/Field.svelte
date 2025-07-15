<script lang="ts">
  import type Manager from "../manager"
  import { Types, type GenericTypeJson } from "../types"
  import FieldHelper from "./FieldHelper.svelte"
  import StringInput from "./StringInput.svelte"
  import { anyValidator } from "./validators"

  import { getContext } from "svelte"
  const manager = getContext("manager") as Manager

  let { item, indent = 0 }: { item: GenericTypeJson; indent?: number } =
    $props()

  function sendFieldUpdate() {
    if (!item.isValid) return
    manager.socket.sendMessage("updatedConfigurable", [
      {
        id: item.id,
        newValueString: item.newValueString,
      },
    ])

    // item.valueString = item.newValueString
    // item.value = item.newValueString
  }
</script>

<!-- <p style="--indent: {indent};">{JSON.stringify(item)}</p> -->
<div style="--indent: {indent};">
  <p>{item.fieldName}</p>
  {#if [Types.INT, Types.LONG, Types.DOUBLE, Types.FLOAT, Types.STRING].includes(item.type)}
    <button onclick={sendFieldUpdate} disabled={!item.isValid}>Update</button>
    <StringInput
      bind:value={item.value}
      bind:startValue={item.valueString}
      bind:newValue={item.newValueString}
      bind:isValid={item.isValid}
      validate={anyValidator(item.type)}
    />
  {/if}
</div>
{#if item.customValues}
  {#each item.customValues as value}
    <FieldHelper item={value} indent={indent + 1} />
  {/each}
{/if}

<style>
  div {
    margin-left: calc(var(--indent) * 16px);
  }
</style>
