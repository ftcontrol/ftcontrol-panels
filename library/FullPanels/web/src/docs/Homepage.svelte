<script lang="ts">
    import type {PluginConfig} from "ftc-panels"
    import {Title, Image, Paragraph, Accent, OrderedList, ListItem} from "ftc-panels/docs"

    let {
        info,
    }: {
        info: PluginConfig
    } = $props()

    import image from "./image.png"

    import Manager from "./../manager"
</script>

<Title level={1}>Installation</Title>

{#await Manager.getNewVersion()}
    <Paragraph>Loading</Paragraph>
{:then data}
    <Paragraph>Replace basic Panels Installation with:</Paragraph>
    <Paragraph><Accent>{info.id}:{data}</Accent></Paragraph>
{:catch error}
    <Paragraph>Please check you connection: {error}</Paragraph>
{/await}

<Title  level={1}>What's FullPanels?</Title>

<Paragraph>
    This plugin conveniently bundles all the essential core plugins, simplifying installation and setup.
</Paragraph>

<OrderedList>
    <ListItem>com.bylazar.battery</ListItem>
    <ListItem>com.bylazar.capture</ListItem>
    <ListItem>com.bylazar.configurables</ListItem>
    <ListItem>com.bylazar.docs</ListItem>
    <ListItem>com.bylazar.exampleplugin</ListItem>
    <ListItem>com.bylazar.field</ListItem>
    <ListItem>com.bylazar.gamepad</ListItem>
    <ListItem>com.bylazar.limelightproxy</ListItem>
    <ListItem>com.bylazar.opmodecontrol</ListItem>
    <ListItem>com.bylazar.telemetry</ListItem>
    <ListItem>com.bylazar.themes</ListItem>
    <ListItem>com.bylazar.utils</ListItem>
    <ListItem>com.bylazar.pinger</ListItem>
</OrderedList>

<Title level={1}>Templates</Title>

<Paragraph>
    It also includes core templates alongside the essential plugins, so you don’t have to start from scratch when designing your UI.
</Paragraph>

<Image src={image} alt={"Templates"}/>