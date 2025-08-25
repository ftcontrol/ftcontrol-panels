<script>
    import GradientImage from "$lib/ui/GradientImage.svelte";
</script>

<section>
    <div class="showcase">
        <GradientImage mobileType="bottom">
            <img class="g" src="/gamepads-showcase.png" alt="Gamepads Showcase"/>
        </GradientImage>
    </div>
    <div class="wrapper">
        <div class="text">
            <h3>Gamepad(s)</h3>
            <p>Panels supports up to two FTC gamepads, giving Driver 1 and Driver 2 full wireless control of the robot. Both controllers are mapped and ready to use right out of the box.</p>
        </div>
        <div class="media">
            <img class="plugin" src="/plugin-gamepad.png" alt="Gamepads plugin"/>
            <img class="gamepads" src="/gamepads.png" alt="Gamepads"/>
        </div>
    </div>
</section>

<style>
    .g {
        width: 100%;
    }

    section {
        max-width: 100vw;
        margin: 6rem auto;
        padding: 1rem;

        display: flex;
        flex-wrap: wrap;
        gap: 1rem;
    }

    p, h3 {
        max-width: 300px;
    }

    .showcase {
        max-width: 33vw;
        height: auto;
        object-fit: cover;
        border-radius: 0.5rem;
        position: relative;
    }

    img {
        width: 100%;
        max-height: 100%;
        object-fit: cover;
    }

    .media {
        position: relative;
    }

    .plugin {
        width: 200px;
        position: absolute;
        bottom: -20%;
        left: -25%;
        box-shadow: 10px 10px 16px 0 rgba(0, 0, 0, 0.66);
        border-radius: 0.5rem;
    }

    .wrapper {
        width: 50vw;
        min-width: 400px;
    }

    .gamepads {
        width: 100%;
        border-radius: 0.5rem;
    }

    @media (max-width: 768px) {
        .showcase {
            max-width: calc(100vw - 1rem);
            width: 100%;
            max-height: 400px;
            margin-bottom: -12rem;
            position: relative;
            z-index: -10;
            overflow: hidden;
        }
        .plugin {
            width: 200px;
            bottom: -45%;
            left: calc(-4rem + 8%);
            rotate: 4deg;
        }
        .text {
            width: fit-content;
        }

        .wrapper {
            padding: 1rem;
        }

        .media {
            max-width: 80vw;
        }
    }
</style>