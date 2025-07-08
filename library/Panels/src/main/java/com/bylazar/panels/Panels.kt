package com.bylazar.panels

import android.content.Context
import android.view.Menu
import com.bylazar.panels.core.MenuHandler
import com.bylazar.panels.core.MenuManager
import com.bylazar.panels.core.OpModeHandler
import com.bylazar.panels.core.PreferencesHandler
import com.bylazar.panels.reflection.ClassFinder
import com.bylazar.panels.server.StaticServer
import com.qualcomm.ftccommon.FtcEventLoop
import com.qualcomm.robotcore.eventloop.opmode.OpMode
import com.qualcomm.robotcore.eventloop.opmode.OpModeManager
import com.qualcomm.robotcore.eventloop.opmode.OpModeManagerNotifier.Notifications
import com.qualcomm.robotcore.eventloop.opmode.OpModeRegistrar
import com.qualcomm.robotcore.util.WebHandlerManager
import org.firstinspires.ftc.ftccommon.external.OnCreate
import org.firstinspires.ftc.ftccommon.external.OnCreateEventLoop
import org.firstinspires.ftc.ftccommon.external.OnCreateMenu
import org.firstinspires.ftc.ftccommon.external.OnDestroy
import org.firstinspires.ftc.ftccommon.external.WebHandlerRegistrar
import org.firstinspires.ftc.ftccommon.internal.FtcRobotControllerWatchdogService
import org.firstinspires.ftc.robotcore.internal.system.AppUtil


object Panels : Notifications {
    lateinit var server: StaticServer
    var config: PanelsConfig = PanelsConfig()

    @JvmStatic
    @OpModeRegistrar
    fun registerOpMode(manager: OpModeManager) {
        OpModeHandler.init(manager)
        OpModeHandler.registerOpMode()
    }

    @JvmStatic
    @OnCreate
    fun start(context: Context) {
        PreferencesHandler.init(context)
        enable()
        MenuHandler.injectText()

        val configs = ClassFinder().findClasses(
            apkPath = context.packageCodePath,
            shouldKeepFilter = { clazz ->
                PanelsConfig::class.java.isAssignableFrom(clazz) && clazz != PanelsConfig::class.java
            }
        )

        Logger.coreLog("Found ${configs.size} configs.")

        if (configs.isNotEmpty()) {
            config = Class.forName(configs[0].className)
                .getDeclaredConstructor()
                .newInstance() as PanelsConfig
        }
        Logger.coreLog("Config is $config")

        if(config.isDisabled){
            PreferencesHandler.isEnabled = false
        }
    }

    @JvmStatic
    @WebHandlerRegistrar
    fun attachWebServer(context: Context, manager: WebHandlerManager) {
        try {
            server = StaticServer(context, 8001, "web")
        } catch (e: Exception) {
            Logger.coreLog("Failed to start webserver: " + e.message)
        }

        if (PreferencesHandler.isEnabled) {
            server.startServer()
        }
    }

    @JvmStatic
    @OnCreateEventLoop
    fun attachEventLoop(context: Context, eventLoop: FtcEventLoop?) {

    }

    @JvmStatic
    @OnCreateMenu
    fun populateMenu(context: Context, menu: Menu) {
        MenuManager.createMenu(menu)
    }

    @JvmStatic
    @OnDestroy
    fun stop(context: Context) {
        if (!FtcRobotControllerWatchdogService.isLaunchActivity(
                AppUtil.getInstance().rootActivity
            )
        ) {
            return
        }

        MenuHandler.removeText()
        server.stopServer()
    }

    override fun onOpModePreInit(opMode: OpMode) {

    }

    override fun onOpModePreStart(opMode: OpMode) {

    }

    override fun onOpModePostStop(opMode: OpMode) {

    }


    fun toggle() {
        when (PreferencesHandler.isEnabled) {
            true -> disable()
            false -> enable()
        }
    }

    fun enable() {
        if(config.isDisabled) return
        if (PreferencesHandler.isEnabled) return
        PreferencesHandler.isEnabled = true
        MenuHandler.updateText()
        server.startServer()
    }

    fun disable() {
        if (!PreferencesHandler.isEnabled) return
        PreferencesHandler.isEnabled = false
        MenuHandler.updateText()
        server.stopServer()
    }
}