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

  import bad from "@examples/configurables/copysemantics/bad/Example.kt?raw"
  import fixed from "@examples/configurables/copysemantics/fixed/Example.kt?raw"
  import lambda from "@examples/configurables/copysemantics/lambda/Example.kt?raw"

  import javaClass from "@examples/configurables/TestJavaClass.java?raw"
  import kotlinClass from "@examples/configurables/TestKotlinClass.kt?raw"
  import kotlinObject from "@examples/configurables/TestKotlinObject.kt?raw"
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

<Title level={1}>The Common Pitfall: Copy Semantics</Title>
<Paragraph
  >In Kotlin and Java, when you assign an object to a new variable, you copy the
  reference to the object, not the object itself. This means both variables
  point to the same memory location. However, when dealing with primitives (like
  int, double, etc.) or immutable objects, the value is copied directly. This
  can lead to unexpected behavior if one variable is modified, as the other
  variable won't reflect the change.</Paragraph
>

<Title level={1}>Example: Implementing a configurable Claw System</Title>
<Title level={2}>Bad Example</Title>
<CodeBlock code={bad} language="kotlin"></CodeBlock>
<Paragraph>
  In this example, the offset variable is passed as a value to the RobotClaw
  constructor during initialization. Since Double is a primitive type, its value
  is copied into the RobotClaw object. If offset is changed later during the
  OpMode (e.g., via Panels interface), the RobotClaw instance will not reflect
  the updated value. This leads to inconsistent behavior because the RobotClaw
  continues to use the old offset value.
</Paragraph>

<Title level={2}>Solution 1: Use a Mutable Shared State</Title>
<Paragraph>
  One way to solve this issue is to make the clawOffset a mutable shared state
  that can be updated dynamically. Instead of passing the value directly, we
  store it in a shared location (e.g., a companion object) and reference it
  within the RobotClaw.
</Paragraph>
<CodeBlock code={fixed} language="kotlin"></CodeBlock>

<Title level={2}>Solution 2: Use a Lambda Function</Title>
<Paragraph>
  Another elegant solution is to pass a lambda function that retrieves the
  current value of offset. This ensures that the RobotClaw always queries the
  latest value of offset whenever it needs it.
</Paragraph>
<CodeBlock code={lambda} language="kotlin"></CodeBlock>

<Title level={1}>Examples</Title>

<Title level={2}>Kotlin Object</Title>
<CodeBlock code={kotlinObject} language="kotlin"></CodeBlock>

<Title level={2}>Kotlin Class</Title>
<CodeBlock code={kotlinClass} language="kotlin"></CodeBlock>

<Title level={2}>Java Class</Title>
<CodeBlock code={javaClass} language="java"></CodeBlock>
