package com.parris.yotolite

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

object PlayerController {
    private const val TAG = "PlayerController"
    private var player: ExoPlayer? = null
    private var listener: PlayerEventListener? = null

    private val _isPlaying = MutableStateFlow(false)
    val isPlaying: StateFlow<Boolean> = _isPlaying

    private val _currentTitle = MutableStateFlow<String?>(null)
    val currentTitle: StateFlow<String?> = _currentTitle

    private val _currentIndex = MutableStateFlow(0)
    val currentIndex: StateFlow<Int> = _currentIndex

    private val _duration = MutableStateFlow(0L)
    val duration: StateFlow<Long> = _duration

    private val _position = MutableStateFlow(0L)
    val position: StateFlow<Long> = _position

    private val _hasNext = MutableStateFlow(false)
    val hasNext: StateFlow<Boolean> = _hasNext

    private val _hasPrevious = MutableStateFlow(false)
    val hasPrevious: StateFlow<Boolean> = _hasPrevious

    fun initialize(context: Context) {
        if (player == null) {
            player = ExoPlayer.Builder(context).build()
            setupPlayerListener()
            Log.d(TAG, "ExoPlayer initialized")
        }
    }

    fun release() {
        player?.removeListener(listener)
        player?.release()
        player = null
        Log.d(TAG, "ExoPlayer released")
    }

    fun playTracks(uris: List<Uri>) {
        val p = player ?: return
        if (uris.isEmpty()) return

        val items = uris.map { MediaItem.fromUri(it) }
        p.setMediaItems(items)
        p.prepare()
        p.play()

        _currentIndex.value = 0
        _currentTitle.value = uris[0].lastPathSegment ?: "Track 1"
        _isPlaying.value = true
        _hasNext.value = uris.size > 1

        Log.d(TAG, "Playing ${uris.size} tracks")
    }

    fun play() {
        player?.play()
        _isPlaying.value = true
    }

    fun pause() {
        player?.pause()
        _isPlaying.value = false
    }

    fun next() {
        val p = player ?: return
        if (p.hasNext()) {
            p.seekToNext()
            _currentIndex.value = p.currentMediaItemIndex
            _currentTitle.value = p.currentMediaItem?.localConfiguration?.uri?.lastPathSegment ?: "Track ${_currentIndex.value + 1}"
            _hasNext.value = p.hasNext()
            _hasPrevious.value = p.hasPrevious()
        }
    }

    fun prev() {
        val p = player ?: return
        if (p.hasPrevious()) {
            p.seekToPrevious()
            _currentIndex.value = p.currentMediaItemIndex
            _currentTitle.value = p.currentMediaItem?.localConfiguration?.uri?.lastPathSegment ?: "Track ${_currentIndex.value + 1}"
            _hasNext.value = p.hasNext()
            _hasPrevious.value = p.hasPrevious()
        }
    }

    fun seekTo(positionMs: Long) {
        player?.seekTo(positionMs)
    }

    fun getCurrentPlayer(): ExoPlayer? = player

    private fun setupPlayerListener() {
        listener = PlayerEventListener()
        player?.addListener(listener!!)
    }

    private inner class PlayerEventListener : Player.Listener {
        override fun onPlaybackStateChanged(state: Int) {
            _isPlaying.value = state == Player.STATE_READY && player?.isPlaying == true
            when (state) {
                Player.STATE_READY -> {
                    _duration.value = player?.duration ?: 0L
                    Log.d(TAG, "Player ready, duration: ${_duration.value}ms")
                }
                Player.STATE_ENDED -> {
                    Log.d(TAG, "Playback ended")
                }
            }
        }

        override fun onIsPlayingChanged(isPlaying: Boolean) {
            _isPlaying.value = isPlaying
            Log.d(TAG, "Is playing: $isPlaying")
        }

        override fun onMediaItemTransition(mediaItem: MediaItem?, reason: Int) {
            val p = player ?: return
            _currentIndex.value = p.currentMediaItemIndex
            _currentTitle.value = mediaItem?.localConfiguration?.uri?.lastPathSegment ?: "Track ${_currentIndex.value + 1}"
            _hasNext.value = p.hasNext()
            _hasPrevious.value = p.hasPrevious()
            Log.d(TAG, "Media item transition to: ${_currentTitle.value}")
        }

        override fun onPositionDiscontinuity(
            oldPosition: Player.PositionInfo,
            newPosition: Player.PositionInfo,
            reason: Int
        ) {
            _position.value = newPosition.positionMs
        }
    }
}
