<script>
    import HeroCopy from "$lib/ui/sections/HeroCopy.svelte";
</script>

<section>
    <HeroCopy/>
</section>

<style>
    section {
        margin: 0 auto;
        display: flex;
        flex-direction: column;
        align-items: center;
        gap: 0.25rem;
        margin-block: 14rem;
        margin-top: 8rem;
    }
    section > :global(h2) {
        text-align: center;
    }
</style>