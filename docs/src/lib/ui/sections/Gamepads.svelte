<script>
</script>

<section>
    <img class="showcase" src="/gamepads-showcase.png" alt="Gamepads Showcase"/>
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
    }

    .gamepads {
        width: 100%;
    }
</style>