package com.bylazar.panels

object PanelsGlobalStats {
    val pluginsCoreVersion = "0.0.1"

    val isCombined: Boolean
        get() {
            return try {
                val clazz = Class.forName("com.bylazar.fullpanels.PanelsInstallationIsCombined")
                true
            } catch (t: Throwable) {
                false
            }
        }

    val version: String
        get() {
            return try {
                if (isCombined) {
                    val clazz = Class.forName("com.bylazar.fullpanels.PanelsInstallationIsCombined")
                    val instance = clazz.getDeclaredConstructor().newInstance()
                    val versionField = clazz.getDeclaredField("version")
                    versionField.isAccessible = true
                    versionField.get(instance) as? String ?: ""
                } else {
                    "0.0.2"
                }
            } catch (e: Exception) {
                ""
            }
        }
}