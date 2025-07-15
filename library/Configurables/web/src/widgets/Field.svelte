<script lang="ts">
  import type Manager from "../manager"
  import { Types, type GenericTypeJson } from "../types"
  import FieldHelper from "./FieldHelper.svelte"
  import OptionInput from "./OptionInput.svelte"
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
{#if [Types.INT, Types.LONG, Types.DOUBLE, Types.FLOAT, Types.STRING].includes(item.type)}
  <div style="--indent: {indent};">
    <p>{item.fieldName}</p>
    <button onclick={sendFieldUpdate} disabled={!item.isValid}>Update</button>
    <StringInput
      bind:value={item.value}
      bind:startValue={item.valueString}
      bind:newValue={item.newValueString}
      bind:isValid={item.isValid}
      validate={anyValidator(item.type)}
    />
  </div>
{/if}
{#if item.possibleValues != undefined && [Types.BOOLEAN, Types.ENUM].includes(item.type)}
  <div style="--indent: {indent};">
    <p>{item.fieldName}</p>
    <button onclick={sendFieldUpdate} disabled={!item.isValid}>Update</button>
    <OptionInput
      bind:value={item.value}
      bind:startValue={item.valueString}
      bind:newValue={item.newValueString}
      bind:isValid={item.isValid}
      bind:possibleValues={item.possibleValues}
      validate={(value: string) => {
        if (item.possibleValues == null) return false
        return item.possibleValues.includes(value)
      }}
    />
  </div>
{/if}

{#if item.customValues}
  <p>{item.fieldName}</p>
  {#each item.customValues as value}
    <FieldHelper item={value} indent={indent + 1} />
  {/each}
{/if}

<style>
  p {
    margin: 0;
  }
  div {
    margin-left: calc(var(--indent) * 16px);
    display: flex;
  }
</style>
