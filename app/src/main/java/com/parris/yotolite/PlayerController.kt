package com.parris.yotolite

import android.content.Context
import android.net.Uri
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

object PlayerController {
    private var player: ExoPlayer? = null

    private val _isPlaying = MutableStateFlow(false)
    val isPlaying: StateFlow<Boolean> = _isPlaying

    private val _currentTitle = MutableStateFlow<String?>(null)
    val currentTitle: StateFlow<String?> = _currentTitle

    private val _currentIndex = MutableStateFlow(0)
    val currentIndex: StateFlow<Int> = _currentIndex

    fun initialize(context: Context) {
        if (player == null) {
            player = ExoPlayer.Builder(context).build()
        }
    }

    fun playTracks(uris: List<Uri>) {
        val p = player ?: return
        if (uris.isEmpty()) return
        val items = uris.map { MediaItem.fromUri(it) }
        p.setMediaItems(items)
        p.prepare()
        p.play()
        _currentIndex.value = 0
        _currentTitle.value = uris[0].lastPathSegment
        _isPlaying.value = true
    }

    fun play() { player?.play(); _isPlaying.value = true }
    fun pause() { player?.pause(); _isPlaying.value = false }
    fun next() { player?.seekToNext(); _currentIndex.value += 1 }
    fun prev() { player?.seekToPrevious(); if (_currentIndex.value > 0) _currentIndex.value -= 1 }
}
