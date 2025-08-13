package org.firstinspires.ftc.teamcode

import com.bylazar.configurables.annotations.Configurable

class Follower {
    lateinit var something: String
}

interface PathCallback {

}

@Configurable
object ClawConfig {
    @JvmField
    var test = "lazar"
    @JvmField
    var number = 0
    @JvmField
    var follower: Follower? = null
    @JvmField
    var callbacks: ArrayList<PathCallback> = ArrayList<PathCallback>()
}