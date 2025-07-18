<script lang="ts">
  let {
    startValue = $bindable(),
    newValue = $bindable(),
    value = $bindable(),
    isValid = $bindable(),
    type = "",
    validate = (value: string) => true,
  }: {
    startValue: string
    newValue: string
    value: string
    isValid: Boolean
    type?: string
    validate: (value: string) => boolean
  } = $props()

  import { onMount } from "svelte"

  $effect(() => {
    isValid = validate(value)
    if (value == newValue) return
    if (!isValid) return
    newValue = value
  })
</script>

<!-- {JSON.stringify({
  value,
  startValue,
  newValue,
  isValid,
})} -->
<div class="container">
  <input
    type="text"
    class:invalid={!isValid}
    placeholder={startValue}
    bind:value
  />
  {#if type != ""}
    <div class="text">
      <div class="bg"></div>
      <span class="base">{type}</span>
      <span class="shown">{type}</span>
    </div>
  {/if}
</div>

<style>
  div.container {
    position: relative;
    border: 1px solid currentColor;
    border-radius: 0.25rem;
    margin-top: 0.5rem;
    max-width: 120px;
  }

  input {
    all: unset;
    border: none;
    padding: 0.25em;
    position: relative;
    color: inherit;
    background-color: transparent;
  }
  .text {
    text-transform: uppercase;
    position: absolute;
    top: -45%;
    left: 0.5rem;
    color: inherit;
  }
  .bg {
    background-color: var(--bgMedium);
    position: absolute;
    width: 100%;
    top: 55%;
    height: 15%;
    left: 0;
  }
  span {
    padding-inline: 0.15rem;
  }
  .base {
    opacity: 0;
  }
  .shown {
    position: absolute;
    left: 0;
    top: 0;
  }

  input.invalid {
    opacity: 0.5;
  }
</style>
