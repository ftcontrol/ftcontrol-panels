export type OpMode = {
  name: string
  group: string
  description: string
  flavour: "TELEOP" | "AUTONOMOUS" | "UTILITY"
  source: "ANDROID_STUDIO" | "BLOCKLY" | "ONBOTJAVA" | "EXTERNAL_LIBRARY"
  defaultGroup: string
  autoTransition: string
}
