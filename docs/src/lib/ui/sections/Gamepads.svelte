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
            <p>"Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et
                dolore magna aliqua. Ut enim ad minim.</p>
            <p>"Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et
                dolore magna aliqua. Ut enim ad minim.</p>
        </div>
        <div class="media">
            <img class="plugin" src="/plugin-gamepad.png" alt="Gamepads plugin"/>
            <img class="gamepads" src="/gamepads.png" alt="Gamepads"/>
        </div>
    </div>
</section>

<style>
    .g{
        width: 100%;
    }
    section {
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
    }

    img {
        border-radius: 0.5rem;
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
    }

    .wrapper {
        width: 50vw;
        min-width: 400px;
    }

    .gamepads {
        width: 100%;
    }

    @media (max-width: 768px) {
        .showcase {
            max-width: 100%;
            margin-bottom: -12rem;
            position: relative;
            z-index: -10;
        }
        .wrapper{
            padding: 1rem;
        }
        .plugin {
            width: 200px;
            bottom: -45%;
            left: calc(-4rem + 5%);
            rotate: 4deg;
        }
    }
</style>