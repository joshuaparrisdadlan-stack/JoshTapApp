package com.parris.joshtap.util

import android.util.Log
import java.util.concurrent.atomic.AtomicInteger

/**
 * AppLog: lightweight logger with an in-memory ring buffer for quick diagnostics.
 */
object AppLog {
    private const val TAG = "YotoLite"
    private const val MAX_ENTRIES = 500
    private val buffer = Array<String?>(MAX_ENTRIES) { null }
    private val idx = AtomicInteger(0)

    private fun add(entry: String) {
        val i = idx.getAndIncrement() % MAX_ENTRIES
        buffer[i] = entry
    }

    fun d(tag: String = TAG, msg: String) {
        add("D/$tag: $msg")
        Log.d(tag, msg)
    }

    fun w(tag: String = TAG, msg: String, t: Throwable? = null) {
        add("W/$tag: $msg ${t?.message ?: ""}")
        if (t != null) Log.w(tag, msg, t) else Log.w(tag, msg)
    }

    fun e(tag: String = TAG, msg: String, t: Throwable? = null) {
        add("E/$tag: $msg ${t?.message ?: ""}")
        if (t != null) Log.e(tag, msg, t) else Log.e(tag, msg)
    }

    fun i(tag: String = TAG, msg: String) {
        add("I/$tag: $msg")
        Log.i(tag, msg)
    }

    fun getBufferedLogs(): List<String> {
        return buffer.filterNotNull().toList()
    }
}
