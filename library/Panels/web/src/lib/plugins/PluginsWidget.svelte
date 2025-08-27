<script lang="ts">
  import { Button, type PluginInfo } from "ftc-panels"
  import Section from "$lib/Section.svelte"
  import { goto } from "$app/navigation"
  import { PluginDetails } from "ftc-panels-docs"

  let {
    plugin,
    isDev = false,
  }: {
    plugin: PluginInfo
    isDev?: boolean
  } = $props()
</script>

<Section>
  {#if isDev}
    <span>DEV</span>
  {/if}
  <PluginDetails
    plugin={plugin.details}
    transparent={true}
    showIncludedPlugins={false}
  >
    <div class="main-buttons">
      <Button
        onclick={() => {
          goto(`/plugins/${plugin.details.id}`)
        }}
        >Details
      </Button>
    </div>
  </PluginDetails>
</Section>

<style>
  .main-buttons {
    display: flex;
    flex-direction: column;
    margin-top: 1rem;
    gap: 1rem;
  }
  span {
    position: absolute;
    top: var(--padding);
    right: var(--padding);
    font-weight: bold;
  }
</style>
