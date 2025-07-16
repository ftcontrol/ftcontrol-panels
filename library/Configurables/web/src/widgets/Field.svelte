<script lang="ts">
  import type Manager from "../manager"
  import { Types, type GenericTypeJson } from "../types"
  import Arrow from "./Arrow.svelte"
  import FieldHelper from "./FieldHelper.svelte"
  import OptionInput from "./OptionInput.svelte"
  import StringInput from "./StringInput.svelte"
  import UpdateIcon from "./UpdateIcon.svelte"
  import { anyValidator } from "./validators"
  import { Toggle } from "ftc-panels"

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
    <p style="margin-top: 0.75rem;">{item.fieldName}</p>
    <StringInput
      bind:value={item.value}
      bind:startValue={item.valueString}
      bind:newValue={item.newValueString}
      bind:isValid={item.isValid}
      type={item.type}
      validate={anyValidator(item.type)}
    />
    <button
      onclick={sendFieldUpdate}
      disabled={!item.isValid || item.valueString == item.newValueString}
    >
      <UpdateIcon />
    </button>
  </div>
{/if}
{#if item.possibleValues != undefined && [Types.BOOLEAN, Types.ENUM].includes(item.type)}
  <div style="--indent: {indent};">
    <p>{item.fieldName}</p>
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
    <button
      onclick={sendFieldUpdate}
      disabled={!item.isValid || item.valueString == item.newValueString}
    >
      <UpdateIcon />
    </button>
  </div>
{/if}

{#if item.customValues}
  {#if item.customValues.length}
    <Toggle>
      {#snippet trigger({ isOpen }: { isOpen: boolean })}
        <div style="--indent: {indent};">
          <p><Arrow {isOpen} /> {item.fieldName}</p>
        </div>
      {/snippet}
      {#snippet content({ close }: { close: () => void })}
        <div class="container">
          {#each item.customValues || [] as value}
            <FieldHelper item={value} indent={indent + 1} />
          {/each}
        </div>
      {/snippet}
    </Toggle>
  {/if}
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
