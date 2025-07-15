<script lang="ts">
  let { isOpen }: { isOpen: boolean } = $props()
</script>

<svg
  class:isOpen
  width="100%"
  height="100%"
  viewBox="0 0 9 6"
  version="1.1"
  xmlns="http://www.w3.org/2000/svg"
  xmlns:xlink="http://www.w3.org/1999/xlink"
  xml:space="preserve"
  style="fill-rule:evenodd;clip-rule:evenodd;stroke-linejoin:round;stroke-miterlimit:2;"
>
  <g transform="matrix(1,0,0,1,-659.041,-27.7997)">
    <g transform="matrix(0.160417,0,0,0.390741,461.595,0.595914)">
      <g transform="matrix(7.23921,0,0,3.09534,-1868.27,968.028)">
        <path
          d="M428.099,-289.592L428.807,-290.245C428.807,-290.245 430.959,-288.094 431.667,-287.385C431.725,-287.327 431.806,-287.294 431.89,-287.294C431.974,-287.294 432.055,-287.327 432.113,-287.385C432.817,-288.09 434.948,-290.22 434.948,-290.22L435.656,-289.568L432.244,-286.156C432.151,-286.063 432.024,-286.011 431.89,-286.011C431.756,-286.011 431.629,-286.063 431.536,-286.156L428.099,-289.592Z"
        />
      </g>
    </g>
  </g>
</svg>

<style>
  svg {
    height: 8px;
    width: fit-content;
  }
  path {
    fill: currentColor;
  }
  svg {
    rotate: 90deg;
    transition: rotate 250ms;
  }
  svg.isOpen {
    rotate: 0deg;
  }
</style>
