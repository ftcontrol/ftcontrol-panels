<script lang="ts">
  import { goto } from "$app/navigation"
  import { global } from "$lib"
  import Section from "$lib/Section.svelte"
  import { Button, TextInput } from "ftc-panels"
</script>

<h1>Plugins</h1>
<div>
  {#each global.plugins as plugin}
    {@const isDev = global.devServers.includes(plugin.details.id)}

    <Section>
      <p>{plugin.details.id}</p>
      <h2>
        {plugin.details.name}
        {#if global.devServers.includes(plugin.details.id)}
          DEV
        {/if}
      </h2>
      <p>by {plugin.details.author}</p>
      <br />
      <p>{plugin.details.description}</p>
      <br />
      <div class="main-buttons">
        <div class="buttons">
          <Button
            onclick={() => {
              goto(`/plugins/${plugin.details.id}`)
            }}>Details</Button
          >
          <Button
            onclick={() => {
              if (isDev) {
                global.devServers = global.devServers.filter(
                  (it) => it != plugin.details.id
                )
              } else {
                global.devServers.push(plugin.details.id)
              }
            }}
          >
            {isDev ? "Disable Dev" : "Enable Dev"}
          </Button>
        </div>

        <TextInput
          bind:value={plugin.details.devURL}
          placeholder={"http://localhost:3000"}
        />
      </div>
    </Section>
  {/each}

  {#each global.skippedPlugins as plugin}
    <Section>
      <h2>
        {plugin.name}
        SKIPPED
      </h2>
      <p>{plugin.id}</p>
      <p>{plugin.author}</p>
    </Section>
  {/each}
</div>

<style>
  .buttons {
    display: flex;
  }
  .main-buttons {
    display: flex;
    flex-direction: column;
  }
  h2,
  p {
    margin: 0;
  }
  p {
    margin: 0.25rem;
  }
  div {
    display: grid;
    gap: 1rem;
    grid-template-columns: repeat(auto-fill, minmax(300px, 1fr));
  }
</style>
