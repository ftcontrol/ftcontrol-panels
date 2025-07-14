package org.firstinspires.ftc.teamcode

import com.bylazar.configurables.annotations.Configurable

class Follower {
    lateinit var something: String
}

@Configurable
object ClawConfig {
    @JvmField
    var test = "lazar"
    @JvmField
    var number = 0
    @JvmField
    var follower: Follower? = null
}