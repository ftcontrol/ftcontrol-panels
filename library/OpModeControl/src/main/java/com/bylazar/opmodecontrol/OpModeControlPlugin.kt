package com.bylazar.opmodecontrol

import android.content.Context
import com.bylazar.panels.Panels
import com.bylazar.panels.plugins.BasePluginConfig
import com.bylazar.panels.plugins.Plugin
import com.bylazar.panels.server.Socket
import com.qualcomm.ftccommon.FtcEventLoop
import com.qualcomm.robotcore.eventloop.opmode.OpMode
import com.qualcomm.robotcore.eventloop.opmode.OpModeManagerImpl
import org.firstinspires.ftc.robotcore.internal.opmode.OpModeMeta
import org.firstinspires.ftc.robotcore.internal.opmode.RegisteredOpModes

open class OpModeControlPluginConfig : BasePluginConfig() {
    open var test = "test"
}

class OpModeControlPlugin : Plugin<BasePluginConfig>(OpModeControlPluginConfig()) {
    override var id = "com.bylazar.opmodecontrol"

    enum class OpModeStatus {
        INIT,
        RUNNING,
        STOPPED
    }

    var opModeList: MutableList<OpModeDetails> = mutableListOf()
        set(value) {
            field = value
            send("opModesList", OpModesList(value))
        }
    var status = OpModeStatus.STOPPED
    var activeOpMode: OpMode? = null
    var activeOpModeName = ""

    val activeOpModeInfo: OpModeDetails
        get() {
            for (opMode in opModeList) {
                if (opMode.name == activeOpModeName) {
                    return opMode
                }
            }
            return OpModeDetails(
                name = "\$Stop\$Robot\$",
                group = "",
                flavour = OpModeMeta.Flavor.AUTONOMOUS,
                source = OpModeMeta.Source.ANDROID_STUDIO,
                defaultGroup = OpModeMeta.DefaultGroup,
                autoTransition = ""
            )
        }

    override fun onNewClient(client: Socket.ClientSocket) {
        sendClient(client, "opModesList", OpModesList(opModeList))
    }

    override fun onMessage(type: String, data: Any?) {
        log("Got message of type $type with data $data")
    }

    override fun onRegister(
        panelsInstance: Panels,
        context: Context
    ) {

    }

    lateinit var opModeManager: OpModeManagerImpl

    override fun onAttachEventLoop(eventLoop: FtcEventLoop) {

    }

    override fun onOpModeManager(o: OpModeManagerImpl) {
        opModeManager = o
        activeOpMode = null
        activeOpModeName = ""
        opModeList.clear()
        val t = Thread(FetcherRoutine())
        t.start()
    }

    override fun onOpModePreInit(opMode: OpMode) {
        activeOpMode = opMode
        activeOpModeName = opModeManager.activeOpModeName
        status = OpModeStatus.INIT
    }

    override fun onOpModePreStart(opMode: OpMode) {
        activeOpMode = opMode
        activeOpModeName = opModeManager.activeOpModeName
        status = OpModeStatus.RUNNING
    }

    override fun onOpModePostStop(opMode: OpMode) {
        activeOpMode = opMode
        activeOpModeName = opModeManager.activeOpModeName
        status = OpModeStatus.STOPPED
    }

    inner class FetcherRoutine : Runnable {
        override fun run() {
            RegisteredOpModes.getInstance().waitOpModesRegistered()

            var list = ArrayList<OpModeDetails>()
            for (opModeMeta in RegisteredOpModes.getInstance().opModes) {
                if (opModeMeta.flavor != OpModeMeta.Flavor.SYSTEM) {
                    list.add(
                        OpModeDetails(
                            name = opModeMeta.name,
                            group = opModeMeta.group,
                            flavour = opModeMeta.flavor,
                            source = opModeMeta.source ?: OpModeMeta.Source.ANDROID_STUDIO,
                            defaultGroup = OpModeMeta.DefaultGroup,
                            autoTransition = opModeMeta.autoTransition ?: ""
                        )
                    )
                }
            }

            opModeList = list

            log("OpModes: ${opModeList.joinToString(", ")}")
        }
    }
}