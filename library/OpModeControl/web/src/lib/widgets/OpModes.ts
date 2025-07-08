import { mount } from "svelte"
import OpModes from "./OpModes.svelte"

export default function load(target: HTMLElement, props: any) {
  return mount(OpModes, {
    target: target,
    props: props,
  })
}
