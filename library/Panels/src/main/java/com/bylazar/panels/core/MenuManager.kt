package com.bylazar.panels.core

import android.view.Menu
import android.view.MenuItem
import com.bylazar.panels.Logger
import com.bylazar.panels.Panels

object MenuManager {
    private val enableMenuItems: MutableList<MenuItem> = arrayListOf()
    private val disableMenuItems: MutableList<MenuItem> = arrayListOf()

    fun createMenu(menu: Menu) {
        val enable = menu.add(Menu.NONE, Menu.NONE, 700, "Enable Panels")
        val disable = menu.add(Menu.NONE, Menu.NONE, 700, "Disable Panels")

        enable.isVisible = !PreferencesHandler.isEnabled
        disable.isVisible = PreferencesHandler.isEnabled

        synchronized(enableMenuItems) {
            enableMenuItems.add(enable)
        }

        synchronized(disableMenuItems) {
            disableMenuItems.add(disable)
        }

        enable.setOnMenuItemClickListener {
            Panels.enable()
            synchronized(enableMenuItems) {
                for (menuItem in enableMenuItems) {
                    menuItem.isVisible = false
                }
            }

            synchronized(disableMenuItems) {
                for (menuItem in disableMenuItems) {
                    menuItem.isVisible = true
                }
            }
            true
        }

        disable.setOnMenuItemClickListener {
            Panels.disable()
            synchronized(enableMenuItems) {
                for (menuItem in enableMenuItems) {
                    menuItem.isVisible = true
                }
            }

            synchronized(disableMenuItems) {
                for (menuItem in disableMenuItems) {
                    menuItem.isVisible = false
                }
            }
            true
        }

        Logger.coreLog("Created Enable/Disable Menu")
    }
}