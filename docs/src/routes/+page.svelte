<script lang="ts">

    import Panels from "$lib/ui/Panels.svelte";
    import GitHub from "$lib/ui/GitHub.svelte";
</script>

<div class="main">
    <section>
        <div class="flex-right">
            <Panels/>
            <h2>An intuitive, real-time<br> dashboard for your FTC Robot.</h2>
            <div class="flex">
                <a class="button primary">Read the docs</a>
                <a class="button">Install</a>
                <a>
                    <GitHub/>
                </a>
            </div>
            <p>v2.0.0</p>
        </div>

        <img src="/homepage-panels.png" alt="panels"/>
    </section>
</div>

<style>
    h2{
        margin-block: 0.25em;
        text-align: right;
    }
    .flex {
        display: flex;
        gap: 0.5rem;
        align-items: center;
        width: fit-content;
    }

    .flex-right {
        display: flex;
        flex-direction: column;
        gap: 0.5rem;
        align-items: flex-end;
    }

    .button {
        display: inline-block;
        background: #212222;
        padding: 0.25em 1em;
        border-radius: 0.25em;
    }

    .button.primary {
        background: #001dff;
    }

    section {
        width: 100%;
        display: flex;
        flex-wrap: wrap;
        gap: 1rem;
        padding: 2rem;
        align-items: center;
        margin: 4rem auto;
        margin-right: -10vw;
    }

    .main{
        max-width: 100vw;
        overflow-x: hidden;
        overflow-y: auto;
    }

    img {
        width: 60vw;
        height: auto;
    }
</style>