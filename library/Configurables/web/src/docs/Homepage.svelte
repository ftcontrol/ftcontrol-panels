<script lang="ts">
  import type { PluginConfig } from "ftc-panels"
  import {
    Title,
    Paragraph,
    CodeBlock,
    InlineCode,
    UnorderedList,
    ListItem,
    OrderedList,
    AutoplayVideo,
  } from "ftc-panels/docs"

  let {
    info,
  }: {
    info: PluginConfig
  } = $props()

  import video from "./video.mp4"
  import javaRobotCOnstants from "@examples/configurables/JavaRobotConstants.java?raw"
  import kotlinRobotCOnstants from "@examples/configurables/KotlinRobotConstants.kt?raw"
</script>

<Title level={1}>{info.name}</Title>

<AutoplayVideo src={video} alt={"Using Configurables"} />

<Paragraph>
  Configurables are runtime-modifiable variables that make testing, tuning, and
  debugging robot behavior easier without needing to recompile or reupload your
  code. You can think of them as live sliders or inputs exposed to your Panels
  Dashboard.
</Paragraph>

<Title level={2}>What is a Configurable?</Title>

<Paragraph>
  A Configurable is a <InlineCode>public static (Java)</InlineCode> or
  <InlineCode>@JvmField @JvmStatic (Kotlin)</InlineCode> variable marked in a class
  annotated with <InlineCode>@Configurable</InlineCode>. These variables are
  exposed in the Panels UI and can be changed while the robot is running.
</Paragraph>

<Paragraph>Useful for things like:</Paragraph>
<UnorderedList>
  <ListItem>PID tuning</ListItem>
  <ListItem>Autonomous positions and paths</ListItem>
  <ListItem>Behavioral toggles (e.g., enabling/disabling subsystems)</ListItem>
  <ListItem>Testing new constants on-the-fly</ListItem>
</UnorderedList>

<Title level={2}>Annotating Classes with @Configurable</Title>

<Paragraph>To make a class’s fields configurable:</Paragraph>
<CodeBlock code={javaRobotCOnstants} language="java"></CodeBlock>
<Paragraph>In Kotlin:</Paragraph>
<CodeBlock code={kotlinRobotCOnstants} language="kotlin"></CodeBlock>

<Title level={2}>Real Examples</Title>
<Paragraph
  >You can define configurables in many ways. Let's explore the styles
  supported.</Paragraph
>

<OrderedList>
  <ListItem>
    <Paragraph>Java Class</Paragraph>
    <CodeBlock
      language={"java"}
      code={`
@Configurable
public class TestJavaClass {
    public static int testInt = 1;
    public static boolean testBoolean = false;
    public static String testString = "Hello";
}
    `}
    ></CodeBlock>
  </ListItem>
  <ListItem>
    <Paragraph>Kotlin Class</Paragraph>
    <CodeBlock
      language={"kotlin"}
      code={`
@Configurable
class TestKotlinClass {
    companion object {
        @JvmField
        var testDouble: Double = 1.0
    }
}
    `}
    ></CodeBlock>
  </ListItem>
  <ListItem>
    <Paragraph>Kotlin Object</Paragraph>
    <CodeBlock
      language={"kotlin"}
      code={`
@Configurable
object TestKotlinObject {
    @JvmField
    var testFloat: Float = 1.0f
}
    `}
    ></CodeBlock>
  </ListItem>
</OrderedList>

<Title level={2}>Supported Types</Title>
<Paragraph>All fields must be:</Paragraph>

<UnorderedList>
  <ListItem>Java: public static</ListItem>
  <ListItem
    >Kotlin: @JvmField, and @JvmStatic (items inside an object or companion
    object ar static by default)</ListItem
  >
</UnorderedList>

<Paragraph>You can use:</Paragraph>

<UnorderedList>
  <ListItem>Primitive types: int, double, boolean, etc.</ListItem>
  <ListItem>Enums</ListItem>
  <ListItem>Strings</ListItem>
  <ListItem>Arrays and Lists</ListItem>
  <ListItem>Maps</ListItem>
  <ListItem>Custom types (detected automatically)</ListItem>
  <ListItem>Generic types (via @GenericValue)</ListItem>
</UnorderedList>

<Title level={2}>Custom Types</Title>
<Paragraph>Custom objects are detected automatically.</Paragraph>
<CodeBlock
  language={"java"}
  code={`
public class CustomType {
    public final int id;
    public final String name;
    
    public CustomType(int id, String name) {
        this.id = id;
        this.name = name;
    }
}
    `}
></CodeBlock>

<Paragraph>You can nest custom types, too:</Paragraph>
<CodeBlock
  language={"java"}
  code={`
public class NestedType {
    public final CustomType child;

    public NestedType(CustomType child) {
        this.child = child;
    }
}
    `}
></CodeBlock>

<Title level={2}>Generic Types with @GenericValue</Title>
<Paragraph>If you have a generic wrapper class:</Paragraph>
<CodeBlock
  language={"java"}
  code={`
public class TParamClass<T> {
    public final T value;
    public TParamClass(T value) { this.value = value; }
}
    `}
></CodeBlock>
<Paragraph>Then mark the instance with its type:</Paragraph>
<CodeBlock
  language={"java"}
  code={`
@GenericValue(tParam = Integer.class)
public static TParamClass<Integer> testGeneric = new TParamClass<>(42);
    `}
></CodeBlock>
<Paragraph>Kotlin:</Paragraph>
<CodeBlock
  language={"kotlin"}
  code={`
@GenericValue(Int::class)
@JvmField
var testTParamClass = TParamClass(1)
`}
></CodeBlock>
