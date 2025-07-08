package com.bylazar.panels.core

import android.graphics.Color
import android.graphics.Typeface
import android.widget.LinearLayout
import android.widget.TextView
import org.firstinspires.ftc.robotcore.internal.system.AppUtil
import java.lang.ref.WeakReference

object MenuHandler {
    private var connectionStatusTextViewRef: WeakReference<TextView>? = null
    private var parentLayoutRef: WeakReference<LinearLayout>? = null

    fun injectText() {
        val activity = AppUtil.getInstance().activity ?: return

        val connectionStatusTextView = TextView(activity).apply {
            typeface = Typeface.DEFAULT_BOLD
            setTextColor(Color.RED)

            val horizontalMarginId = activity.resources.getIdentifier(
                "activity_horizontal_margin", "dimen", activity.packageName
            )
            val horizontalMargin = activity.resources.getDimension(horizontalMarginId).toInt()

            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                setMargins(horizontalMargin, 0, horizontalMargin, 0)
            }
        }

        val parentLayoutId = activity.resources.getIdentifier(
            "entire_screen", "id", activity.packageName
        )
        val parentLayout = activity.findViewById<LinearLayout>(parentLayoutId) ?: return

        val childCount = parentLayout.childCount
        val relativeLayoutId = activity.resources.getIdentifier(
            "RelativeLayout", "id", activity.packageName
        )

        val relativeLayoutIndex = (0 until childCount).indexOfFirst {
            parentLayout.getChildAt(it).id == relativeLayoutId
        }.takeIf { it >= 0 } ?: childCount

        connectionStatusTextViewRef = WeakReference(connectionStatusTextView)
        parentLayoutRef = WeakReference(parentLayout)

        AppUtil.getInstance().runOnUiThread {
            parentLayout.addView(connectionStatusTextView, relativeLayoutIndex)
        }

        updateText()
    }

    fun updateText() {
        val textView = connectionStatusTextViewRef?.get() ?: return
        AppUtil.getInstance().runOnUiThread {
            textView.text = if (PreferencesHandler.isEnabled) {
                "Panels: enabled"
            } else {
                "Panels: disabled"
            }
        }
    }

    fun removeText() {
        val parentLayout = parentLayoutRef?.get() ?: return
        val textView = connectionStatusTextViewRef?.get() ?: return

        AppUtil.getInstance().runOnUiThread {
            parentLayout.removeView(textView)
            connectionStatusTextViewRef = null
            parentLayoutRef = null
        }
    }
}
