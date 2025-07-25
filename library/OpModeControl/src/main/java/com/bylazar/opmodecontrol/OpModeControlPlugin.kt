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
}

class OpModeControlPlugin : Plugin<BasePluginConfig>(OpModeControlPluginConfig()) {
    override var id = "com.bylazar.opmodecontrol"

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
                name = "",
                group = "",
                flavour = OpModeMeta.Flavor.AUTONOMOUS,
                source = OpModeMeta.Source.ANDROID_STUDIO,
                defaultGroup = OpModeMeta.DefaultGroup,
                autoTransition = ""
            )
        }

    override fun onNewClient(client: Socket.ClientSocket) {
        sendClient(client, "opModesList", OpModesList(opModeList))
        sendClient(
            client, "activeOpMode", ActiveOpMode(
                opMode = activeOpModeInfo,
                status = status
            )
        )
    }

    override fun onMessage(type: String, data: Any?) {
        log("Got message of type $type with data $data")
        when(type){
            "initOpMode" -> {
                opModeManager.initOpMode(data as String)
            }
            "startActiveOpMode" -> {
                opModeManager.startActiveOpMode()
            }
            "stopActiveOpMode" -> {
                opModeManager.stopActiveOpMode()
            }
        }
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

    fun sendActiveOpMode() {
        log("New active OpMode $status, ${activeOpModeInfo.name}")
        if(activeOpModeName == "\$Stop\$Robot\$") status = OpModeStatus.STOPPED
        send(
            "activeOpMode", ActiveOpMode(
                opMode = activeOpModeInfo,
                status = status
            )
        )
    }

    override fun onOpModePreInit(opMode: OpMode) {
        activeOpMode = opMode
        activeOpModeName = opModeManager.activeOpModeName
        status = OpModeStatus.INIT
        sendActiveOpMode()
    }

    override fun onOpModePreStart(opMode: OpMode) {
        activeOpMode = opMode
        activeOpModeName = opModeManager.activeOpModeName
        status = OpModeStatus.RUNNING
        sendActiveOpMode()
    }

    override fun onOpModePostStop(opMode: OpMode) {
        activeOpMode = opMode
        activeOpModeName = opModeManager.activeOpModeName
        status = OpModeStatus.STOPPED
        sendActiveOpMode()
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

            opModeList = list.sortedWith(compareBy({ it.group }, { it.name })).toMutableList()

            log("OpModes: ${opModeList.joinToString(", ")}")
        }
    }


    override fun onEnablePanels() {
    }

    override fun onDisablePanels() {
    }
}