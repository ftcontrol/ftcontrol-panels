package com.bylazar.gamepad

data class Gamepad(
    val l1: Boolean = false,
    val l2: Boolean = false,
    val r1: Boolean = false,
    val r2: Boolean = false,
    val leftStick: Stick = Stick(),
    val rightStick: Stick = Stick(),
    val cross: Boolean = false,
    val circle: Boolean = false,
    val square: Boolean = false,
    val triangle: Boolean = false,
    val dpad_up: Boolean = false,
    val dpad_left: Boolean = false,
    val dpad_right: Boolean = false,
    val dpad_down: Boolean = false,
    val touchpad: Boolean = false,
    var options: Boolean = false,
    val share: Boolean = false,
    val ps: Boolean = false
)

data class Stick(
    val x: Double = 0.0,
    val y: Double = 0.0,
    val value: Boolean = false
)

data class GamepadTimestamps(
    val l1: Long = 0L,
    val l2: Long = 0L,
    val r1: Long = 0L,
    val r2: Long = 0L,
    val leftStick: Stick = Stick(),
    val rightStick: Stick = Stick(),
    val cross: Long = 0L,
    val circle: Long = 0L,
    val square: Long = 0L,
    val triangle: Long = 0L,
    val dpad_up: Long = 0L,
    val dpad_left: Long = 0L,
    val dpad_right: Long = 0L,
    val dpad_down: Long = 0L,
    val touchpad: Long = 0L,
    var options: Long = 0L,
    val share: Long = 0L,
    val ps: Long = 0L
)

data class StickTimestamps(
    val x: Long = 0L,
    val y: Long = 0L,
    val value: Long = 0L,
)