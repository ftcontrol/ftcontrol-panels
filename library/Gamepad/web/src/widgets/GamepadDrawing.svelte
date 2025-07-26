<script lang="ts">
  let {
    gamepad = $bindable(),
  }: {
    gamepad: Gamepad
  } = $props()

  import front from "../controller/front.png"
  import top from "../controller/top.png"

  import type { Gamepad } from "../types"
  import SimpleButton from "./SimpleButton.svelte"
  import SimpleStick from "./SimpleStick.svelte"
</script>

<div class="reversed">
  <img src={front} alt="" draggable="false" />
  <SimpleButton text={"L1"} top={35} left={30.5} bind:value={gamepad.l1} />
  <SimpleButton text={"L2"} top={46.4} left={30.9} bind:value={gamepad.l2} />
  <SimpleButton
    text={"R1"}
    top={35}
    left={100 - 30.5}
    bind:value={gamepad.r1}
  />
  <SimpleButton
    text={"R2"}
    top={46.4}
    left={100 - 30.9}
    bind:value={gamepad.r2}
  />
</div>
<div class="normal">
  <img src={top} alt="" draggable="false" />
  <SimpleButton
    text={"dpad up"}
    top={25.8}
    left={30}
    bind:value={gamepad.dpad_up}
  />
  <SimpleButton
    text={"dpad down"}
    top={36.9}
    left={30}
    bind:value={gamepad.dpad_down}
  />
  <SimpleButton
    text={"dpad left"}
    top={30.9}
    left={26.4}
    bind:value={gamepad.dpad_left}
  />
  <SimpleButton
    text={"dpad right"}
    top={30.9}
    left={33.4}
    bind:value={gamepad.dpad_right}
  />

  <SimpleButton
    text={"triangle"}
    top={25.8 - 3.1}
    left={100 - 30}
    bind:value={gamepad.triangle}
  />
  <SimpleButton
    text={"cross"}
    top={36.9 + 2.9}
    left={100 - 30}
    bind:value={gamepad.cross}
  />
  <SimpleButton
    text={"circle"}
    top={30.9 + 0.1}
    left={100 - 26.4 + 1.2}
    bind:value={gamepad.circle}
  />
  <SimpleButton
    text={"square"}
    top={30.9 + 0.1}
    left={100 - 33.4 - 1.6 + 0.3}
    bind:value={gamepad.square}
  />

  <SimpleButton
    text={"touchpad"}
    top={23.9}
    left={50}
    bind:value={gamepad.touchpad}
  />

  <SimpleButton text={"ps"} top={47.9} left={50} bind:value={gamepad.ps} />

  <SimpleButton
    text={"share"}
    top={19.7}
    left={37}
    bind:value={gamepad.share}
  />
  <SimpleButton
    text={"options"}
    top={19.7}
    left={100 - 37}
    bind:value={gamepad.options}
  />

  <SimpleButton
    text={"left stick"}
    top={60}
    left={40}
    bind:value={gamepad.leftStick.value}
  />
  <SimpleButton
    text={"right stick"}
    top={60}
    left={100 - 40}
    bind:value={gamepad.rightStick.value}
  />

  <SimpleStick
    text={"left stick"}
    top={45}
    left={40}
    bind:x={gamepad.leftStick.x}
    bind:y={gamepad.leftStick.y}
  />

  <SimpleStick
    text={"right stick"}
    top={45}
    left={100 - 40}
    bind:x={gamepad.rightStick.x}
    bind:y={gamepad.rightStick.y}
  />
</div>

<style>
  div {
    position: relative;
    aspect-ratio: 1920 / 1080;
    height: auto;
  }
  .normal {
  }
  .reversed {
    /* transform: scaleX(-1); */
    margin-bottom: -20%;
    margin-top: -15%;
  }
  img {
    width: 100%;
    height: 100%;
    position: absolute;
  }
</style>
