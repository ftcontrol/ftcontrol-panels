<script>
</script>

<section>
  <div class="title">
    <h3>A dashboard<br />for everyone</h3>
    <h4>Powered by Themes Plugin</h4>
  </div>
  <div class="text">
    <img class="plugin" src="/plugin-themes.png" alt="Themes Plugin" />
    <p>
      The Themes plugin lets you change the look of Panels with full control
      over colors, layout, and widgets. Move widgets in a grid structure, insert
      navlets into your navbar, and preview widgets before adding them. Multiple
      tabbed widget groups make it easy to organize everything the way you want.
    </p>
  </div>
  <div class="points">
    <p>Customizable colors</p>
    <p>Resizable layouts</p>
    <p>Grid-based widgets</p>
    <p>Navlets for your navbar</p>
    <p>Tabbed widget groups</p>
  </div>
  <img class="blue theme" src="/theme-blue.png" alt="Theme" />
  <img class="red theme" src="/theme-red.png" alt="Theme" />
  <img class="green theme" src="/theme-green.png" alt="Theme" />
  <img class="white theme" src="/theme-white.png" alt="Theme" />
  <img class="dark theme" src="/theme-dark.png" alt="Theme" />
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

  .points > p {
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

  h3,
  h4,
  p {
    margin: 0;
  }

  h3 {
    text-align: center;
    font-size: min(8vw, 3rem);
    line-height: 1;
    margin-bottom: 0.5rem;
  }

  h4 {
    text-align: center;
    font-size: min(4vw, 1.5rem);
  }

  @media (max-width: 768px) {
    section {
      padding: 1rem;
      gap: 1rem;
      display: flex;
      flex-direction: column;
      background: transparent;
      margin-bottom: -4rem;
    }

    .plugin {
      margin-top: -4.5rem;
      margin-left: -3rem;
      box-shadow: 10px 10px 16px 0 rgba(0, 0, 0, 0.66);
      rotate: 4deg;
      margin-bottom: 0.5rem;
    }

    .title {
      margin-bottom: 4rem;
    }

    h3,
    h4 {
      text-align: left;
    }
    .theme {
      display: inline-block;
      position: relative;
      box-shadow: 0 2px 10px rgba(0, 0, 0, 0.3);
    }
    .theme:not(:first-of-type) {
      margin-top: -50%;
    }
    .theme:nth-child(4) {
      transform: rotate(5deg);
    }

    .theme:nth-child(5) {
      transform: rotate(-3deg);
    }

    .theme:nth-child(6) {
      transform: rotate(2deg);
    }

    .theme:nth-child(7) {
      transform: rotate(-6deg);
    }

    .theme:nth-child(8) {
      transform: rotate(4deg);
    }
  }
</style>
