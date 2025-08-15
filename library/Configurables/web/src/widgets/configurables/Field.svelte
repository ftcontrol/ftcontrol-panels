<script lang="ts">
  import { Types, type ExtendedType, type GenericTypeJson } from "../../types"
  import FieldHelper from "./FieldHelper.svelte"
  import OptionInput from "./OptionInput.svelte"
  import StringInput from "./StringInput.svelte"
  import UpdateIcon from "./UpdateIcon.svelte"
  import { Toggle } from "ftc-panels"
  import { anyValidator } from "../validators"
  import Arrow from "../Arrow.svelte"

  import {getContext, tick} from "svelte"
  import type Manager from "../../manager.ts";
  const manager = getContext("manager") as Manager

  let { item, indent = 0 }: { item: ExtendedType; indent?: number } = $props()

  async function sendFieldUpdate() {
    if (!item.isValid) return

    manager.socket.sendMessage("updatedConfigurable", [
      {
        id: item.id,
        newValueString: item.newValueString,
      },
    ])
  }

  function isSameValue(value1: any, value2: any) {
    if (value1 === value2) return true;

    const num1 = Number(value1);
    const num2 = Number(value2);

    if (!Number.isNaN(num1) && !Number.isNaN(num2) && num1 === num2) {
      return true;
    }

    return String(value1) === String(value2);
  }
</script>
{#if [Types.UNKNOWN, Types.RECURSION_REACHED, Types.GENERIC_NO_ANNOTATION, Types.ERROR].includes(item.type)}
  <div style="--indent: {indent};">
    <p>{item.fieldName}</p>
    <p>{item.type}</p>
    <span></span>
  </div>
{/if}
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
      submit={sendFieldUpdate}
    />
    <button
      onclick={sendFieldUpdate}
      disabled={!item.isValid || isSameValue(item.valueString,item.newValueString)}
    >
      <UpdateIcon />
    </button>
  </div>
{/if}
{#if item.possibleValues !== undefined && [Types.BOOLEAN, Types.ENUM].includes(item.type)}
  <div style="--indent: {indent};">
    <p>{item.fieldName}</p>
    <OptionInput
      bind:value={item.value}
      bind:startValue={item.valueString}
      bind:newValue={item.newValueString}
      bind:isValid={item.isValid}
      bind:possibleValues={item.possibleValues}
      validate={(value) => {
        if (item.possibleValues == null) return false
        return item.possibleValues.includes(value)
      }}
    />
    <button
      onclick={sendFieldUpdate}
      disabled={!item.isValid || isSameValue(item.valueString,item.newValueString)}
    >
      <UpdateIcon />
    </button>
  </div>
{/if}

{#if item.customValues}
  {#if item.customValues.length}
    <Toggle>
      {#snippet trigger({ isOpen })}
        <div style="--indent: {indent};">
          <p><Arrow {isOpen} /> {item.fieldName}</p>
        </div>
      {/snippet}
      {#snippet content({ close })}
        <div class="container">
          {#if item.customValues}
            {#each item.customValues as value}
              <FieldHelper item={value} indent={indent + 1} />
            {/each}
          {/if}
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
