package com.parris.joshtap

import android.content.Context
import android.net.Uri
import com.parris.joshtap.util.AppLog
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import androidx.media3.common.PlaybackException

object PlayerController {
    private const val TAG = "PlayerController"
    private var player: ExoPlayer? = null
    private var listener: Player.Listener? = null
    private val scope = CoroutineScope(Dispatchers.Main + Job())
    private var positionJob: Job? = null
    private var isUpdating = false

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    private val _isUpdatingPosition = MutableStateFlow(false)
    val isUpdatingPosition: StateFlow<Boolean> = _isUpdatingPosition

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
            // do not auto-start position updates here; allow resumeUpdates() to control
            AppLog.d(TAG, "ExoPlayer initialized")
        }
    }

    fun release() {
        stopPositionUpdates()
        listener?.let { player?.removeListener(it) }
        player?.release()
        player = null
        positionJob = null
        _errorMessage.value = null
        _isUpdatingPosition.value = false
        scope.coroutineContext[Job]?.cancel()
        AppLog.d(TAG, "ExoPlayer released")
    }

    fun playTracks(uris: List<Uri>) {
        val p = player ?: return
        if (uris.isEmpty()) {
            AppLog.w(TAG, "playTracks called with empty list")
            _errorMessage.value = "No tracks to play"
            return
        }

        val items = uris.map { MediaItem.fromUri(it) }
        p.setMediaItems(items)
        p.prepare()
        p.play()

        _currentIndex.value = 0
        _currentTitle.value = uris[0].lastPathSegment ?: "Track 1"
        _isPlaying.value = true
        _hasNext.value = uris.size > 1

        AppLog.d(TAG, "Playing ${uris.size} tracks")

        // when starting playback ensure updates are running
        resumeUpdates()
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
        if (p.hasNextMediaItem()) {
            p.seekToNext()
            _currentIndex.value = p.currentMediaItemIndex
            _currentTitle.value = p.currentMediaItem?.localConfiguration?.uri?.lastPathSegment ?: "Track ${_currentIndex.value + 1}"
            _hasNext.value = p.hasNextMediaItem()
            _hasPrevious.value = p.hasPreviousMediaItem()
        }
    }

    fun prev() {
        val p = player ?: return
        if (p.hasPreviousMediaItem()) {
            p.seekToPrevious()
            _currentIndex.value = p.currentMediaItemIndex
            _currentTitle.value = p.currentMediaItem?.localConfiguration?.uri?.lastPathSegment ?: "Track ${_currentIndex.value + 1}"
            _hasNext.value = p.hasNextMediaItem()
            _hasPrevious.value = p.hasPreviousMediaItem()
        }
    }

    fun seekTo(positionMs: Long) {
        player?.seekTo(positionMs)
    }

    fun getCurrentPlayer(): ExoPlayer? = player

    private fun setupPlayerListener() {
        listener = object : Player.Listener {
            override fun onPlayerError(error: PlaybackException) {
                try {
                    AppLog.e(TAG, "Playback error: ${error.message}")
                    _errorMessage.value = "Problem playing this track. Skipping..."
                    val p = player
                    if (p != null && p.hasNextMediaItem()) {
                        p.seekToNext()
                    } else {
                        p?.pause()
                        _isPlaying.value = false
                    }
                } catch (t: Throwable) {
                    AppLog.e(TAG, "Error handling playback error: ${t.message}")
                }
            }

            override fun onPlaybackStateChanged(state: Int) {
                _isPlaying.value = state == Player.STATE_READY && player?.isPlaying == true
                when (state) {
                    Player.STATE_READY -> {
                        _duration.value = player?.duration ?: 0L
                        AppLog.d(TAG, "Player ready, duration: ${_duration.value}ms")
                    }
                    Player.STATE_ENDED -> {
                        AppLog.d(TAG, "Playback ended")
                    }
                }
            }

            override fun onIsPlayingChanged(isPlaying: Boolean) {
                _isPlaying.value = isPlaying
                AppLog.d(TAG, "Is playing: $isPlaying")
            }

            override fun onMediaItemTransition(mediaItem: MediaItem?, reason: Int) {
                val p = player ?: return
                _currentIndex.value = p.currentMediaItemIndex
                _currentTitle.value = mediaItem?.localConfiguration?.uri?.lastPathSegment ?: "Track ${_currentIndex.value + 1}"
                _hasNext.value = p.hasNextMediaItem()
                _hasPrevious.value = p.hasPreviousMediaItem()
                AppLog.d(TAG, "Media item transition to: ${_currentTitle.value}")
            }
        }

        player?.addListener(listener!!)
    }
    
    private fun startPositionUpdates() {
        stopPositionUpdates()
        _isUpdatingPosition.value = true
        positionJob = scope.launch {
            try {
                while (isActive) {
                    val pos = player?.currentPosition ?: 0L
                    _position.value = pos
                    delay(500)
                }
            } finally {
                _isUpdatingPosition.value = false
            }
        }
    }

    private fun stopPositionUpdates() {
        positionJob?.cancel()
        positionJob = null
        _isUpdatingPosition.value = false
    }

    fun pauseUpdates() {
        stopPositionUpdates()
    }

    fun resumeUpdates() {
        val p = player
        if (p != null && p.isPlaying) {
            startPositionUpdates()
        }
    }

    fun clearError() {
        _errorMessage.value = null
    }
}
