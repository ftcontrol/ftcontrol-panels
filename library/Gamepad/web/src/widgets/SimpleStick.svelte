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
    x = Math.max(-12, Math.min(startX - e.clientX, 12)) / 12
    y = Math.max(-12, Math.min(startY - e.clientY, 12)) / 12
  }
</script>

<button
  onmousedown={handleMouseDown}
  class:selected={isMoving}
  style="--offsetX:{x}px;--offsetY:{y}px;--top:{top}%;--left:{left}%;"
  aria-label={text}
></button>

<style>
  button {
    all: unset;
    cursor: pointer;
    opacity: 0.5;
    position: absolute;

    background-color: var(--primary);
    transform: translateX(-50%);
    width: 6.5%;
    aspect-ratio: 1 / 1;
    border-radius: 69vw;

    --offsetX: 0;
    --offsetY: 0;

    top: calc(var(--top) - var(--offsetY) * 12);
    left: calc(var(--left) - var(--offsetX) * 12);
  }
  button.selected {
    opacity: 1;
  }
</style>
