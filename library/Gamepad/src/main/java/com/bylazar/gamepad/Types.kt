package com.bylazar.gamepad

data class Gamepad(
    var l1: Boolean = false,
    var l2: Double = 0.0,
    var r1: Boolean = false,
    var r2: Double = 0.0,
    var leftStick: Stick = Stick(),
    var rightStick: Stick = Stick(),
    var cross: Boolean = false,
    var circle: Boolean = false,
    var square: Boolean = false,
    var triangle: Boolean = false,
    var dpad_up: Boolean = false,
    var dpad_left: Boolean = false,
    var dpad_right: Boolean = false,
    var dpad_down: Boolean = false,
    var touchpad: Boolean = false,
    var options: Boolean = false,
    var share: Boolean = false,
    var ps: Boolean = false
)

data class Stick(
    var x: Double = 0.0,
    var y: Double = 0.0,
    var value: Boolean = false
)

data class GamepadTimestamps(
    var l1: Long = 0L,
    var l2: Long = 0L,
    var r1: Long = 0L,
    var r2: Long = 0L,
    var leftStick: StickTimestamps = StickTimestamps(),
    var rightStick: StickTimestamps = StickTimestamps(),
    var cross: Long = 0L,
    var circle: Long = 0L,
    var square: Long = 0L,
    var triangle: Long = 0L,
    var dpad_up: Long = 0L,
    var dpad_left: Long = 0L,
    var dpad_right: Long = 0L,
    var dpad_down: Long = 0L,
    var touchpad: Long = 0L,
    var options: Long = 0L,
    var share: Long = 0L,
    var ps: Long = 0L
)

data class StickTimestamps(
    var x: Long = 0L,
    var y: Long = 0L,
    var value: Long = 0L,
)