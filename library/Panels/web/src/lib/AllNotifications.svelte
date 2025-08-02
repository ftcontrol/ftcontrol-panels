<!-- <script lang="ts">
  import { notifications } from "$lib"
  import { Button } from "ftc-panels"
  import type { Notification } from "./notifications.svelte"

  function getSimple(notifications: Notification[]) {
    return notifications.filter((it) => it.actions.length <= 0)
  }
  function getActions(notifications: Notification[]) {
    return notifications.filter((it) => it.actions.length > 0)
  }
</script>

<section>
  {#each getSimple(notifications.data) as notif}
    <div
      class="notif"
      in:fly={{ y: -10, opacity: 0, duration: 200 }}
      out:fly={{ y: -10, opacity: 0, duration: 300 }}
    >
      <p>{notif.text}</p>
    </div>
  {/each}
</section>
<section>
  {#each getActions(notifications.data) as notif}
    <div
      class="notif"
      in:fly={{ y: -10, opacity: 0, duration: 200 }}
      out:fly={{ y: -10, opacity: 0, duration: 300 }}
    >
      <p class="center">{notif.text}</p>
      <div class="flex">
        {#each notif.actions as action}
          <Button
            onclick={() => {
              action.task()
              notifications.data = notifications.data.filter((n) => n !== notif)
            }}
          >
            {action.text}
          </Button>
        {/each}
      </div>
    </div>
  {/each}
</section>

<style>
  section {
    position: absolute;
    top: var(--padding);
    left: 50%;
    transform: translateX(-50%);
    text-align: center;
    z-index: 100;
  }
  .notif {
    border: 1px solid var(--bgDark);
    background-color: var(--bgMedium);
    padding: 0.5em 1em;
    border-radius: 1rem;
    margin-bottom: 0.25rem;
  }
  p.center {
    text-align: center;
  }
  .flex {
    display: flex;
    gap: 1rem;
    justify-content: space-between;
  }
</style> -->
