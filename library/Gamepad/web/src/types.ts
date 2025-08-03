export type GamepadData = {
  l1: boolean
  l2: number
  r1: boolean
  r2: number
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

export type GamepadTimestamps = {
  l1: number
  l2: number
  r1: number
  r2: number
  leftStick: {
    x: number
    y: number
    value: number
  }
  rightStick: {
    x: number
    y: number
    value: number
  }
  cross: number
  circle: number
  square: number
  triangle: number

  dpad_up: number
  dpad_left: number
  dpad_right: number
  dpad_down: number

  touchpad: number
  options: number
  share: number
  ps: number
}
