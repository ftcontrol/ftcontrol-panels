package com.bylazar.panels

import java.util.Collections
import java.util.concurrent.ConcurrentHashMap

data class TaskTiming(
    val id: String,
    val startNanos: Long,
    val endNanos: Long,
    val durationMillis: Long
)

object TaskTimer {
    val activeStarts = ConcurrentHashMap<String, Long>()
    val records = Collections.synchronizedList(mutableListOf<TaskTiming>())

    init {
        records.clear()
        activeStarts.clear()
        Logger.coreLog("TaskTimer initialized (storage cleared).")
    }

    fun clear() {
        records.clear()
        activeStarts.clear()
        Logger.coreLog("TaskTimer cleared.")
    }

    fun start(id: String) {
        val now = System.nanoTime()
        val prev = activeStarts.put(id, now)
        if (prev != null) {
            Logger.coreError("Timer for '$id' was already running; restarting.")
        } else {
            Logger.coreLog("Timer started for '$id'.")
        }
    }

    fun end(id: String): TaskTiming? {
        val start = activeStarts.remove(id)
        if (start == null) {
            Logger.coreError("Timer end called with unknown id '$id'. Did you call start(id)?")
            return null
        }
        val end = System.nanoTime()
        val durationMs = (end - start) / 1_000_000
        val record = TaskTiming(id, start, end, durationMs)
        records.add(record)
        Logger.coreLog("Task '$id' took ${record.durationMillis} ms.")
        return record
    }

    inline fun <T> measure(id: String, block: () -> T): T {
        val start = System.nanoTime()
        try {
            return block()
        } finally {
            val end = System.nanoTime()
            val durationMs = (end - start) / 1_000_000
            val record = TaskTiming(id, start, end, durationMs)
            records.add(record)
            Logger.coreLog("Task '$id' took ${record.durationMillis} ms.")
        }
    }
}
