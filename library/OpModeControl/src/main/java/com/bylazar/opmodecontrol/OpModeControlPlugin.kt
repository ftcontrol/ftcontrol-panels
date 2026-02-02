package com.bylazar.opmodecontrol

import android.content.Context
import com.bylazar.panels.Panels
import com.bylazar.panels.plugins.BasePluginConfig
import com.bylazar.panels.plugins.Plugin
import com.bylazar.panels.server.Socket
import com.qualcomm.ftccommon.FtcEventLoop
import com.qualcomm.robotcore.eventloop.opmode.OpMode
import com.qualcomm.robotcore.eventloop.opmode.OpModeManagerImpl
import dev.frozenmilk.sinister.Scanner
import dev.frozenmilk.sinister.sdk.apphooks.SDKOpModeRegistrar
import dev.frozenmilk.sinister.sdk.opmodes.OpModeScanner
import dev.frozenmilk.sinister.sdk.opmodes.SinisterRegisteredOpModes
import dev.frozenmilk.sinister.targeting.NarrowSearch
import dev.frozenmilk.util.graph.rule.dependsOn
import dev.frozenmilk.util.graph.rule.dependsOnClass
import org.firstinspires.ftc.robotcore.internal.opmode.OpModeMeta
import java.lang.ref.WeakReference
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledFuture
import java.util.concurrent.TimeUnit

open class OpModeControlPluginConfig : BasePluginConfig() {}

object Plugin : Plugin<BasePluginConfig>(OpModeControlPluginConfig()), Scanner {
    val opModeList: List<OpModeDetails>
        get() = SinisterRegisteredOpModes.opModes.mapNotNull { opModeMeta ->
            if (opModeMeta.flavor == OpModeMeta.Flavor.SYSTEM) null
            else OpModeDetails(
                name = opModeMeta.name,
                group = opModeMeta.group,
                flavour = opModeMeta.flavor,
                source = opModeMeta.source ?: OpModeMeta.Source.ANDROID_STUDIO,
                defaultGroup = OpModeMeta.DefaultGroup,
                autoTransition = opModeMeta.autoTransition ?: ""
            )
        }.sortedWith(compareBy({ it.group }, { it.name }))

    var status = OpModeStatus.STOPPED
    var activeOpMode: OpMode? = null
    var activeOpModeStartTimestamp: Long? = null
        set(value) {
            if (value == null) {
                stopTicker()
            } else {
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

    @Volatile
    private var tickTask: ScheduledFuture<*>? = null

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
        panelsInstance: Panels, context: Context
    ) {

    }

    private var opModeManagerRef: WeakReference<OpModeManagerImpl>? = null

    override fun onAttachEventLoop(eventLoop: FtcEventLoop) {
    }

    override fun onOpModeManager(o: OpModeManagerImpl) {
        opModeManagerRef = WeakReference(o)
        activeOpMode = null
        activeOpModeName = ""
        activeOpModeStartTimestamp = null
    }

    fun sendActiveOpMode() {
        log("New active OpMode $status, ${activeOpModeInfo.name}")
        if (activeOpModeName == "\$Stop\$Robot\$") status = OpModeStatus.STOPPED
        if (status == OpModeStatus.STOPPED) {
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


    override fun onEnablePanels() {
    }

    override fun onDisablePanels() {
    }

    override val loadAdjacencyRule =
        dependsOnClass(OpModeScanner::class.java).and(dependsOn(SDKOpModeRegistrar))
    override val unloadAdjacencyRule =
        dependsOnClass(OpModeScanner::class.java).and(dependsOn(SDKOpModeRegistrar))
    override val targets = NarrowSearch()

    override fun scan(loader: ClassLoader, cls: Class<*>) {}
    override fun afterScan(loader: ClassLoader) {
        val opModeList = opModeList
        log("OpModes: ${opModeList.joinToString(", ", transform = { it.name })}")
        send("opModesList", OpModesList(opModeList))
        sendActiveOpMode()
    }

    override fun unload(loader: ClassLoader, cls: Class<*>) {}
    override fun afterUnload(loader: ClassLoader) {
        val opModeList = opModeList
        log("OpModes: ${opModeList.joinToString(", ", transform = { it.name })}")
        send("opModesList", OpModesList(opModeList))
        sendActiveOpMode()
    }
}