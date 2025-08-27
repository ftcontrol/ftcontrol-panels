<script lang="ts">
  import Panels from "$lib/ui/Panels.svelte"
  import GitHub from "$lib/ui/GitHub.svelte"
</script>

<Panels />
<h2>An intuitive, real-time<br />dashboard for your FTC Robot.</h2>

<div class="actions">
  <a class="button primary" href="/docs/com.bylazar.docs">Read the docs</a>
  <a class="button" href="/docs/com.bylazar.docs/Prerequisites">Install</a>
  <a
    class="icon"
    href="https://github.com/ftcontrol/ftcontrol-panels"
    aria-label="View on GitHub"
  >
    <GitHub />
  </a>
</div>

<p class="version">v1.0.0</p>

<style>
  h2 {
    margin: 0.25em 0;
    font-weight: 700;
    font-size: clamp(1.5rem, 1.2vw + 1rem, 2.4rem);
    line-height: 1.15;
  }

  .version {
    margin: 0;
    opacity: 0.7;
    font-size: clamp(0.85rem, 0.35vw + 0.8rem, 0.95rem);
  }

  .actions {
    display: flex;
    flex-wrap: wrap;
    gap: 0.55rem;
    justify-content: flex-end;
    align-items: center;
  }

  .button {
    display: inline-flex;
    align-items: center;
    justify-content: center;
    background: #212222;
    color: #fff;
    text-decoration: none;
    font-size: clamp(0.9rem, 0.35vw + 0.85rem, 1rem);
    padding: 0.5em 0.9em;
    border-radius: var(--radius);
    border-radius: 0.5rem;
  }

  .button.primary {
    background: #001dff;
  }

  @media (max-width: 768px) {
    .actions {
      justify-content: center;
    }

    h2 {
      font-size: clamp(1.25rem, 5.5vw + 0.5rem, 1.9rem);
      line-height: 1.2;
    }

    .button {
      font-size: clamp(0.95rem, 3.5vw, 1.05rem);
      padding: 0.55em 1em;
    }
  }
</style>
