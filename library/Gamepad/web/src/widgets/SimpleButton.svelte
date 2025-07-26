<script lang="ts">
  let {
    text,
    top,
    left,
    value = $bindable(),
  }: {
    text: string
    top: number
    left: number
    value: boolean
  } = $props()
</script>

<button
  onmousedown={() => {
    value = true
  }}
  onmouseup={() => {
    value = false
  }}
  onmouseleave={() => {
    value = false
  }}
  class="overlay"
  style="--value: {value ? 1 : 0};--top:{top}%;--left:{left}%;"
  aria-label={text}
>
</button>

<style>
  .overlay {
    all: unset;
    cursor: pointer;
    --value: 0;
    opacity: calc(0.5 + var(--value) / 2);
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
