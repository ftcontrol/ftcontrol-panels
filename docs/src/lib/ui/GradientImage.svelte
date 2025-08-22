<script lang="ts">
    import type {Snippet} from "svelte";

    let {
        desktopType,
        mobileType,
        children
    }: {
        desktopType?: "top" | "bottom" | "left" | "right",
        mobileType?: "top" | "bottom" | "left" | "right",
        children?: Snippet
    } = $props();
</script>
<div
        class="{desktopType ? `desktop-gradient-${desktopType}` : ''} {mobileType ? `mobile-gradient-${mobileType}` : ''}
"
>
    {@render children?.()}
</div>

<style>
    div {
        position: relative;
        display: inline-block;

        width: 100%;
        height: auto;
    }

    div::after {
        content: "";
        position: absolute;
        inset: 0;
        pointer-events: none;
    }

    .desktop-gradient-top::after {
        background: linear-gradient(to bottom, #0b0d0d 0%, transparent 100%);
    }
    .desktop-gradient-bottom::after {
        background: linear-gradient(to top, #0b0d0d 0%, transparent 100%);
    }
    .desktop-gradient-left::after {
        background: linear-gradient(to right, #0b0d0d 0%, transparent 100%);
    }
    .desktop-gradient-right::after {
        background: linear-gradient(to left, #0b0d0d 0%, transparent 100%);
    }

    [class*="mobile-gradient-"]::after {
        display: none;
    }

    [class*="desktop-gradient-"]::after {
        display: block;
    }

    @media (max-width: 768px) {
        [class*="desktop-gradient-"]::after {
            display: none;
        }

        [class*="mobile-gradient-"]::after {
            display: block;
            background: none;
        }

        .mobile-gradient-top::after {
            background: linear-gradient(to bottom, #0b0d0d 0%, transparent 100%);
        }
        .mobile-gradient-bottom::after {
            background: linear-gradient(to top, #0b0d0d 0%, transparent 100%);
        }
        .mobile-gradient-left::after {
            background: linear-gradient(to right, #0b0d0d 0%, transparent 100%);
        }
        .mobile-gradient-right::after {
            background: linear-gradient(to left, #0b0d0d 0%, transparent 100%);
        }
    }

</style>
