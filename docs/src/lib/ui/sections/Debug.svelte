<script>
</script>

<section>
    <h2>Debug like a PRO</h2>
    <div class="text">
        <div class="flex">
            <div class="media">
                <img src="/plugin-telemetry.png" alt="Plugin telemetry"/>
                <img src="/telemetry-1.png" alt="Telemetry"/>
                <img src="/telemetry-2.png" alt="Telemetry"/>
            </div>
            <h3>Telemetry</h3>
            <div>
                <p>"Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore
                    et dolore magna aliqua. Ut enim ad minim.</p>
            </div>
        </div>
        <div class="flex">
            <div class="media">
                <img src="/plugin-field.png" alt="Plugin field"/>
                <img src="/field.png" alt="Field Showcase"/>
            </div>
           <div>
               <h3>Field</h3>
               <p>"Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et
                   dolore magna aliqua. Ut enim ad minim.</p>
           </div>
        </div>
        <div class="flex">
            <div class="media">
                <img src="/plugin-graph.png" alt="Plugin graph"/>
                <img src="/graph.png" alt="Graph"/>
                <img src="/graph-telemetry.png" alt="Graph telemetry"/>
            </div>
           <div>
               <h3>Graph</h3>
               <p>"Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et
                   dolore magna aliqua. Ut enim ad minim.</p>
           </div>
        </div>
        <div class="flex">
            <div class="media">
                <img src="/plugin-lights.png" alt="Plugin lights"/>
                <img src="/lights.png" alt="Lights"/>
                <img src="/lights-telemetry.png" alt="Lights telemetry"/>
            </div>
           <div>
               <h3>Lights</h3>
               <p>"Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et
                   dolore magna aliqua. Ut enim ad minim.</p>
           </div>
        </div>
        <div class="flex">
            <div class="media">
                <img src="/plugin-capture.png" alt="Plugin capture"/>
                <img src="/capture.png" alt="Capture"/>
            </div>
           <div>
               <h3>Capture</h3>
               <p>"Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et
                   dolore magna aliqua. Ut enim ad minim.</p>
           </div>
        </div>
        <div class="flex">
            <div class="media">
                <img src="/plugin-configurables.png" alt="Plugin configurables"/>
                <img src="/configurables.png" alt="Configurables"/>
                <img src="/changed-configurables.png" alt="Changed configurables"/>
                <img src="/configurables-telemetry.png" alt="Configurables telemetry"/>
            </div>
            <div>
                <h3>Configurables</h3>
                <p>"Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et
                    dolore magna aliqua. Ut enim ad minim.</p>
            </div>
        </div>
        <div class="flex">
            <div class="media">
                <img src="/plugin-docs.png" alt="Plugin docs"/>
                <img src="/docs.png" alt="Docs"/>
            </div>
            <div>
                <h3>Integrated Docs</h3>
                <p>"Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et
                    dolore magna aliqua. Ut enim ad minim.</p>
            </div>
        </div>
        <div class="flex">
            <div class="media">
                <img src="/plugin-battery.png" alt="Plugin battery"/>
                <img src="/plugin-pinger.png" alt="Plugin pinger"/>
                <img src="/battery-pinger.png" alt="Battery & Pinger"/>
            </div>
            <div>
                <h3>Battery + Pinger</h3>
                <p>"Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et
                    dolore magna aliqua. Ut enim ad minim.</p>
            </div>
        </div>
        <div class="flex">
            <div class="media">
                <img src="/plugin-opmode-control.png" alt="Plugin opmode control"/>
                <img src="/opmode-control.png" alt="OpMode control"/>
                <img src="/opmode-control-autos.png" alt="OpMode control autos"/>
                <img src="/opmode-control-teleops.png" alt="OpMode control teleops"/>
            </div>
            <div>
                <h3>Control OpModes</h3>
                <p>"Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et
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

    .media {
        flex: 1 1 100%;
        display: flex;
        flex-wrap: wrap;
        gap: 0.5rem;
        justify-content: center;
        align-items: center;
    }

    .media img {
        max-width: 120px;
        max-height: 120px;
        width: auto;
        height: auto;
    }

    /* 🔽 Responsive adjustments */
    @media (max-width: 900px) {
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

        .media {
            justify-content: center;
        }

        img {
            width: 45%;
        }
    }

</style>