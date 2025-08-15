<script lang="ts">
    let {
        startValue = $bindable(),
        newValue = $bindable(),
        value = $bindable(),
        isValid = $bindable(),
        type = "",
        validate = (value: string) => true,
        submit = () => {},
    }: {
        startValue: string
        newValue: string
        value: string
        isValid: Boolean
        type?: string
        validate: (value: string) => boolean
        submit?: (e: SubmitEvent) => void
    } = $props()

    let inputEl: HTMLInputElement

    let selStart = 0
    let selEnd = 0
    let selDir: "forward" | "backward" | "none" = "none"

    function rememberSelection() {
        if (!inputEl) return
        selStart = inputEl.selectionStart ?? 0
        selEnd = inputEl.selectionEnd ?? selStart
        selDir = inputEl.selectionDirection ?? "none"
    }

    function restoreSelection() {
        if (!inputEl) return
        inputEl.focus()
        requestAnimationFrame(() => {
            inputEl.setSelectionRange(selStart, selEnd, selDir)
        })
    }

    $effect(() => {
        isValid = validate(value)
        if (value == newValue) return
        if (!isValid) return
        newValue = value
    })
</script>

<form
        onsubmit={(e) => {
    e.preventDefault()
    rememberSelection()
    submit(e)
    restoreSelection()
  }}
>
    <input
            type="text"
            class:invalid={!isValid}
            placeholder={startValue}
            bind:value
            bind:this={inputEl}
    />
    {#if type != ""}
        <div class="text">
            <div class="bg"></div>
            <span class="base">{type}</span>
            <span class="shown">{type}</span>
        </div>
    {/if}
</form>

<style>
    form {
        position: relative;
        border: 1px solid currentColor;
        border-radius: 0.25rem;
        margin-top: 0.5rem;
        max-width: 120px;
    }

    input {
        all: unset;
        border: none;
        padding: 0.25em;
        position: relative;
        color: inherit;
        background-color: transparent;
    }
    .text {
        text-transform: uppercase;
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
    }

    input.invalid {
        opacity: 0.5;
    }
</style>
