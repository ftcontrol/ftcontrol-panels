<script>
    import GradientImage from "$lib/ui/GradientImage.svelte";
</script>

<section>
    <div class="media">
        <GradientImage desktopType="bottom" mobileType="bottom">
            <img class="bg" src="/notifications-bg.png" alt="UpToDate dashboard background"/>
            <img class="update" src="/update.png" alt="Update Notification"/>
        </GradientImage>
    </div>
    <div class="text">
        <h3>Remain Up-To-Date</h3>
        <p>"Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et
            dolore magna aliqua. Ut enim ad minim.</p>
    </div>
</section>

<style>
    section{
        margin-block: 6rem;
    }

    .text {
        padding-inline: 1rem;
        display: flex;
        justify-content: space-between;
        align-items: flex-start;
    }

    p {
        max-width: 300px;
        text-align: right;
    }

    p, h3 {
        margin: 0;
    }

    h3{
        min-width: 200px;
    }

    .media {
        width: calc(100% - 2rem);
        margin: 1rem;
        border-radius: 0.5rem;
        margin-bottom: -2rem;
        position: relative;
        z-index: -10;
    }

    img {
        width: 100%;
    }

    .update{
        border-radius: 0.5rem;
        width: 25vw;
        min-width: 200px;
        position: absolute;
        top: 0;
        left: 50%;
        transform: translate(-50%, -50%);
        margin: auto;
        box-shadow: 10px 10px 16px 0 rgba(0,0,0,0.66);
        rotate: -4deg;
    }

    .bg{
        opacity: 0.45;
    }
</style>