package com.parris.yotolite.debug

import android.util.Log
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 * AppLog maintains a ring buffer of recent log messages for debugging.
 * Useful for exporting debug reports and troubleshooting.
 */
object AppLog {
    private const val MAX_ENTRIES = 500
    private const val TAG = "AppLog"

    private data class LogEntry(
        val timestamp: Long,
        val level: String,
        val tag: String,
        val message: String
    ) {
        fun format(): String {
            val time = SimpleDateFormat("HH:mm:ss.SSS", Locale.US).format(Date(timestamp))
            return "[$time] $level/$tag: $message"
        }
    }

    private val buffer = mutableListOf<LogEntry>()

    fun d(tag: String, message: String) {
        log("D", tag, message)
        Log.d(tag, message)
    }

    fun e(tag: String, message: String, throwable: Throwable? = null) {
        val msg = if (throwable != null) "$message - ${throwable.message}" else message
        log("E", tag, msg)
        Log.e(tag, message, throwable)
    }

    fun w(tag: String, message: String) {
        log("W", tag, message)
        Log.w(tag, message)
    }

    fun i(tag: String, message: String) {
        log("I", tag, message)
        Log.i(tag, message)
    }

    fun exportDebugReport(): String {
        val sb = StringBuilder()
        sb.append("=== YotoLite Debug Report ===\n")
        sb.append("Generated: ${SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US).format(Date())}\n")
        sb.append("Total entries: ${buffer.size}\n")
        sb.append("=== Recent Logs ===\n")

        synchronized(buffer) {
            for (entry in buffer) {
                sb.append(entry.format()).append("\n")
            }
        }

        return sb.toString()
    }

    fun clear() {
        synchronized(buffer) {
            buffer.clear()
        }
    }

    fun getLogs(count: Int = 50): List<String> {
        synchronized(buffer) {
            val start = maxOf(0, buffer.size - count)
            return buffer.subList(start, buffer.size).map { it.format() }
        }
    }

    private fun log(level: String, tag: String, message: String) {
        synchronized(buffer) {
            buffer.add(LogEntry(System.currentTimeMillis(), level, tag, message))
            if (buffer.size > MAX_ENTRIES) {
                buffer.removeAt(0)
            }
        }
    }
}
