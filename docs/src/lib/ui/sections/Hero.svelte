<script lang="ts">
  import { simpleModules } from "$lib/simpleData"
  import GradientImage from "$lib/ui/GradientImage.svelte"
  import HeroCopy from "$lib/ui/sections/HeroCopy.svelte"
</script>

<section class="hero">
  <div class="copy">
    <HeroCopy />
  </div>

  <div class="media">
    <GradientImage desktopType="right" mobileType="bottom">
      <img src="/homepage-panels.png" alt="Dashboard panels preview" />
    </GradientImage>
  </div>
</section>

<style>
  img {
    width: 100%;
    height: auto;
  }

  .hero {
    max-width: 1200px;
    margin: 10rem auto 15rem;
    padding: 0 1rem;
    display: grid;
    grid-template-columns: 1.05fr 0.95fr;
    align-items: center;
    gap: 1rem;
  }

  .copy {
    display: flex;
    flex-direction: column;
    align-items: flex-end;
    text-align: right;
    gap: 0.5rem;
  }

  .media img {
    display: block;
    width: 100%;
    height: auto;
    object-fit: contain;
    object-position: right center;
  }

  @media (max-width: 768px) {
    .hero {
      grid-template-columns: 1fr;
      margin: 2rem auto;
      margin-top: 11rem;
      padding: 0 1rem;
      gap: 1rem;
    }

    .copy {
      align-items: center;
      text-align: center;
      margin-bottom: 7rem;
    }

    .media {
      margin: 0;
      width: 100%;
      display: flex;
      justify-content: center;
    }

    .media img {
      max-width: 100%;
      width: 100%;
      height: auto;
      object-fit: contain;
      object-position: center;
    }
  }
</style>
