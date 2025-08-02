<script lang="ts">
  import { Button, Toggle } from "ftc-panels"

  import { onMount, setContext } from "svelte"
  import { ExtendedType, GenericTypeJson } from "../../types"
  import Manager from "../../manager"
  import Arrow from "../Arrow.svelte"
  import Field from "./Field.svelte"

  let { manager }: { manager: Manager } = $props()

  setContext("manager", manager)

  onMount(() => {
    manager.state.onChange(
      manager.CONFIGURABLES_KEY,
      (data: Record<string, GenericTypeJson[]>) => {
        const newConfigurables: Record<string, ExtendedType[]> = {}

        for (const key in data) {
          const incomingArray = data[key]
          const existingArray = configurables[key] || []

          newConfigurables[key] = incomingArray.map((incomingItem, index) => {
            const existingItem = existingArray[index]

            const extendItem = (
              item: GenericTypeJson,
              existing?: ExtendedType
            ): ExtendedType => {
              return {
                ...item,
                value: existing?.value ?? item.value,
                isValid: existing?.isValid ?? true,
                valueString: item.value,
                newValueString: existing?.value ?? item.value,
                customValues: item.customValues?.map((subItem, subIndex) =>
                  extendItem(subItem, existing?.customValues?.[subIndex])
                ),
              }
            }

            return extendItem(incomingItem, existingItem)
          })
        }

        configurables = newConfigurables
      }
    )
  })

  let configurables: Record<string, ExtendedType[]> = $state({})

  let changes = $derived.by(() => {
    var result: ChangeJson[] = []

    for (const [key, items] of Object.entries(configurables)) {
      const filtered = items
        .filter(
          (item) => item.isValid && item.valueString !== item.newValueString
        )
        .map((it) => {
          return {
            id: it.id,
            newValueString: it.newValueString,
          }
        })

      if (filtered.length > 0) {
        result = [...result, ...filtered]
      }
    }

    return result
  })
</script>

<nav>
  <Button
    onclick={() => {
      manager.socket.sendMessage("updatedConfigurable", changes)
    }}
    disabled={changes.length == 0}>Update All</Button
  >
</nav>
{#each Object.entries(configurables) as [key, value]}
  <Toggle>
    {#snippet trigger({ isOpen }: { isOpen: boolean })}
      <h3>
        <Arrow {isOpen} />
        {key.split(".")[key.split(".").length - 1]}
      </h3>
    {/snippet}
    {#snippet content({ close }: { close: () => void })}
      <div class="container">
        {#each value as item}
          <Field {item} />
        {/each}
      </div>
    {/snippet}
  </Toggle>
{/each}

<style>
  nav {
    margin-bottom: var(--padding);
  }
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
