<script lang="ts">
  import Manager from "../../manager"
  import type { FieldWithValues, GenericTypeJson } from "../../types"
  import { onMount, setContext } from "svelte"
  import Arrow from "../Arrow.svelte"
  import Field from "./Field.svelte"

  let {
    manager,
  }: {
    manager: Manager
  } = $props()

  setContext("manager", manager)

  let initialConfigurables: Record<string, GenericTypeJson[]> = {}
  let configurables: Record<string, GenericTypeJson[]> = {}
  let changedValues: Record<string, FieldWithValues[]> = $state({})

  onMount(() => {
    manager.state.onChange(manager.INITIAL_CONFIGURABLES_KEY, (data) => {
      initialConfigurables = data
      updateChangedValues()
    })
    manager.state.onChange(manager.CONFIGURABLES_KEY, (data) => {
      configurables = data
      updateChangedValues()
    })
  })

  function deepEqual(a: any, b: any): boolean {
    if (a === b) return true
    if (a == null || b == null) return false
    if (typeof a !== typeof b) return false
    if (typeof a !== "object") return false

    const keysA = Object.keys(a)
    const keysB = Object.keys(b)
    if (keysA.length !== keysB.length) return false

    for (const key of keysA) {
      if (!keysB.includes(key)) return false
      if (!deepEqual(a[key], b[key])) return false
    }
    return true
  }

  function getFieldValue(field: GenericTypeJson): any {
    const hasCustomValues = (field.customValues?.length ?? 0) > 0
    if (hasCustomValues) {
      return field.customValues
    } else {
      return field.value
    }
  }

  function isChangedField(
    field: GenericTypeJson,
    initialField?: GenericTypeJson
  ): boolean {
    if (!initialField) return true

    const currentValue = getFieldValue(field)
    const initialValue = getFieldValue(initialField)

    const hasCustomValues = (field.customValues?.length ?? 0) > 0
    const hasInitialCustomValues = (initialField.customValues?.length ?? 0) > 0

    if (hasCustomValues || hasInitialCustomValues) {
      const customFields = field.customValues ?? []
      const initialCustomFields = initialField.customValues ?? []

      if (customFields.length !== initialCustomFields.length) return true
      if (customFields.length === 0 && initialCustomFields.length === 0) {
        return false
      }

      const initialMap = new Map(initialCustomFields.map((f) => [f.id, f]))
      for (const custom of customFields) {
        const initial = initialMap.get(custom.id)
        if (!initial || isChangedField(custom, initial)) return true
      }

      const currentIds = new Set(customFields.map((f) => f.id))
      for (const initial of initialCustomFields) {
        if (!currentIds.has(initial.id)) return true
      }
      return false
    } else {
      return !deepEqual(currentValue, initialValue)
    }
  }

  function convertToFieldWithValues(
    field: GenericTypeJson,
    initialField?: GenericTypeJson
  ): FieldWithValues {
    const hasCustomValues = (field.customValues?.length ?? 0) > 0

    let customValues: FieldWithValues[] | undefined = undefined
    if (hasCustomValues) {
      const initialMap = new Map(
        (initialField?.customValues ?? []).map((f) => [f.id, f])
      )
      customValues = field.customValues!.map((sub) =>
        convertToFieldWithValues(sub, initialMap.get(sub.id))
      )
    }

    return {
      ...field,
      customValues,
      initialValue: initialField ? getFieldValue(initialField) : undefined,
      currentValue: getFieldValue(field),
    }
  }

  function updateChangedValues() {
    const result: Record<string, FieldWithValues[]> = {}

    for (const [className, currentFields] of Object.entries(configurables)) {
      const initialFields = initialConfigurables[className] ?? []
      const initialMap = new Map(initialFields.map((f) => [f.id, f]))

      const changedFields = currentFields
        .filter((field) => {
          const initial = initialMap.get(field.id)
          return isChangedField(field, initial)
        })
        .map((field) =>
          convertToFieldWithValues(field, initialMap.get(field.id))
        )

      if (changedFields.length > 0) {
        result[className] = changedFields
      }
    }

    changedValues = result
  }
</script>

{#if Object.keys(changedValues).length > 0}
  {#each Object.entries(changedValues) as [key, items]}
    <h3>
      <Arrow isOpen={true} />
      {key.split(".").pop()}
    </h3>
    <div class="container">
      {#each items as item}
        <Field {item} />
      {/each}
    </div>
  {/each}
{:else}
  <p>No values changed</p>
{/if}

<style>
  h3 {
    margin: 0;
    margin-bottom: var(--padding);
  }
  .container {
    display: flex;
    flex-direction: column;
    gap: calc(var(--padding) / 1.5);
    margin-bottom: var(--padding);
  }
</style>
