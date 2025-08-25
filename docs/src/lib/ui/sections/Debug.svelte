<script>
</script>
<section>
    <h2>And other tools</h2>

    <div class="text">
        <div class="flex">
            <img src="/combined/telemetry.png" alt="Telemetry plugin"/>
            <div>
                <h3>Telemetry</h3>
                <p>
                    Text-based telemetry that can mirror Driver Hub telemetry, so data stays
                    consistent across your dashboard and the field.
                </p>
            </div>
        </div>

        <div class="flex">
            <img src="/combined/field.png" alt="Field plugin"/>
            <div>
                <h3>Field</h3>
                <p>
                    A drawable canvas of the game field, render any shape or path with mapped
                    coordinates and support for popular pathing libraries.
                </p>
            </div>
        </div>

        <div class="flex">
            <img src="/combined/graph.png" alt="Graph plugin"/>
            <div>
                <h3>Graph</h3>
                <p>
                    Visualize live data over time. Perfect for tuning PIDs and validating
                    changes at a glance.
                </p>
            </div>
        </div>

        <div class="flex">
            <img src="/combined/lights.png" alt="Lights plugin"/>
            <div>
                <h3>Lights</h3>
                <p>
                    Mimics goBILDA-style RGB indicators to provide fast, glanceable feedback
                    during development.
                </p>
            </div>
        </div>

        <div class="flex">
            <img src="/combined/capture.png" alt="Capture plugin"/>
            <div>
                <h3>Capture</h3>
                <p>
                    Record debug data and replay it later, great for diagnosing intermittent
                    issues without being tethered to the robot.
                </p>
            </div>
        </div>

        <div class="flex">
            <img src="/combined/configurables.png" alt="Configurables plugin"/>
            <div>
                <h3>Configurables</h3>
                <p>
                    Tunable variables that update in real time and support any data type—perfect for adjusting positions and parameters on the fly.                </p>
            </div>
        </div>

        <div class="flex">
            <img src="/combined/docs.png" alt="Integrated Docs plugin"/>
            <div>
                <h3>Integrated Docs</h3>
                <p>
                    Read the docs on the website or directly inside Panels, even when you’re
                    offline and not connected to Wi-Fi.
                </p>
            </div>
        </div>

        <div class="flex">
            <img src="/combined/pinger-battery.png" alt="Battery and Pinger plugins"/>
            <div>
                <h3>Battery & Pinger</h3>
                <p>
                    Monitor robot battery level and input delay in real-time to keep your
                    system responsive and reliable.
                </p>
            </div>
        </div>

        <div class="flex">
            <img src="/combined/opmodecontrol.png" alt="OpMode Control plugin"/>
            <div>
                <h3>OpMode Control</h3>
                <p>
                    A Driver Station–style layout with dedicated Autos and TeleOps sections, plus built-in timers for safe robot stop.
                </p>
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