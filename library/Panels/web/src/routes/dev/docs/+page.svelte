<script lang="ts">
    import {
        Title,
        Paragraph,
        Link,
        InlineCode,
        Accent,
        AccentedParagraph,
        Separator,
        OrderedList,
        ListItem,
        UnorderedList,
        Image,
        Video,
        Table,
        CodeBlock,
    } from "ftc-panels/docs"
</script>

<Title level={1} text="Docs Components Demo"/>

<Paragraph>
    This is a
    <InlineCode>Paragraph</InlineCode>
    with an inline
    <Accent>accent</Accent>
    and an
    <Link href="https://svelte.dev" newTab={true}>external link</Link>
    .
</Paragraph>


<AccentedParagraph tone="info">
    Heads up: You can highlight key info using an accented paragraph.
</AccentedParagraph>

<Separator/>

<Title level={2} text="Lists"/>
<OrderedList start={3}>
    <ListItem>
        <Paragraph>Numbered item with a paragraph inside.</Paragraph>
    </ListItem>
    <ListItem>
        <Paragraph>Another item.</Paragraph>
    </ListItem>
</OrderedList>

<UnorderedList>
    <ListItem>
        <Paragraph>Bullet item</Paragraph>
    </ListItem>
    <ListItem>
        <Paragraph>Bullet item 2</Paragraph>
    </ListItem>
</UnorderedList>

<Separator/>

<Title level={2} text="Images & Video"/>
<Image src="https://picsum.photos/800/360" alt="Random" caption="An example image"/>

<Video src="https://interactive-examples.mdn.mozilla.net/media/cc0-videos/flower.mp4" autoplay={true} controls={false}
       muted={true} loop={true}/>
<Video src="https://interactive-examples.mdn.mozilla.net/media/cc0-videos/flower.mp4" autoplay={false} controls={true}
       muted={true}/>


<Separator/>

<Title level={2} text="Tables"/>

<Table
        hasHeader={true}
        data={[
    ["Nume", "Varsta", "Oras"],
    ["Ana", 18, "Cluj"],
    ["Ion", 7, "Galati"],
  ]}
/>

<Title level={2} text="Code"/>

<CodeBlock
        code={"const lazar = require('lazar');"}
        lang={"js"}
/>