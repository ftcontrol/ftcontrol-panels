package com.bylazar.gamepad

class GamepadManager {
    val DELETION_INTERVAL = 50L + 10L

    internal var currentState = Gamepad()
    internal var timestamps = GamepadTimestamps()

    fun update(newState: Gamepad) {
        val now = System.currentTimeMillis()

        fun updateIfChanged(old: Boolean, new: Boolean, oldTs: Long): Long =
            if (old != new) now else oldTs

        fun updateIfChangedDouble(old: Double, new: Double, oldTs: Long): Long =
            if (old != new) now else oldTs

        fun updateStick(old: Stick, new: Stick, oldTs: StickTimestamps): StickTimestamps =
            StickTimestamps(
                x = updateIfChangedDouble(old.x, new.x, oldTs.x),
                y = updateIfChangedDouble(old.y, new.y, oldTs.y),
                value = updateIfChanged(old.value, new.value, oldTs.value)
            )

        timestamps = timestamps.copy(
            l1 = updateIfChanged(currentState.l1, newState.l1, timestamps.l1),
            l2 = updateIfChangedDouble(currentState.l2, newState.l2, timestamps.l2),
            r1 = updateIfChanged(currentState.r1, newState.r1, timestamps.r1),
            r2 = updateIfChangedDouble(currentState.r2, newState.r2, timestamps.r2),
            leftStick = updateStick(currentState.leftStick, newState.leftStick, timestamps.leftStick),
            rightStick = updateStick(currentState.rightStick, newState.rightStick, timestamps.rightStick),
            cross = updateIfChanged(currentState.cross, newState.cross, timestamps.cross),
            circle = updateIfChanged(currentState.circle, newState.circle, timestamps.circle),
            square = updateIfChanged(currentState.square, newState.square, timestamps.square),
            triangle = updateIfChanged(currentState.triangle, newState.triangle, timestamps.triangle),
            dpad_up = updateIfChanged(currentState.dpad_up, newState.dpad_up, timestamps.dpad_up),
            dpad_left = updateIfChanged(currentState.dpad_left, newState.dpad_left, timestamps.dpad_left),
            dpad_right = updateIfChanged(currentState.dpad_right, newState.dpad_right, timestamps.dpad_right),
            dpad_down = updateIfChanged(currentState.dpad_down, newState.dpad_down, timestamps.dpad_down),
            touchpad = updateIfChanged(currentState.touchpad, newState.touchpad, timestamps.touchpad),
            options = updateIfChanged(currentState.options, newState.options, timestamps.options),
            share = updateIfChanged(currentState.share, newState.share, timestamps.share),
            ps = updateIfChanged(currentState.ps, newState.ps, timestamps.ps),
        )

        currentState = newState
    }

    fun asCombinedFTCGamepad(gamepad: com.qualcomm.robotcore.hardware.Gamepad): com.qualcomm.robotcore.hardware.Gamepad {
        return com.qualcomm.robotcore.hardware.Gamepad().apply {
            left_stick_x = if (currentState.leftStick.x != 0.0) currentState.leftStick.x.toFloat() else gamepad.left_stick_x
            left_stick_y = if (currentState.leftStick.y != 0.0) currentState.leftStick.y.toFloat() else gamepad.left_stick_y
            right_stick_x = if (currentState.rightStick.x != 0.0) currentState.rightStick.x.toFloat() else gamepad.right_stick_x
            right_stick_y = if (currentState.rightStick.y != 0.0) currentState.rightStick.y.toFloat() else gamepad.right_stick_y

            left_trigger = if (currentState.l2 != 0.0) currentState.l2.toFloat() else gamepad.left_trigger
            right_trigger = if (currentState.r2 != 0.0) currentState.r2.toFloat() else gamepad.right_trigger

            left_bumper = currentState.l1 || gamepad.left_bumper
            right_bumper = currentState.r1 || gamepad.right_bumper

            a = currentState.cross || gamepad.a
            b = currentState.circle || gamepad.b
            x = currentState.square || gamepad.x
            y = currentState.triangle || gamepad.y

            dpad_up = currentState.dpad_up || gamepad.dpad_up
            dpad_down = currentState.dpad_down || gamepad.dpad_down
            dpad_left = currentState.dpad_left || gamepad.dpad_left
            dpad_right = currentState.dpad_right || gamepad.dpad_right

            guide = currentState.ps || gamepad.guide
            options = currentState.options || gamepad.options
            back = currentState.share || gamepad.back
            touchpad = currentState.touchpad || gamepad.touchpad

            left_stick_button = currentState.leftStick.value || gamepad.left_stick_button
            right_stick_button = currentState.rightStick.value || gamepad.right_stick_button

            type = com.qualcomm.robotcore.hardware.Gamepad.Type.SONY_PS4
        }
    }

    val asFTCGamepad: com.qualcomm.robotcore.hardware.Gamepad
        get() = com.qualcomm.robotcore.hardware.Gamepad().apply {
            left_stick_x = currentState.leftStick.x.toFloat()
            left_stick_y = currentState.leftStick.y.toFloat()
            right_stick_x = currentState.rightStick.x.toFloat()
            right_stick_y = currentState.rightStick.y.toFloat()
            left_trigger = currentState.l2.toFloat()
            right_trigger = currentState.r2.toFloat()
            left_bumper = currentState.l1
            right_bumper = currentState.r1

            a = currentState.cross
            b = currentState.circle
            x = currentState.square
            y = currentState.triangle

            dpad_up = currentState.dpad_up
            dpad_down = currentState.dpad_down
            dpad_left = currentState.dpad_left
            dpad_right = currentState.dpad_right

            guide = currentState.ps
            options = currentState.options
            back = currentState.share
            touchpad = currentState.touchpad

            left_stick_button = currentState.leftStick.value
            right_stick_button = currentState.rightStick.value

            type = com.qualcomm.robotcore.hardware.Gamepad.Type.SONY_PS4
        }

    private fun isActive(timestamp: Long): Boolean =
        System.currentTimeMillis() - timestamp <= DELETION_INTERVAL

    val l1: Boolean get() = currentState.l1 && isActive(timestamps.l1)
    val l2: Double get() = if (isActive(timestamps.l2)) currentState.l2 else 0.0
    val r1: Boolean get() = currentState.r1 && isActive(timestamps.r1)
    val r2: Double get() = if (isActive(timestamps.r2)) currentState.r2 else 0.0

    val cross: Boolean get() = currentState.cross && isActive(timestamps.cross)
    val circle: Boolean get() = currentState.circle && isActive(timestamps.circle)
    val square: Boolean get() = currentState.square && isActive(timestamps.square)
    val triangle: Boolean get() = currentState.triangle && isActive(timestamps.triangle)

    val dpadUp: Boolean get() = currentState.dpad_up && isActive(timestamps.dpad_up)
    val dpadLeft: Boolean get() = currentState.dpad_left && isActive(timestamps.dpad_left)
    val dpadRight: Boolean get() = currentState.dpad_right && isActive(timestamps.dpad_right)
    val dpadDown: Boolean get() = currentState.dpad_down && isActive(timestamps.dpad_down)

    val touchpad: Boolean get() = currentState.touchpad && isActive(timestamps.touchpad)
    val options: Boolean get() = currentState.options && isActive(timestamps.options)
    val share: Boolean get() = currentState.share && isActive(timestamps.share)
    val ps: Boolean get() = currentState.ps && isActive(timestamps.ps)

    val leftStickX: Double get() = if (isActive(timestamps.leftStick.x)) currentState.leftStick.x else 0.0
    val leftStickY: Double get() = if (isActive(timestamps.leftStick.y)) currentState.leftStick.y else 0.0
    val leftStickPressed: Boolean get() = currentState.leftStick.value && isActive(timestamps.leftStick.value)

    val rightStickX: Double get() = if (isActive(timestamps.rightStick.x)) currentState.rightStick.x else 0.0
    val rightStickY: Double get() = if (isActive(timestamps.rightStick.y)) currentState.rightStick.y else 0.0
    val rightStickPressed: Boolean get() = currentState.rightStick.value && isActive(timestamps.rightStick.value)

    val leftStick: Stick get() = Stick(leftStickX, leftStickY, leftStickPressed)
    val rightStick: Stick get() = Stick(rightStickX, rightStickY, rightStickPressed)
}