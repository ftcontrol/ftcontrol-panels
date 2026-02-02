package org.firstinspires.ftc.teamcode.field

import com.bylazar.field.CanvasRotation
import com.bylazar.field.FieldPluginConfig
import com.bylazar.field.FieldPresetParams

class FieldConfig : FieldPluginConfig() {
    //You can define custom Field presets in a config class
    //This config must inherit FieldPluginConfig and can be placed anywhere in the project
    override var extraPresets: List<FieldPresetParams> = listOf(
        FieldPresetParams(
            name = "My Awesome Preset",
            offsetX = 24.0 * -1,
            offsetY = 24.0 * 4,
            flipY = true,
            reverseXY = true,
            rotation = CanvasRotation.DEG_270,
        )
    )
}