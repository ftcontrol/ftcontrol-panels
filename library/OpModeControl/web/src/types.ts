export type OpMode = {
  name: string
  group: string
  flavour: "TELEOP" | "AUTONOMOUS"
  source: "ANDROID_STUDIO" | "BLOCKLY" | "ONBOTJAVA" | "EXTERNAL_LIBRARY"
  defaultGroup: string
  autoTransition: string
}
