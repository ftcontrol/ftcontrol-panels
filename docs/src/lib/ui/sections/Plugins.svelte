<script lang="ts">
  import GradientImage from "$lib/ui/GradientImage.svelte"
</script>

<section class="plugins">
  <div class="text">
    <h3>Panels 1.0: Built on Plugins</h3>
    <p>
      Panels is now entirely plugin-driven. Out of the box, you get all the
      essentials delivered as native plugins.
    </p>
    <h3>Extend with custom plugins.</h3>
    <p>
      Build a Svelte frontend and Kotlin backend, hook into the full Panels UI,
      and use the JS utilities, components, and FTC SDK to quickly ship new
      features. Panels becomes your FTC app modding platform.
    </p>
  </div>
  <div class="all-plugins">
    <GradientImage desktopType="right" mobileType="right">
      <img src="/all-plugins.png" alt="Panels plugins list" />
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

  p {
    max-width: 300px;
  }

  .all-plugins {
    max-width: 80vw;
    transform: rotate(18deg) translateY(6rem);
    margin-left: auto;
    margin-right: -15vw;
    z-index: -100;
  }

  @media (max-width: 768px) {
    .plugins {
      margin-top: -2rem;
      flex-direction: column-reverse;
      gap: 4rem;
      align-items: flex-end;
      padding-bottom: 2rem;
    }
    h3,
    p {
      text-align: right;
    }
  }

  img {
    width: 100%;
    height: auto;
  }
</style>
