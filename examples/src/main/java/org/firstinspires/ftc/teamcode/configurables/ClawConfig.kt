package org.firstinspires.ftc.teamcode.configurables


import com.bylazar.configurables.TPair
import com.bylazar.configurables.annotations.Configurable
import com.bylazar.configurables.annotations.GenericValue

@Configurable
object ClawConfig {
    enum class State {
        OPENED,
        CLOSED,
        AJAR
    }

    @JvmField
    var state: State = State.OPENED

    @field:GenericValue(State::class, Double::class)
    @JvmField
    var poses = TPair({ state }) {
        pair(State.OPENED, 0.0)
        pair(State.CLOSED, 1.0)
        pair(State.AJAR, 0.5)

        default(0.5)
    }

    val clawPos = poses()
    val openedPos = poses.getStateValue(State.OPENED)
}