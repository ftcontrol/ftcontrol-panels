<script>
import GradientImage from "$lib/ui/GradientImage.svelte";
</script>

<section>
    <div class="text">
        <h3>Remain Up-To-Date</h3>
        <p>"Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim.</p>
    </div>
    <div class="media">
        <GradientImage desktopType="bottom" mobileType="bottom">
            <img src="/notifications-bg.png" alt="UpToDate dashboard background"/>
        </GradientImage>
    </div>
</section>

<style>
    .text{
        padding-inline: 1rem;
        display: flex;
        justify-content: space-between;
        align-items: flex-end;
    }
    p{
        max-width: 300px;
        text-align: right;
    }
    p, h3{
        margin: 0;
    }
.media{
    width: calc(100% - 2rem);
    margin: 1rem;
    border-radius: 0.5rem;
}
img{
    width: 100%;
}
</style>