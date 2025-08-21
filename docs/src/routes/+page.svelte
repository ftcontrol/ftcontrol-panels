<script lang="ts">
    import Panels from "$lib/ui/Panels.svelte"
    import GitHub from "$lib/ui/GitHub.svelte"
    import GradientImage from "$lib/ui/GradientImage.svelte";
</script>

<section class="hero">
    <div class="copy">
        <Panels/>
        <h2>An intuitive, real-time<br/>dashboard for your FTC Robot.</h2>

        <div class="actions">
            <a class="button primary" href="/docs">Read the docs</a>
            <a class="button" href="/docs/com.bylazar.docs/Prerequisites">Install</a>
            <a
                    class="icon"
                    href="https://github.com/ftcontrol/ftcontrol-panels"
                    aria-label="View on GitHub"
            >
                <GitHub/>
            </a>
        </div>

        <p class="version">v2.0.0</p>
    </div>

    <div class="media">
        <GradientImage desktopType="right" mobileType="bottom">
            <img src="/homepage-panels.png" alt="Dashboard panels preview"/>
        </GradientImage>
    </div>
</section>

<section class="plugins">
    <div class="text">
        <h3>The power of<br>Panels Plugins</h3>
        <p>"Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim.</p>
        <h3>The power to<br>extend as you wish</h3>
        <p>"Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim.</p>
    </div>
    <div class="all-plugins">
        <GradientImage desktopType="right" mobileType="right">
            <img  src="/all-plugins.png" alt="Panels plugins list"/>
        </GradientImage>
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

    .plugins {
        margin-top: 5rem;
        overflow-x: clip;
        display: flex;
        gap: 5rem;
        padding: 1rem
    }

    p{
        max-width: 300px;
    }

    .all-plugins{
        max-width: 80vw;
        transform: rotate(18deg) translateY(6rem);
        margin-left: auto;
        margin-right: -15vw;
    }

    @media (max-width: 768px) {
        .all-plugins{
            z-index: -100;
        }
        .plugins{
            margin-top: -2rem;
            flex-direction: column-reverse;
            gap: 4rem;
            align-items: flex-end;
        }
        h3, p{
            text-align: right;
        }
        .text {
            display: grid;
            grid-template-columns: 1fr 1fr;
            grid-template-rows: auto auto;
            gap: 1rem;
        }

        .text h3:nth-of-type(1) {
            grid-column: 1;
            grid-row: 1;
        }

        .text p:nth-of-type(1) {
            grid-column: 1;
            grid-row: 2;
        }

        .text h3:nth-of-type(2) {
            grid-column: 2;
            grid-row: 1;
        }

        .text p:nth-of-type(2) {
            grid-column: 2;
            grid-row: 2;
        }
    }


    img{
        width: 100%;
        height: auto;
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
    }

    .button.primary {
        background: #001dff;
    }

    .media {
        margin-right: calc(-1 * var(--page-pad));
    }

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

        .copy {
            align-items: center;
            text-align: center;
            margin-bottom: 4rem;
        }

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

        .hero {
            margin-top: 16rem;
        }
    }
</style>
