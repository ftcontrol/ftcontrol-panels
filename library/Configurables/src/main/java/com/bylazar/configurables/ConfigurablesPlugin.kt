package com.bylazar.configurables

import android.content.Context
import com.bylazar.configurables.GlobalConfigurables.fieldsMap
import com.bylazar.configurables.GlobalConfigurables.jvmFields
import com.bylazar.configurables.GlobalConfigurables.loadedFieldsMap
import com.bylazar.configurables.annotations.Configurable
import com.bylazar.configurables.annotations.IgnoreConfigurable
import com.bylazar.configurables.annotations.Sorter
import com.bylazar.configurables.variables.generics.GenericField
import com.bylazar.panels.Panels
import com.bylazar.panels.json.SocketMessage
import com.bylazar.panels.plugins.BasePluginConfig
import com.bylazar.panels.plugins.Plugin
import com.bylazar.panels.server.Socket
import com.qualcomm.ftccommon.FtcEventLoop
import com.qualcomm.robotcore.eventloop.opmode.OpMode
import com.qualcomm.robotcore.eventloop.opmode.OpModeManagerImpl
import dev.frozenmilk.sinister.Scanner
import dev.frozenmilk.sinister.loaders.RootClassLoader
import dev.frozenmilk.sinister.targeting.WideSearch
import java.lang.reflect.Modifier

open class ConfigurablesPluginConfig : BasePluginConfig() {}

object Plugin : Plugin<ConfigurablesPluginConfig>(ConfigurablesPluginConfig()), Scanner {
    private val allFieldsMap: Map<String, List<GenericTypeJson>>
        get() = jvmFields//
            .flatMap { it.value.map(GenericField::toJsonType) }//
            .groupBy { it.className }.toSortedMap()

    private var initialFieldsMap: Map<String, List<GenericTypeJson>> = mapOf()

    override fun onNewClient(client: Socket.ClientSocket) {
        sendClient(client, "initialConfigurables", initialFieldsMap)
        sendClient(client, "configurables", allFieldsMap)
    }

    override fun onMessage(client: Socket.ClientSocket, type: String, data: Any?) {
        log("Got message of type $type with data $data")
        if (type == "updatedConfigurable") {
            val changes = try {
                SocketMessage.convertData<List<ChangeJson>>(data)
            } catch (e: Exception) {
                log("Failed to convert data: ${e.message}")
                emptyList()
            }

            if (changes == null) return

            changes.forEach {
                val generalRef = fieldsMap[it.id] ?: return
                log("Field id: ${it.id}, New value: ${it.newValueString}")
                generalRef.setValue(it.newValueString)
                it.newValueString = generalRef.getValue().toString()
            }

            send("newConfigurables", changes)
        }
    }

    fun refreshClass(cls: Class<*>) {
        jvmFields.forEach { (loader, fields) ->
            if(fields.removeIf { it.reference.declaringClass == cls }) {
                scan(loader, cls)
                send("configurables", allFieldsMap)
                return
            }
        }
    }

    override fun onRegister(
        panelsInstance: Panels, context: Context
    ) {
        log("Initializing configurables")
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

    //
    // Scanner
    //

    override val loadAdjacencyRule = Scanner.INDEPENDENT
    override val unloadAdjacencyRule = Scanner.INDEPENDENT
    override val targets = WideSearch()

    override fun scan(loader: ClassLoader, cls: Class<*>) {
        if (!cls.isAnnotationPresent(Configurable::class.java)) return
        if (cls.isAnnotationPresent(IgnoreConfigurable::class.java)) return

        log("Sorting fields for class ${cls.simpleName}")
        val fields = cls.declaredFields.sortedBy { field ->
            val sort = field.getAnnotation(Sorter::class.java)?.sort
            log("Field ${field.name} has sort value: $sort")
            sort ?: Int.MAX_VALUE
        }
        log("Order after sorting: ${fields.map { it.name }}")
        fields.forEach { field ->
            try {
                val isFinal = Modifier.isFinal(field.modifiers)
                val isStatic = Modifier.isStatic(field.modifiers)
                val isIgnored = field.isAnnotationPresent(IgnoreConfigurable::class.java)

                val isJvmField = !isFinal && isStatic && !isIgnored

                val fieldTypeName = field.type.canonicalName ?: ""

                log("Found field of $fieldTypeName / $isJvmField")

                if (isJvmField) {
                    val displayClassName = cls.kotlin.simpleName ?: cls.name
                    val genericField = GenericField(classLoader = loader, className = displayClassName, reference = field)
                    log("Adding field $genericField / ${genericField.type} / ${genericField.value} / ${genericField.isNull}")
                    jvmFields.getOrPut(loader, ::mutableListOf).add(genericField)
                }
            } catch (t: Throwable) {
                error("Error inspecting field ${field.name} in $cls: ${t.message}")
            }
        }
    }

    override fun afterScan(loader: ClassLoader) {
        initialFieldsMap = allFieldsMap
        send("initialConfigurables", initialFieldsMap)
        send("configurables", allFieldsMap)
    }

    override fun beforeUnload(loader: ClassLoader) {
        jvmFields.remove(loader)
        loadedFieldsMap.remove(loader)?.forEach(fieldsMap::remove)
        initialFieldsMap = allFieldsMap
        send("initialConfigurables", initialFieldsMap)
        send("configurables", allFieldsMap)
    }

    override fun unload(loader: ClassLoader, cls: Class<*>) {}
}