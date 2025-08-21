<script lang="ts">
    import GradientImage from "$lib/ui/GradientImage.svelte";
</script>

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
    .plugins {
        margin-top: 5rem;
        overflow-x: clip;
        display: flex;
        gap: 6rem;
        padding: 2rem;
        padding-bottom: 14rem;
    }

    p{
        max-width: 300px;
    }

    .all-plugins{
        max-width: 80vw;
        transform: rotate(18deg) translateY(6rem);
        margin-left: auto;
        margin-right: -15vw;
        z-index: -100;
    }

    @media (max-width: 768px) {
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
</style>
