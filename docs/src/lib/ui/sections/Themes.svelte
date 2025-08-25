<script>
</script>

<section>
    <img class="blue" src="/theme-blue.png" alt="Theme"/>
    <img class="white" src="/theme-white.png" alt="Theme"/>
    <img class="dark" src="/theme-dark.png" alt="Theme"/>
    <img class="red" src="/theme-red.png" alt="Theme"/>
    <img class="green" src="/theme-green.png" alt="Theme"/>
    <div class="points">
        <p>Customizable colors</p>
        <p>Resizable layouts</p>
        <p>Grid-based widgets</p>
        <p>Navlets for your navbar</p>
        <p>Tabbed widget groups</p>
    </div>
    <div class="text">
        <img class="plugin" src="/plugin-themes.png" alt="Themes Plugin"/>
        <p>
            The Themes plugin lets you change the look of Panels with full control over colors,
            layout, and widgets. Move widgets in a grid structure, insert navlets into your
            navbar, and preview widgets before adding them. Multiple tabbed widget groups
            make it easy to organize everything the way you want.
        </p>
    </div>
    <div class="title">
        <h3>A dashboard<br>for everyone</h3>
        <h4>Powered by Themes Plugin</h4>
    </div>
</section>

<style>
    .plugin {
        max-width: 120px;
        margin-top: -2.5rem;
        margin-left: -3rem;
    }

    section {
        display: grid;
        grid-template-columns: repeat(6, 1fr);
        grid-template-rows: repeat(3, auto);
        padding: 4rem;
        gap: 2rem;
        margin-bottom: 4rem;
        margin-top: 10rem;
        background: #363737;
    }

    .points > p{
        font-weight: 600;
    }

    .blue {
        grid-row: 3 / span 1;
        grid-column: 3 / span 2;
    }

    .white {
        grid-row: 2 / span 1;
        grid-column: 5 / span 2;
    }

    .dark {
        grid-row: 2 / span 1;
        grid-column: 1 / span 2;
    }

    .red {
        grid-row: 1 / span 1;
        grid-column: 4 / span 2;
    }

    .green {
        grid-row: 1 / span 1;
        grid-column: 2 / span 2;
    }

    img {
        width: 100%;
    }

    .points {
        grid-row: 3 / span 1;
        grid-column: 1 / span 2;
    }

    .points p {
        text-align: right;
    }

    .text {
        grid-row: 3 / span 1;
        grid-column: 5 / span 2;
    }

    .title {
        grid-row: 2 / span 1;
        grid-column: 3 / span 2;
        display: grid;
        place-content: center;
    }

    h3, h4, p {
        margin: 0;
    }

    h3 {
        text-align: center;
        font-size: min(4vw, 3rem);
        line-height: 1;
        margin-bottom: 0.5rem;
    }

    h4 {
        text-align: center;
        font-size: min(1.5vw, 1.5rem);
    }

    @media (max-width: 768px) {
        section {
            padding: 1rem;
            gap: 1rem;
            grid-template-columns: repeat(6, 1fr);
            grid-template-rows: repeat(4, auto);
        }

        .text {
            grid-row: 1 / span 2;
            grid-column: 3 / span 4;
        }

        .plugin {
            margin-top: -4.5rem;
            margin-left: 0;
            box-shadow: 10px 10px 16px 0 rgba(0, 0, 0, 0.66);
            rotate: -4deg;
            margin-bottom: 0.5rem
        }

        .title {
            grid-row: 1 / span 1;
            grid-column: 1 / span 2;
        }

        .points {
            grid-row: 2 / span 1;
            grid-column: 1 / span 2;
        }

        h3, h4 {
            text-align: left;
        }

        .blue {
            grid-row: 3 / span 1;
            grid-column: 1 / span 2;
        }

        .white {
            grid-row: 3 / span 1;
            grid-column: 3 / span 2;
        }

        .dark {
            grid-row: 3 / span 1;
            grid-column: 5 / span 2;
        }

        .red {
            grid-row: 4 / span 1;
            grid-column: 2 / span 2;
        }

        .green {
            grid-row: 4 / span 1;
            grid-column: 4 / span 2;
        }
    }
</style>