<script lang="ts">
  let {
    text,
    top,
    left,
    x = $bindable(),
    y = $bindable(),
  }: {
    text: string
    top: number
    left: number
    x: number
    y: number
  } = $props()

  let isMoving = $state(false)
  let startX = $state(0)
  let startY = $state(0)

  function handleMouseDown(e: MouseEvent) {
    isMoving = true
    startX = e.clientX
    startY = e.clientY

    window.addEventListener("mouseup", handleMouseUp)
    window.addEventListener("mousemove", handleMouseMove)
  }

  function handleMouseUp() {
    isMoving = false
    x = 0
    y = 0
    startX = 0
    startY = 0

    window.removeEventListener("mouseup", handleMouseUp)
    window.removeEventListener("mousemove", handleMouseMove)
  }

  function handleMouseMove(e: MouseEvent) {
    if (!isMoving) return
    x = Math.max(-24, Math.min(startX - e.clientX, 24)) / 24
    y = Math.max(-24, Math.min(startY - e.clientY, 24)) / 24
  }
</script>

<button
  onmousedown={handleMouseDown}
  class="overlay"
  style="--offsetX:{x}px;--offsetY:{y}px;--top:{top}%;--left:{left}%;"
  aria-label={text}
></button>

<style>
  .overlay {
    all: unset;
    cursor: pointer;
    --value: 0;
    opacity: calc(0.5 + var(--value) / 2);
    position: absolute;

    background-color: var(--primary);
    transform: translateX(-50%);
    width: 6.5%;
    aspect-ratio: 1 / 1;
    border-radius: 69vw;

    --offsetX: 0;
    --offsetY: 0;

    top: calc(var(--top) - var(--offsetY) * 24);
    left: calc(var(--left) - var(--offsetX) * 24);
  }
</style>
