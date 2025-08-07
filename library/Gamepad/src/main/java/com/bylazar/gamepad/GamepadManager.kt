package com.bylazar.gamepad

class GamepadManager {
    val DELETION_INTERVAL = 50L + 10L

    internal var currentState = Gamepad()
    internal var timestamps = GamepadTimestamps()

    internal fun update(newState: Gamepad) {
        val now = System.currentTimeMillis()

        fun updateIfChanged(old: Boolean, new: Boolean, oldTs: Long): Long {
            if(new) return now
            return oldTs
        }

        fun updateIfChangedDouble(old: Double, new: Double, oldTs: Long): Long {
            if(new != 0.0) return now
            return oldTs
        }

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
            leftStick = updateStick(
                currentState.leftStick,
                newState.leftStick,
                timestamps.leftStick
            ),
            rightStick = updateStick(
                currentState.rightStick,
                newState.rightStick,
                timestamps.rightStick
            ),
            cross = updateIfChanged(currentState.cross, newState.cross, timestamps.cross),
            circle = updateIfChanged(currentState.circle, newState.circle, timestamps.circle),
            square = updateIfChanged(currentState.square, newState.square, timestamps.square),
            triangle = updateIfChanged(
                currentState.triangle,
                newState.triangle,
                timestamps.triangle
            ),
            dpad_up = updateIfChanged(currentState.dpad_up, newState.dpad_up, timestamps.dpad_up),
            dpad_left = updateIfChanged(
                currentState.dpad_left,
                newState.dpad_left,
                timestamps.dpad_left
            ),
            dpad_right = updateIfChanged(
                currentState.dpad_right,
                newState.dpad_right,
                timestamps.dpad_right
            ),
            dpad_down = updateIfChanged(
                currentState.dpad_down,
                newState.dpad_down,
                timestamps.dpad_down
            ),
            touchpad = updateIfChanged(
                currentState.touchpad,
                newState.touchpad,
                timestamps.touchpad
            ),
            options = updateIfChanged(currentState.options, newState.options, timestamps.options),
            share = updateIfChanged(currentState.share, newState.share, timestamps.share),
            ps = updateIfChanged(currentState.ps, newState.ps, timestamps.ps),
        )

        currentState = newState
    }

    fun asCombinedFTCGamepad(gamepad: com.qualcomm.robotcore.hardware.Gamepad): com.qualcomm.robotcore.hardware.Gamepad {
        val g = com.qualcomm.robotcore.hardware.Gamepad()
        g.left_stick_x = if (leftStick.x != 0.0) leftStick.x.toFloat() else gamepad.left_stick_x
        g.left_stick_y = if (leftStick.y != 0.0) leftStick.y.toFloat() else gamepad.left_stick_y
        g.right_stick_x =
            if (rightStick.x != 0.0) rightStick.x.toFloat() else gamepad.right_stick_x
        g.right_stick_y =
            if (rightStick.y != 0.0) rightStick.y.toFloat() else gamepad.right_stick_y

        g.left_trigger = if (l2 != 0.0) l2.toFloat() else gamepad.left_trigger
        g.right_trigger = if (r2 != 0.0) r2.toFloat() else gamepad.right_trigger

        g.left_bumper = l1 || gamepad.left_bumper
        g.right_bumper = r1 || gamepad.right_bumper

        g.a = cross || gamepad.a
        g.b = circle || gamepad.b
        g.x = square || gamepad.x
        g.y = triangle || gamepad.y

        g.cross = g.a
        g.circle = g.b
        g.square = g.x
        g.triangle = g.y

        g.dpad_up = dpadUp || gamepad.dpad_up
        g.dpad_down = dpadDown || gamepad.dpad_down
        g.dpad_left = dpadLeft || gamepad.dpad_left
        g.dpad_right = dpadRight || gamepad.dpad_right

        g.guide = ps || gamepad.guide
        g.ps = g.guide
        g.options = options || gamepad.options
        g.back = share || gamepad.back
        g.share = g.back
        g.touchpad = touchpad || gamepad.touchpad

        g.left_stick_button = leftStick.value || gamepad.left_stick_button
        g.right_stick_button = rightStick.value || gamepad.right_stick_button

        g.type = com.qualcomm.robotcore.hardware.Gamepad.Type.SONY_PS4

        return g
    }

    val asFTCGamepad: com.qualcomm.robotcore.hardware.Gamepad
        get() {
            val g = com.qualcomm.robotcore.hardware.Gamepad()
            g.left_stick_x = leftStick.x.toFloat()
            g.left_stick_y = leftStick.y.toFloat()
            g.right_stick_x = rightStick.x.toFloat()
            g.right_stick_y = rightStick.y.toFloat()
            g.left_trigger = l2.toFloat()
            g.right_trigger = r2.toFloat()
            g.left_bumper = l1
            g.right_bumper = r1

            g.a = cross
            g.b = circle
            g.x = square
            g.y = triangle

            g.cross = cross
            g.circle = circle
            g.square = square
            g.triangle = triangle

            g.dpad_up = dpadUp
            g.dpad_down = dpadDown
            g.dpad_left = dpadLeft
            g.dpad_right = dpadRight

            g.guide = ps
            g.ps = ps
            g.options = options
            g.back = share
            g.share = share
            g.touchpad = touchpad

            g.left_stick_button = leftStick.value
            g.right_stick_button = rightStick.value

            g.type = com.qualcomm.robotcore.hardware.Gamepad.Type.SONY_PS4

            return g
        }

    private fun isActive(timestamp: Long): Boolean =
        System.currentTimeMillis() - timestamp <= DELETION_INTERVAL

    val l1: Boolean get() = currentState.l1 && isActive(timestamps.l1)
    val l2: Double get() = if (isActive(timestamps.l2)) currentState.l2 else 0.0
    val r1: Boolean get() = currentState.r1 && isActive(timestamps.r1)
    val r2: Double get() = if (isActive(timestamps.r2)) currentState.r2 else 0.0

    val cross: Boolean get() = currentState.cross && isActive(timestamps.cross)
    val crossTimestamp: Long get() = timestamps.cross
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