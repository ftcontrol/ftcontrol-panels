<script>
  import GradientImage from "$lib/ui/GradientImage.svelte"
</script>

<section>
  <div class="media">
    <GradientImage desktopType="bottom" mobileType="bottom">
      <img
        class="stream"
        src="/limelight-stream.png"
        alt="Limelight dashboard background"
      />
    </GradientImage>
  </div>
  <div class="flex">
    <div class="text">
      <img class="plugin" src="/plugin-limelight.png" alt="Limelight plugin" />
      <h3>Wirelessly control your<br /><span>Limelight</span></h3>
      <p>
        Panels gives you full access to the Limelight 3A dashboard, with
        built-in widgets for streaming video, tracking camera stats, and logging
        telemetry, keeping your vision system seamlessly integrated with the
        rest of your dashboard.
      </p>
    </div>
    <div class="dash">
      <GradientImage mobileType="bottom">
        <img
          src="/limelight-dashboard-2.png"
          alt="Limelight dashboard background"
        />
      </GradientImage>
    </div>
  </div>
</section>

<style>
  .plugin {
    border-radius: 0.5rem;
    width: 60%;
    right: 0;
  }

  section {
    margin-block: 4rem;
  }

  span {
    font-size: 2rem;
    color: #66bf0d;
    font-weight: bold;
  }

  p,
  h3 {
    max-width: 300px;
    margin: 0;
    text-align: right;
  }

  .media {
    width: calc(80% - 2rem);
    margin: 1rem auto;
    border-radius: 0.5rem;
  }

  img {
    width: 100%;
  }

  .flex {
    display: flex;
    gap: 1rem;
    margin-top: -6rem;
    z-index: 100;
    position: relative;
    align-items: center;
    justify-content: center;
    padding-inline: 1rem;
  }

  .dash {
    max-width: 50vw;
  }

  @media (max-width: 768px) {
    section {
      margin-bottom: 0rem;
    }
    .media {
      width: calc(100% - 2rem);
    }
    .flex {
      flex-direction: column-reverse;
      overflow-x: hidden;
    }
    .text {
      width: calc(100% - 2rem);
      margin-top: -7rem;
    }
    p,
    h3 {
      max-width: 400px;
      margin-left: auto;
    }
    .dash {
      margin-left: auto;
      margin-right: -1.5rem;
      max-width: 70vw;
    }
    .plugin {
      max-width: 133px;
      position: relative;
      z-index: 9999;
    }
  }
</style>
