package com.parris.joshtap

import android.content.Context
import java.io.File
import java.io.FileOutputStream
import kotlin.math.sin

object AudioRepo {
    // Generate a short sine-wave WAV file and return its file:// URI string
    fun generateDemoTone(context: Context, durationSeconds: Int = 2): String {
        val sampleRate = 44100
        val numSamples = durationSeconds * sampleRate
        val frequency = 880.0 // A5 tone
        val amplitude = 32767

        val pcm = ShortArray(numSamples)
        for (i in 0 until numSamples) {
            val t = i.toDouble() / sampleRate
            val value = (amplitude * sin(2.0 * Math.PI * frequency * t)).toInt()
            pcm[i] = value.toShort()
        }

        val file = File(context.filesDir, "demo_tone.wav")
        FileOutputStream(file).use { out ->
            // WAV header
            val byteRate = 16 * sampleRate / 8
            writeString(out, "RIFF")
            writeInt(out, 36 + numSamples * 2)
            writeString(out, "WAVE")
            writeString(out, "fmt ")
            writeInt(out, 16)
            writeShort(out, 1.toShort()) // PCM
            writeShort(out, 1.toShort()) // mono
            writeInt(out, sampleRate)
            writeInt(out, sampleRate * 2)
            writeShort(out, (2).toShort())
            writeShort(out, 16.toShort())
            writeString(out, "data")
            writeInt(out, numSamples * 2)
            // PCM data
            val buffer = ByteArray(2)
            for (s in pcm) {
                buffer[0] = (s.toInt() and 0x00FF).toByte()
                buffer[1] = ((s.toInt() shr 8) and 0x00FF).toByte()
                out.write(buffer)
            }
            out.flush()
        }

        return file.toURI().toString()
    }

    private fun writeInt(out: FileOutputStream, value: Int) {
        out.write(value and 0xFF)
        out.write((value shr 8) and 0xFF)
        out.write((value shr 16) and 0xFF)
        out.write((value shr 24) and 0xFF)
    }

    private fun writeShort(out: FileOutputStream, value: Short) {
        out.write((value.toInt() and 0xFF))
        out.write((value.toInt() shr 8) and 0xFF)
    }

    private fun writeString(out: FileOutputStream, s: String) {
        out.write(s.toByteArray(Charsets.US_ASCII))
    }
}
