package org.firstinspires.ftc.teamcode.field

import com.bylazar.field.PanelsField
import com.bylazar.field.Style

class Drawing {
    val panelsField = PanelsField.field
    val image = panelsField.registerImage("./example.png")

    fun main() {
        panelsField.setOffsets(PanelsField.presets.DEFAULT_FTC) //choose field coordinates system
        panelsField.setOffsets(PanelsField.presets.ROAD_RUNNER)
        panelsField.setOffsets(PanelsField.presets.PEDRO_PATHING)

        panelsField.setStyle("red", "blue", 2.0) //fill, outline fill, outline width
        panelsField.setStyle(
            Style(
                fill = "red",
                outlineFill = "blue",
                outlineWidth = 2.0
            )
        )

        panelsField.moveCursor(0.0, 0.0) //move cursor

        panelsField.circle(r = 2.0)
        panelsField.line(x2 = 10.0, y2 = 10.0)
        panelsField.rect(w = 10.0, h = 5.0)
        panelsField.img(w = 10.0, h = 10.0, id = image)

        panelsField.update() //update drawing
    }
}