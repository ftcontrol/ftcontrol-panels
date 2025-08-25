<script lang="ts">
  import type { PluginInfo } from "ftc-panels"
  import {
    Title,
    Paragraph,
    Accent,
    Link,
    ListItem,
    InlineCode,
    CodeBlock,
    AccentedParagraph,
  } from "ftc-panels/docs"
</script>

<Title level={1}>Panels Setup Guide</Title>

<Paragraph>
  This guide helps you integrate <InlineCode>ftcontrol</InlineCode> into your FTC
  Robot Controller.
</Paragraph>

<Title level={2}>Clone FTC Robot Controller</Title>

<Paragraph>Start by cloning the official FTC repository:</Paragraph>

<CodeBlock
  language={"bash"}
  code={"git clone https://github.com/FIRST-Tech-Challenge/FtcRobotController"}
/>

<Title level={2}>[Optional] Enable Kotlin Support</Title>

<AccentedParagraph type={"warn"}>
  You only need this if you want to use Kotlin instead of Java.
</AccentedParagraph>

<Paragraph>Edit TeamCode/build.gradle:</Paragraph>

<CodeBlock
  language={"groovy"}
  code={`
apply plugin: 'org.jetbrains.kotlin.android'// [svp! ++]

android {
    namespace = 'org.firstinspires.ftc.teamcode'

    kotlinOptions {// [svp! ++]
        jvmTarget = '1.8'// [svp! ++]
    }// [svp! ++]
    compileSdk 34// [svp! ++]
    defaultConfig {// [svp! ++]
        minSdk 24// [svp! ++]
    }// [svp! ++]

    packagingOptions {
        jniLibs.useLegacyPackaging true
    }
}
`}
/>

<Title level={2}>Add FTControl to Your Project</Title>

<AccentedParagraph type={"warn"}>
  [if you aren't using Kotlin] Edit TeamCode/build.gradle:
</AccentedParagraph>

<CodeBlock
  language={"groovy"}
  code={`
android {
    namespace = 'org.firstinspires.ftc.teamcode'

    compileSdk 35// [svp! ++]
    defaultConfig {// [svp! ++]
        minSdk 24// [svp! ++]
    }// [svp! ++]

    packagingOptions {
        jniLibs.useLegacyPackaging true
    }
}
`}
/>

<Paragraph>
  In your root build.gradle or settings.gradle, add this Maven repository:
</Paragraph>

<CodeBlock
  language={"groovy"}
  code={`
allprojects {
    repositories {
        mavenCentral()
        google()
        maven {// [svp! ++]
            url = "https://mymaven.bylazar.com/releases"// [svp! ++]
        }// [svp! ++]
    }
}
`}
/>

<Paragraph>
  Then in your module’s build.gradle, add one of the Plugin Presets or
  individual plugins:
</Paragraph>

<CodeBlock
  language={"groovy"}
  code={`
dependencies {
    implementation "com.bylazar:<PRESET>:<VERSION>"// [svp! ++]
}
`}
/>

<Title level={3}>Current official presets</Title>

<Paragraph>
  Currently you can either install <InlineCode
    >com.bylazar:fullpanels</InlineCode
  > or use individual plugins together with <InlineCode
    >com.bylazar:panels</InlineCode
  >. Versions can be found in their docs sections.
</Paragraph>

<Title level={2}>Before updating</Title>

<Paragraph>
  Before updating to the latest version of any plugin, it's essential to check
  the changelog for any breaking changes, bug fixes, or new features.
</Paragraph>
