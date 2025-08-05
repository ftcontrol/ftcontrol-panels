<script lang="ts">
    import type {PluginConfig} from "ftc-panels"
    import {Title, Image, Paragraph, Accent} from "ftc-panels/docs"

    let {
        info,
    }: {
        info: PluginConfig
    } = $props()

    import image from "./image.png"

    import Manager from "./../manager"
</script>

<Title>Installation</Title>

{#await Manager.getNewVersion()}
    <Paragraph>Loading</Paragraph>
{:then data}
    <Paragraph>Replace basic Panels Installation with:</Paragraph>
    <Paragraph><Accent>{info.id}:{data}</Accent></Paragraph>
{:catch error}
    <Paragraph>Please check you connection: {error}</Paragraph>
{/await}

<Title>What's FullPanels?</Title>

<Paragraph>
    This plugin conveniently bundles all the essential core plugins, simplifying installation and setup.
</Paragraph>

<Paragraph>
    <ul>
        <li>com.bylazar.battery</li>
        <li>com.bylazar.capture</li>
        <li>com.bylazar.configurables</li>
        <li>com.bylazar.docs</li>
        <li>com.bylazar.exampleplugin</li>
        <li>com.bylazar.field</li>
        <li>com.bylazar.gamepad</li>
        <li>com.bylazar.limelightproxy</li>
        <li>com.bylazar.opmodecontrol</li>
        <li>com.bylazar.telemetry</li>
        <li>com.bylazar.themes</li>
        <li>com.bylazar.utils</li>
    </ul>
</Paragraph>

<Title>Templates</Title>

<Paragraph>
    It also includes core templates alongside the essential plugins, so you don’t have to start from scratch when designing your UI.
</Paragraph>

<Image src={image} alt={"Templates"}/>