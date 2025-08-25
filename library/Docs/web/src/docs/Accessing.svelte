<script lang="ts">
  import type { PluginInfo } from "ftc-panels"
  import {
    Title,
    Paragraph,
    Accent,
    Link,
    ListItem,
    OrderedList,
    UnorderedList,
  } from "ftc-panels/docs"
  let {
    info,
  }: {
    info: PluginInfo
  } = $props()
</script>

<Title level={1}>Accessing Panels web UI</Title>

<OrderedList>
  <ListItem>
    Connect to the WiFi Network:
    <UnorderedList>
      <ListItem>
        Join the WiFi network being broadcast by the Robot Controller (RC).
      </ListItem>
      <ListItem>
        You can find the WiFi passphrase in the Program and Manage menu.
      </ListItem>
    </UnorderedList>
  </ListItem>
  <ListItem>
    Open the webpage:
    <UnorderedList>
      <ListItem>
        If you're using a Phone RC, go to:
        <Link href="http://192.168.49.1:8001">192.168.49.1:8001</Link>
      </ListItem>
      <ListItem>
        If you're using a Control Hub, go to:
        <Link href="http://192.168.43.1:8001">192.168.43.1:8001</Link>
      </ListItem>
    </UnorderedList>
  </ListItem>
</OrderedList>

<Title level={1}>Quick Tip</Title>

<Paragraph>
  To scroll horizontally within a container, hold down <Accent>Shift</Accent> and
  scroll using the mouse wheel.
</Paragraph>
