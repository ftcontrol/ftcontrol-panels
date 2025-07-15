<script lang="ts">
  let {
    startValue = $bindable(),
    newValue = $bindable(),
    value = $bindable(),
    isValid = $bindable(),
    validate = (value: string) => true,
  }: {
    startValue: string
    newValue: string
    value: string
    isValid: Boolean
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

<!-- {JSON.stringify({
  value,
  startValue,
  newValue,
  isValid,
})} -->
<input type="text" class:invalid={!isValid} bind:value />

<style>
  input.invalid {
    opacity: 0.5;
  }
</style>
