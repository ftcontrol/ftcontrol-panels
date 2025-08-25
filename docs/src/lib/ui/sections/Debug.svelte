<script>
</script>

<section>
    <h2>And other tools</h2>
    <div class="text">
        <div class="flex">
            <img src="/combined/telemetry.png" alt="Plugin telemetry"/>
            <div>
                <h3>Telemetry</h3>
                <p>"Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore
                    et dolore magna aliqua. Ut enim ad minim.</p>
            </div>
        </div>
        <div class="flex">
            <img src="/combined/field.png" alt="Plugin field"/>
            <div>
                <h3>Field</h3>
                <p>"Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore
                    et
                    dolore magna aliqua. Ut enim ad minim.</p>
            </div>
        </div>
        <div class="flex">
            <img src="/combined/graph.png" alt="Plugin graph"/>
            <div>
                <h3>Graph</h3>
                <p>"Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore
                    et
                    dolore magna aliqua. Ut enim ad minim.</p>
            </div>
        </div>
        <div class="flex">
            <img src="/combined/lights.png" alt="Plugin lights"/>
            <div>
                <h3>Lights</h3>
                <p>"Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore
                    et
                    dolore magna aliqua. Ut enim ad minim.</p>
            </div>
        </div>
        <div class="flex">
            <img src="/combined/capture.png" alt="Plugin capture"/>
            <div>
                <h3>Capture</h3>
                <p>"Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore
                    et
                    dolore magna aliqua. Ut enim ad minim.</p>
            </div>
        </div>
        <div class="flex">
            <img src="/combined/configurables.png" alt="Plugin configurables"/>
            <div>
                <h3>Configurables</h3>
                <p>"Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore
                    et
                    dolore magna aliqua. Ut enim ad minim.</p>
            </div>
        </div>
        <div class="flex">
            <img src="/combined/docs.png" alt="Plugin docs"/>
            <div>
                <h3>Integrated Docs</h3>
                <p>"Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore
                    et
                    dolore magna aliqua. Ut enim ad minim.</p>
            </div>
        </div>
        <div class="flex">
            <img src="/combined/pinger-battery.png" alt="Plugins pinger & battery"/>
            <div>
                <h3>Battery + Pinger</h3>
                <p>"Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore
                    et
                    dolore magna aliqua. Ut enim ad minim.</p>
            </div>
        </div>
        <div class="flex">
            <img src="/combined/opmodecontrol.png" alt="Plugin opmode control"/>
            <div>
                <h3>Control OpModes</h3>
                <p>"Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore
                    et
                    dolore magna aliqua. Ut enim ad minim.</p>
            </div>
        </div>
    </div>
</section>

<style>
    section {
        margin-block: 12rem;
    }

    h2 {
        text-align: center;
    }

    .text {
        display: grid;
        padding: 1rem;
        gap: 1rem;
        grid-template-columns: 1fr 1fr 1fr;
        max-width: 1100px;
        margin: 0 auto;
    }

    .flex {
        display: flex;
        flex-wrap: wrap;
        gap: 0.5rem;
    }

    img{
        width: 100%;
        height: 256px;
        object-fit: contain;
    }


    @media (max-width: 1000px) {
        .text {
            grid-template-columns: 1fr 1fr;
        }
    }

    @media (max-width: 600px) {
        .text {
            grid-template-columns: 1fr;
        }

        .flex {
            flex-direction: column;
            align-items: center;
            text-align: center;
        }
    }

</style>