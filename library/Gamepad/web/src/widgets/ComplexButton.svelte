<script lang="ts">
  let {
    text,
    top,
    left,
    value = $bindable(),
    shownValue = $bindable(),
  }: {
    text: string
    top: number
    left: number
    value: number
    shownValue: number
  } = $props()
</script>

<button
  onmousedown={() => {
    value = 1.0
  }}
  onmouseup={() => {
    value = 0.0
  }}
  onmouseleave={() => {
    value = 0.0
  }}
  style="--value: {Math.max(value, shownValue)};--top:{top}%;--left:{left}%;"
  aria-label={text}
>
</button>

<style>
  button {
    all: unset;
    cursor: pointer;
    --value: 0;
    opacity: calc(0.25 + var(--value) / 2);
    position: absolute;
    top: var(--top);
    left: var(--left);
    background-color: var(--primary);
    transform: translateX(-50%);
    width: 4%;
    aspect-ratio: 1 / 1;
    border-radius: 69vw;
  }
</style>
