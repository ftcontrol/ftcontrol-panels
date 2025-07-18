<script lang="ts">
  let {
    value,
    type,
  }: {
    value: string
    type: string
  } = $props()
</script>

<div class="container">
  <div class="input">{value}</div>
  <div class="text">
    <div class="bg"></div>
    <span class="base">{type}</span>
    <span class="shown">{type}</span>
  </div>
</div>

<style>
  div.container {
    position: relative;
    border: 1px solid currentColor;
    border-radius: 0.25rem;
    max-width: 120px;
  }

  .input {
    padding: 0.25em;
    position: relative;
    color: inherit;
    background-color: transparent;
  }
  .text {
    position: absolute;
    top: -45%;
    left: 0.5rem;
    color: inherit;
  }
  .bg {
    background-color: var(--bgMedium);
    position: absolute;
    width: 100%;
    top: 55%;
    height: 15%;
    left: 0;
  }
  span {
    padding-inline: 0.15rem;
  }
  .base {
    opacity: 0;
  }
  .shown {
    position: absolute;
    left: 0;
    top: 0;
    opacity: 0.5;
  }
</style>
