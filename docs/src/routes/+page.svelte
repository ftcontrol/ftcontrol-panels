<script lang="ts">
    import Panels from "$lib/ui/Panels.svelte";
    import GitHub from "$lib/ui/GitHub.svelte";
</script>

<section class="hero">
    <div class="copy">
        <Panels />
        <h2>An intuitive, real-time<br />dashboard for your FTC Robot.</h2>

        <div class="actions">
            <a class="button primary" href="/docs">Read the docs</a>
            <a class="button" href="/install">Install</a>
            <a class="icon" href="https://github.com/your/repo" aria-label="View on GitHub">
                <GitHub />
            </a>
        </div>

        <p class="version">v2.0.0</p>
    </div>

    <div class="media">
        <img src="/homepage-panels.png" alt="Dashboard panels preview" />
    </div>
</section>

<style>
    :root {
        font-size: clamp(14px, 1.6vw, 18px);

        --page-max: 1200px;
        --page-pad: 2rem;
        --gap: 1.5rem;
        --radius: 0.5rem;
    }

    .hero {
        max-width: var(--page-max);
        margin: 10rem auto 3rem;
        padding: 0 var(--page-pad);
        display: grid;
        grid-template-columns: 1.05fr 0.95fr;
        align-items: center;
        gap: var(--gap);
    }

    .copy {
        display: flex;
        flex-direction: column;
        align-items: flex-end;
        text-align: right;
        gap: 0.5rem;
    }

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

    .button{
        display: inline-flex;
        align-items: center;
        justify-content: center;
        background: #212222;
        color: #fff;
        text-decoration: none;
        font-size: clamp(0.9rem, 0.35vw + 0.85rem, 1rem);
        padding: 0.5em 0.9em;
        border-radius: var(--radius);
    }

    .button.primary { background: #001dff; }

    .media { margin-right: calc(-1 * var(--page-pad)); }
    .media img {
        display: block;
        width: 100%;
        height: auto;
        object-fit: contain;
        object-position: right center;
    }

    @media (max-width: 768px) {
        .hero {
            grid-template-columns: 1fr;
            margin: 2rem auto;
            padding: 0 1rem;
            gap: 1rem;
        }

        .copy { align-items: center; text-align: center; }
        .actions { justify-content: center; }

        h2 {
            font-size: clamp(1.25rem, 5.5vw + 0.5rem, 1.9rem);
            line-height: 1.2;
        }

        .button{
            font-size: clamp(0.95rem, 3.5vw, 1.05rem);
            padding: 0.55em 1em;
        }

        .media {
            margin: 0;
            width: 100%;
            display: flex;
            justify-content: center;
        }
        .media img {
            max-width: 100%;
            width: 100%;
            height: auto;
            object-fit: contain;
            object-position: center;
        }
    }
</style>
