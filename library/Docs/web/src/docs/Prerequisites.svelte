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
    UnorderedList,
  } from "ftc-panels/docs"
</script>

<Title level={1}>Panels Setup Guide</Title>

<Paragraph>You can get started by using the Quickstarts:</Paragraph>

<UnorderedList>
  <ListItem>
    <Link href="https://github.com/ftcontrol/Panels-Quickstart-Kotlin"
      >Panels Kotlin Quickstart</Link
    >
  </ListItem>
  <ListItem>
    <Link href="https://github.com/ftcontrol/Panels-Quickstart-Java"
      >Panels Java Quickstart</Link
    >
  </ListItem>
</UnorderedList>

<Title level={1}>Panels Manual Setup Guide</Title>

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

<AccentedParagraph tone={"warn"}>
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

    packagingOptions {
        jniLibs.useLegacyPackaging true
    }
}
`}
/>

<Paragraph>And in the root build.gradle:</Paragraph>

<CodeBlock
  language={"groovy"}
  code={`
buildscript {
    ...
    dependencies {
        classpath "org.jetbrains.kotlin:kotlin-gradle-plugin:2.0.0"// [svp! ++]
    }
}
`}
/>

<Title level={2}>Add FTControl to Your Project</Title>

<Paragraph>
  In your root build.gradle or settings.gradle, add these Maven repositories:
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
        maven {// [svp! ++]
            url = "https://repo.dairy.foundation/releases"// [svp! ++]
        }// [svp! ++]
    }
}
`}
/>

<Paragraph>
  The Dairy repository is required to resolve Sloth, which Panels uses for
  scanning as of SDK 12.
</Paragraph>

<Paragraph>
  Then in your module’s build.gradle, pick <Accent>Option A</Accent> or{" "}
  <Accent>Option B</Accent> below.
</Paragraph>

<Title level={3}>Option A - Full Panels (recommended)</Title>

<Paragraph>
  A single dependency that includes the Panels core and every official plugin.
  Best if you want everything working with no extra setup.
</Paragraph>

<CodeBlock
  language={"groovy"}
  code={`
dependencies {
    implementation "com.bylazar:fullpanels:<VERSION>"// [svp! ++]
}
`}
/>

<Title level={3}>Option B - Individual plugins</Title>

<Paragraph>
  Add the Panels core (<InlineCode>com.bylazar:panels</InlineCode>) plus only
  the plugins you actually use. Each plugin's current <InlineCode
    >&lt;VERSION&gt;</InlineCode
  > is listed on its own docs page.
</Paragraph>

<CodeBlock
  language={"groovy"}
  code={`
dependencies {
    implementation "com.bylazar:panels:<VERSION>" // core dashboard (required)// [svp! ++]
    implementation "com.bylazar:field:<VERSION>" // Field widget// [svp! ++]
    implementation "com.bylazar:gamepad:<VERSION>" // Gamepad widgets// [svp! ++]
    implementation "com.bylazar:telemetry:<VERSION>" // Telemetry widget// [svp! ++]
}
`}
/>

<Title level={3}>Available official plugins</Title>

<Paragraph>
  Use these artifact names with <InlineCode>com.bylazar:&lt;name&gt;:&lt;VERSION&gt;</InlineCode>:
</Paragraph>

<UnorderedList>
  <ListItem>
    <InlineCode>panels</InlineCode> - core dashboard (always required with
    Option B)
  </ListItem>
  <ListItem>
    <InlineCode>opmodecontrol</InlineCode> - select and control OpModes
  </ListItem>
  <ListItem>
    <InlineCode>telemetry</InlineCode> - text-based telemetry
  </ListItem>
  <ListItem>
    <InlineCode>configurables</InlineCode> - tune <InlineCode>@Configurable</InlineCode>{" "}
    variables live
  </ListItem>
  <ListItem>
    <InlineCode>field</InlineCode> - draw on the field view
  </ListItem>
  <ListItem>
    <InlineCode>gamepad</InlineCode> - on-screen gamepad views
  </ListItem>
  <ListItem>
    <InlineCode>graph</InlineCode> - plot values over time
  </ListItem>
  <ListItem>
    <InlineCode>capture</InlineCode> - record and replay match data
  </ListItem>
  <ListItem>
    <InlineCode>limelightproxy</InlineCode> - Limelight 3A support
  </ListItem>
  <ListItem>
    <InlineCode>camerastream</InlineCode> - stream camera frames
  </ListItem>
  <ListItem>
    <InlineCode>themes</InlineCode> - custom theming
  </ListItem>
  <ListItem>
    <InlineCode>lights</InlineCode> - Gobilda light-based telemetry
  </ListItem>
  <ListItem>
    <InlineCode>battery</InlineCode> - battery information
  </ListItem>
  <ListItem>
    <InlineCode>pinger</InlineCode> - latency test
  </ListItem>
  <ListItem>
    <InlineCode>utils</InlineCode> - shared utilities
  </ListItem>
</UnorderedList>

<AccentedParagraph tone={"warn"}>
  Do not mix <InlineCode>fullpanels</InlineCode> with the individual plugins;
  choose one option or the other to avoid duplicate classes.
</AccentedParagraph>

<Title level={2}>Before updating</Title>

<Paragraph>
  Follow these steps before updating to avoid stale files and dependency
  conflicts:
</Paragraph>

<UnorderedList>
  <ListItem>Commit or back up your code first.</ListItem>
  <ListItem>Update the FTC SDK to <InlineCode>12.0.0</InlineCode>.</ListItem>
  <ListItem>
    Make sure both the Bylazar and Dairy Maven repositories are present (see
    above).
  </ListItem>
  <ListItem>
    Run a clean build, or delete your project's <InlineCode>build</InlineCode>{" "}
    folders, so old Panels assets are not reused.
  </ListItem>
  <ListItem>
    Check each plugin's changelog for breaking changes, bug fixes, or new
    features.
  </ListItem>
  <ListItem>
    If you were using the separate Sloth Panels artifacts (<InlineCode
      >com.bylazar.sloth:*</InlineCode
    >), switch back to the official <InlineCode>com.bylazar:*</InlineCode>{" "}
    artifacts, which now support Sloth directly.
  </ListItem>
</UnorderedList>
