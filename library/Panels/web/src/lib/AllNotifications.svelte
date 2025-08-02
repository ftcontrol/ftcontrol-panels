<script lang="ts">
  import { global } from "$lib"
  import { Button } from "ftc-panels"
</script>

{#each global.notifications
  .slice()
  .sort((a, b) => b.timestamp - a.timestamp) as notif, index}
  {#if notif.actions.length == 0}
    <div class="notif" class:notLast={index != global.notifications.length - 1}>
      <p>{notif.text}</p>
    </div>
  {:else}
    <div class="notif" class:notLast={index != global.notifications.length - 1}>
      <p class="center">{notif.text}</p>
      <div class="flex">
        {#each notif.actions as action}
          <Button
            onclick={() => {
              action.task()
            }}
          >
            {action.text}
          </Button>
        {/each}
      </div>
    </div>
  {/if}
{/each}

<style>
  .notif {
    width: 100%;
    padding-block: var(--padding);
  }
  p {
    max-width: 100%;
    overflow-x: auto;
  }
  .notLast {
    border-bottom: 1px solid var(--bgLight);
  }
  p.center {
    text-align: center;
  }
  .flex {
    display: flex;
    gap: 1rem;
    justify-content: space-between;
  }
</style>
