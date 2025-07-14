<script lang="ts">
  import { global } from "$lib"
  import { DynamicComponent } from "ftc-panels"

  let {
    pluginID,
    widgetID,
  }: {
    pluginID: string
    widgetID: string
  } = $props()
</script>

{#key `${pluginID}-${widgetID}`}
  <DynamicComponent
    globalSocket={global.socket}
    textContent={global.plugins
      .find((it) => it.details.id == pluginID)
      ?.details.widgets.find((it) => it.name == widgetID)?.textContent}
    id={pluginID}
  />
{/key}
