<script lang="ts">
  import { Overlay } from "ftc-panels"

  let {
    startValue = $bindable(),
    newValue = $bindable(),
    value = $bindable(),
    isValid = $bindable(),
    possibleValues = $bindable(),
    validate = (value: string) => true,
  }: {
    startValue: string
    newValue: string
    value: string
    isValid: Boolean
    possibleValues: string[]
    validate: (value: string) => boolean
  } = $props()

  $effect(() => {
    if (value != "") return
    value = startValue
    newValue = startValue
    isValid = validate(startValue)
  })

  $effect(() => {
    isValid = validate(value)
    if (value == newValue) return
    if (!isValid) return
    newValue = value
  })
</script>

{JSON.stringify({
  value,
  startValue,
  newValue,
  isValid,
  possibleValues,
})}

<Overlay>
  {#snippet trigger()}
    <button>{value}</button>
  {/snippet}
  {#snippet overlay({ close }: { close: () => void })}
    {#each possibleValues.filter((it) => it != value) as v}
      <button
        onclick={() => {
          value = v
          close()
        }}>{v}</button
      >
    {/each}
  {/snippet}
</Overlay>

<style>
  input.invalid {
    opacity: 0.5;
  }
</style>
