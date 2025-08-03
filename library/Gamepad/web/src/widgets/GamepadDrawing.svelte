<script lang="ts">
  let {
    writeGamepad = $bindable(),
    showGamepad,
  }: {
    writeGamepad: GamepadData
    showGamepad: GamepadData
  } = $props()

  import front from "../controller/front.png"
  import top from "../controller/top.png"

  import type { GamepadData } from "../types"
  import ComplexButton from "./ComplexButton.svelte"
  import SimpleButton from "./SimpleButton.svelte"
  import SimpleStick from "./SimpleStick.svelte"
</script>

<div class="reversed">
  <img src={front} alt="" draggable="false" />
  <SimpleButton
    text={"L1"}
    top={35}
    left={30.5}
    bind:value={writeGamepad.l1}
    bind:shownValue={showGamepad.l1}
  />
  <ComplexButton
    text={"L2"}
    top={46.4}
    left={30.9}
    bind:value={writeGamepad.l2}
    bind:shownValue={showGamepad.l2}
  />
  <SimpleButton
    text={"R1"}
    top={35}
    left={100 - 30.5}
    bind:value={writeGamepad.r1}
    bind:shownValue={showGamepad.r1}
  />
  <ComplexButton
    text={"R2"}
    top={46.4}
    left={100 - 30.9}
    bind:value={writeGamepad.r2}
    bind:shownValue={showGamepad.r2}
  />
</div>
<div class="normal">
  <img src={top} alt="" draggable="false" />
  <SimpleButton
    text={"dpad up"}
    top={25.8}
    left={30}
    bind:value={writeGamepad.dpad_up}
    bind:shownValue={showGamepad.dpad_up}
  />
  <SimpleButton
    text={"dpad down"}
    top={36.9}
    left={30}
    bind:value={writeGamepad.dpad_down}
    bind:shownValue={showGamepad.dpad_down}
  />
  <SimpleButton
    text={"dpad left"}
    top={30.9}
    left={26.4}
    bind:value={writeGamepad.dpad_left}
    bind:shownValue={showGamepad.dpad_left}
  />
  <SimpleButton
    text={"dpad right"}
    top={30.9}
    left={33.4}
    bind:value={writeGamepad.dpad_right}
    bind:shownValue={showGamepad.dpad_right}
  />

  <SimpleButton
    text={"triangle"}
    top={25.8 - 3.1}
    left={100 - 30}
    bind:value={writeGamepad.triangle}
    bind:shownValue={showGamepad.triangle}
  />
  <SimpleButton
    text={"cross"}
    top={36.9 + 2.9}
    left={100 - 30}
    bind:value={writeGamepad.cross}
    bind:shownValue={showGamepad.cross}
  />
  <SimpleButton
    text={"circle"}
    top={30.9 + 0.1}
    left={100 - 26.4 + 1.2}
    bind:value={writeGamepad.circle}
    bind:shownValue={showGamepad.circle}
  />
  <SimpleButton
    text={"square"}
    top={30.9 + 0.1}
    left={100 - 33.4 - 1.6 + 0.3}
    bind:value={writeGamepad.square}
    bind:shownValue={showGamepad.square}
  />

  <SimpleButton
    text={"touchpad"}
    top={23.9}
    left={50}
    bind:value={writeGamepad.touchpad}
    bind:shownValue={showGamepad.touchpad}
  />

  <SimpleButton
    text={"ps"}
    top={47.9}
    left={50}
    bind:value={writeGamepad.ps}
    bind:shownValue={showGamepad.ps}
  />

  <SimpleButton
    text={"share"}
    top={19.7}
    left={37}
    bind:value={writeGamepad.share}
    bind:shownValue={showGamepad.share}
  />
  <SimpleButton
    text={"options"}
    top={19.7}
    left={100 - 37}
    bind:value={writeGamepad.options}
    bind:shownValue={showGamepad.options}
  />

  <SimpleButton
    text={"left stick"}
    top={60}
    left={40}
    bind:value={writeGamepad.leftStick.value}
    bind:shownValue={showGamepad.leftStick.value}
  />
  <SimpleButton
    text={"right stick"}
    top={60}
    left={100 - 40}
    bind:value={writeGamepad.rightStick.value}
    bind:shownValue={showGamepad.rightStick.value}
  />

  <SimpleStick
    text={"left stick"}
    top={45}
    left={40}
    bind:x={writeGamepad.leftStick.x}
    bind:y={writeGamepad.leftStick.y}
    bind:shownX={showGamepad.leftStick.x}
    bind:shownY={showGamepad.leftStick.y}
  />

  <SimpleStick
    text={"right stick"}
    top={45}
    left={100 - 40}
    bind:x={writeGamepad.rightStick.x}
    bind:y={writeGamepad.rightStick.y}
    bind:shownX={showGamepad.rightStick.x}
    bind:shownY={showGamepad.rightStick.y}
  />
</div>

<style>
  div {
    position: relative;
    aspect-ratio: 1920 / 1080;
    height: auto;
  }
  .reversed {
    margin-bottom: -20%;
    margin-top: -15%;
  }
  img {
    width: 100%;
    height: 100%;
    position: absolute;
  }
</style>
