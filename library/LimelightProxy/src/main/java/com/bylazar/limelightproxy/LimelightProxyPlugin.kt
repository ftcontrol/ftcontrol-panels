package com.bylazar.limelightproxy

import android.content.Context
import com.bylazar.limelightproxy.proxies.GenericProxy
import com.bylazar.limelightproxy.proxies.GenericSocketProxy
import com.bylazar.limelightproxy.proxies.GenericStreamingProxy
import com.bylazar.panels.Panels
import com.bylazar.panels.plugins.BasePluginConfig
import com.bylazar.panels.plugins.Plugin
import com.bylazar.panels.server.Socket
import com.qualcomm.ftccommon.FtcEventLoop
import com.qualcomm.robotcore.eventloop.opmode.OpMode
import com.qualcomm.robotcore.eventloop.opmode.OpModeManagerImpl

open class LimelightProxyConfig : BasePluginConfig() {}

object Plugin : Plugin<LimelightProxyConfig>(LimelightProxyConfig()) {
    lateinit var limelightProxy: GenericProxy
    lateinit var limelightFeedProxy: GenericStreamingProxy
    lateinit var limelightWebsocketProxy: GenericSocketProxy
    lateinit var limelightAPIProxy: GenericProxy
    lateinit var testServer: TestLimelightServer

    var isProxied: Boolean = false
        set(value) {
            when (value) {
                true -> {
                    limelightProxy.startServer()
                    limelightFeedProxy.startServer()
                    limelightWebsocketProxy.startProxy()
                    limelightAPIProxy.startServer()
                    if (isDev) limelightProxy.startServer()
                }

                false -> {
                    limelightProxy.stopServer()
                    limelightFeedProxy.stopServer()
                    limelightWebsocketProxy.stopProxy()
                    limelightAPIProxy.stopServer()
                    if (isDev) limelightProxy.stopServer()
                }
            }
            field = value
        }

    override fun onNewClient(client: Socket.ClientSocket) {
    }

    override fun onMessage(type: String, data: Any?) {
        log("Got message of type $type with data $data")
    }

    override fun onRegister(
        panelsInstance: Panels,
        context: Context
    ) {
        if (isDev) {
            limelightProxy = GenericProxy(5801, 3331, "localhost")
            limelightFeedProxy = GenericStreamingProxy(5800, 3331, "localhost")
            testServer = TestLimelightServer()
            testServer.startServer()

        } else {
            limelightProxy = GenericProxy(5801, 5801, "172.29.0.1")
            limelightFeedProxy = GenericStreamingProxy(5800, 5800, "172.29.0.1")
        }

        limelightWebsocketProxy = GenericSocketProxy(5805, 5805, "172.29.0.1")
        limelightAPIProxy = GenericProxy(5807, 5807, "172.29.0.1")

        isProxied = true
    }

    override fun onAttachEventLoop(eventLoop: FtcEventLoop) {
    }

    override fun onOpModeManager(o: OpModeManagerImpl) {
    }

    override fun onOpModePreInit(opMode: OpMode) {
    }

    override fun onOpModePreStart(opMode: OpMode) {
    }

    override fun onOpModePostStop(opMode: OpMode) {
    }

    override fun onEnablePanels() {
    }

    override fun onDisablePanels() {
    }
}