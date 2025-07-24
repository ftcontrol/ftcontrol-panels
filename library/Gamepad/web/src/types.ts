export type Gamepad = {
  l1: boolean
  l2: boolean
  r1: boolean
  r2: boolean
  leftStick: {
    x: number
    y: number
    value: boolean
  }
  rightStick: {
    x: number
    y: number
    value: boolean
  }
  cross: boolean
  circle: boolean
  square: boolean
  triangle: boolean

  dpad_up: boolean
  dpad_left: boolean
  dpad_right: boolean
  dpad_down: boolean

  touchpad: boolean
  options: boolean
  share: boolean
  ps: boolean
}
