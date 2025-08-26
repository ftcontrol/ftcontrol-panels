<script lang="ts">
  import Footer from "$lib/ui/sections/Footer.svelte"
  import Hero from "$lib/ui/sections/Hero.svelte"
  import Plugins from "$lib/ui/sections/Plugins.svelte"
  import HeroCenter from "$lib/ui/sections/HeroCenter.svelte"
  import UpToDate from "$lib/ui/sections/UpToDate.svelte"
  import Limelight from "$lib/ui/sections/Limelight.svelte"
  import Gamepads from "$lib/ui/sections/Gamepads.svelte"
  import Themes from "$lib/ui/sections/Themes.svelte"
  import Debug from "$lib/ui/sections/Debug.svelte"
</script>

<svelte:head>
  <title>Panels</title>
  <meta
    name="description"
    content={"An intuitive, real-time dashboard for your FTC Robot."}
  />
</svelte:head>

<Hero />
<Plugins />
<Limelight />
<Gamepads />
<Themes />
<Debug />
<UpToDate />
<HeroCenter />
<Footer />
