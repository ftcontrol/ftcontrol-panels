package com.bylazar.opmodecontrol

import android.content.Context
import android.os.SystemClock
import com.bylazar.panels.Panels
import com.bylazar.panels.plugins.BasePluginConfig
import com.bylazar.panels.plugins.Plugin
import com.bylazar.panels.server.Socket
import com.qualcomm.ftccommon.FtcEventLoop
import com.qualcomm.robotcore.eventloop.opmode.OpMode
import com.qualcomm.robotcore.eventloop.opmode.OpModeManagerImpl
import org.firstinspires.ftc.robotcore.internal.opmode.OpModeMeta
import org.firstinspires.ftc.robotcore.internal.opmode.RegisteredOpModes
import java.lang.ref.WeakReference
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledFuture
import java.util.concurrent.TimeUnit

open class OpModeControlPluginConfig : BasePluginConfig() {
}

object Plugin : Plugin<BasePluginConfig>(OpModeControlPluginConfig()) {
    var opModeList: MutableList<OpModeDetails> = mutableListOf()
        set(value) {
            field = value
            send("opModesList", OpModesList(value))
        }
    var status = OpModeStatus.STOPPED
    var activeOpMode: OpMode? = null
    var activeOpModeStartTimestamp: Long? = null
        set(value) {
            if(value == null){
                stopTicker()
            }else{
                startTicker(250)
            }
            field = value
        }
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

    private val scheduler = Executors.newSingleThreadScheduledExecutor { r ->
        Thread(r, "OpModeControl-Ticker")
    }
    @Volatile private var tickTask: ScheduledFuture<*>? = null

    private fun startTicker(periodMs: Long = 250) {
        if (tickTask?.isDone == false || tickTask?.isCancelled == false) return
        tickTask = scheduler.scheduleWithFixedDelay({
            val deltaMs = activeOpModeStartTimestamp?.let { start ->
                val now = System.currentTimeMillis()
                now - start
            } ?: 0L

            send("deltaMs", deltaMs)
        }, 0, periodMs, TimeUnit.MILLISECONDS)
    }

    private fun stopTicker() {
        tickTask?.cancel(false)
        tickTask = null
    }

    override fun onNewClient(client: Socket.ClientSocket) {
        sendClient(client, "opModesList", OpModesList(opModeList))
        sendClient(
            client, "activeOpMode", ActiveOpMode(
                opMode = activeOpModeInfo,
                status = status,
                startTimestamp = activeOpModeStartTimestamp
            )
        )
    }

    override fun onMessage(client: Socket.ClientSocket, type: String, data: Any?) {
        log("Got message of type $type with data $data")
        when (type) {
            "initOpMode" -> {
                opModeManagerRef?.get()?.initOpMode(data as String)
            }

            "startActiveOpMode" -> {
                opModeManagerRef?.get()?.startActiveOpMode()
            }

            "stopActiveOpMode" -> {
                opModeManagerRef?.get()?.stopActiveOpMode()
            }
        }
    }

    override fun onRegister(
        panelsInstance: Panels,
        context: Context
    ) {

    }

    private var opModeManagerRef: WeakReference<OpModeManagerImpl>? = null

    override fun onAttachEventLoop(eventLoop: FtcEventLoop) {
    }

    override fun onOpModeManager(o: OpModeManagerImpl) {
        opModeManagerRef = WeakReference(o)
        activeOpMode = null
        activeOpModeName = ""
        opModeList.clear()
        val t = Thread(FetcherRoutine())
        t.start()
        activeOpModeStartTimestamp = null
    }

    fun sendActiveOpMode() {
        log("New active OpMode $status, ${activeOpModeInfo.name}")
        if (activeOpModeName == "\$Stop\$Robot\$") status = OpModeStatus.STOPPED
        if(status == OpModeStatus.STOPPED){
            activeOpModeStartTimestamp = null
        }
        send(
            "activeOpMode", ActiveOpMode(
                opMode = activeOpModeInfo,
                status = status,
                startTimestamp = activeOpModeStartTimestamp
            )
        )
    }

    override fun onOpModePreInit(opMode: OpMode) {
        val manager = opModeManagerRef?.get() ?: return
        activeOpMode = opMode
        activeOpModeName = manager.activeOpModeName
        activeOpModeStartTimestamp = null
        status = OpModeStatus.INIT
        sendActiveOpMode()
    }

    override fun onOpModePreStart(opMode: OpMode) {
        val manager = opModeManagerRef?.get() ?: return
        activeOpMode = opMode
        activeOpModeName = manager.activeOpModeName
        activeOpModeStartTimestamp = System.currentTimeMillis()
        status = OpModeStatus.RUNNING
        sendActiveOpMode()
    }

    override fun onOpModePostStop(opMode: OpMode) {
        val manager = opModeManagerRef?.get() ?: return
        activeOpMode = opMode
        activeOpModeName = manager.activeOpModeName
        activeOpModeStartTimestamp = null
        status = OpModeStatus.STOPPED
        sendActiveOpMode()
    }

    class FetcherRoutine : Runnable {
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

            send("opModesList", OpModesList(opModeList))
            send(
                "activeOpMode", ActiveOpMode(
                    opMode = activeOpModeInfo,
                    status = status,
                    startTimestamp = activeOpModeStartTimestamp
                )
            )
        }
    }


    override fun onEnablePanels() {
    }

    override fun onDisablePanels() {
    }
}