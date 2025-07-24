<script lang="ts">
  let {
    gamepad,
  }: {
    gamepad: Gamepad
  } = $props()

  import front from "../controller/front.png"
  import top from "../controller/top.png"
  import l1 from "../controller/l1.png"
  import l2 from "../controller/l2.png"
  import r1 from "../controller/r1.png"
  import r2 from "../controller/r2.png"
  import left from "../controller/left.png"
  import right from "../controller/right.png"
  import left2 from "../controller/left2.png"
  import right2 from "../controller/right2.png"

  import cross from "../controller/cross.png"
  import circle from "../controller/circle.png"
  import square from "../controller/square.png"
  import triangle from "../controller/triangle.png"

  import dpad_up from "../controller/dpad_up.png"
  import dpad_left from "../controller/dpad_left.png"
  import dpad_down from "../controller/dpad_down.png"
  import dpad_right from "../controller/dpad_right.png"

  import touchpad from "../controller/touchpad.png"
  import options from "../controller/options.png"
  import share from "../controller/share.png"
  import ps from "../controller/ps.png"

  import type { Gamepad } from "../types"
</script>

<div class="photo reversed">
  <img src={front} alt="" />
  <img class="overlay" src={l1} alt="" style="--value:{gamepad.l1};" />
  <img class="overlay" src={l2} alt="" style="--value:{gamepad.l2};" />
  <img class="overlay" src={r1} alt="" style="--value:{gamepad.r1};" />
  <img class="overlay" src={r2} alt="" style="--value:{gamepad.r2};" />
</div>
<div class="photo">
  <img src={top} alt="" />
  <img
    class="stick"
    src={left}
    alt=""
    style="--offsetX:{gamepad.leftStick.x};--offsetY:{gamepad.leftStick.y};"
  />
  <img
    class="stick"
    src={right}
    alt=""
    style="--offsetX:{gamepad.rightStick.x};--offsetY:{gamepad.rightStick.y};"
  />
  <img
    class="stick overlay"
    src={left2}
    alt=""
    style="--offsetX:{gamepad.leftStick.x};--offsetY:{gamepad.leftStick
      .y};--value:{gamepad.leftStick.value};"
  />
  <img
    class="stick overlay"
    src={right2}
    alt=""
    style="--offsetX:{gamepad.rightStick.x};--offsetY:{gamepad.rightStick
      .y};--value:{gamepad.rightStick.value};"
  />

  <img
    class="overlay"
    src={touchpad}
    alt=""
    style="--value:{gamepad.touchpad};"
  />
  <img
    class="overlay"
    src={options}
    alt=""
    style="--value:{gamepad.options};"
  />
  <img class="overlay" src={share} alt="" style="--value:{gamepad.share};" />
  <img class="overlay" src={ps} alt="" style="--value:{gamepad.ps};" />

  <img
    class="overlay"
    src={dpad_up}
    alt=""
    style="--value:{gamepad.dpad_up};"
  />
  <img
    class="overlay"
    src={dpad_down}
    alt=""
    style="--value:{gamepad.dpad_down};"
  />
  <img
    class="overlay"
    src={dpad_left}
    alt=""
    style="--value:{gamepad.dpad_left};"
  />
  <img
    class="overlay"
    src={dpad_right}
    alt=""
    style="--value:{gamepad.dpad_left};"
  />

  <img class="overlay" src={cross} alt="" style="--value:{gamepad.cross};" />
  <img class="overlay" src={circle} alt="" style="--value:{gamepad.circle};" />
  <img class="overlay" src={square} alt="" style="--value:{gamepad.square};" />
  <img
    class="overlay"
    src={triangle}
    alt=""
    style="--value:{gamepad.triangle};"
  />
</div>

<style>
  .photo.reversed {
    transform: scaleX(-1) translateX(calc(1920 / 1080 * -1 * 200px));
    height: 120px;
  }
  .photo {
    height: 200px;
    position: relative;
    width: fit-content;
  }
  img {
    height: 200px;
    aspect-ratio: 1920 / 1080;
    position: absolute;
  }
  .stick {
    --offsetX: 0;
    --offsetY: 0;

    left: calc(var(--offsetX) * 10px);
    top: calc(var(--offsetY) * 10px);
  }
  .overlay {
    --value: 0;
    opacity: var(--value);
  }
</style>
