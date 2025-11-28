package com.parris.joshtap

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.parris.joshtap.data.AppDatabase
import com.parris.joshtap.data.AppRepository
import kotlinx.coroutines.launch

class LibraryViewModel(application: Application) : AndroidViewModel(application) {
    private val db = AppDatabase.getInstance(application)
    private val repo = AppRepository(db)
    private val _tracks = MutableLiveData<List<com.parris.joshtap.data.TrackEntity>>(emptyList())
    val tracks: LiveData<List<com.parris.joshtap.data.TrackEntity>> = _tracks

    init {
        viewModelScope.launch {
            val existing = repo.listTracks()
            if (existing.isEmpty()) {
                importDemoTracks()
            } else {
                _tracks.postValue(existing)
            }
        }
    }

    private suspend fun importDemoTracks() {
        // Generate short demo tones programmatically so demo works without assets
        val uri1 = AudioRepo.generateDemoTone(getApplication(), durationSeconds = 6, frequency = 440.0)
        val uri2 = AudioRepo.generateDemoTone(getApplication(), durationSeconds = 8, frequency = 523.25)
        val uri3 = AudioRepo.generateDemoTone(getApplication(), durationSeconds = 7, frequency = 659.25)

        repo.insertTrack("Demo Piano A4", uri1, 6_000L)
        repo.insertTrack("Demo Piano C5", uri2, 8_000L)
        repo.insertTrack("Demo Piano E5", uri3, 7_000L)
        _tracks.postValue(repo.listTracks())
    }

    fun importDemo(onComplete: (Long, String) -> Unit) {
        viewModelScope.launch {
            val uriString = AudioRepo.generateDemoTone(getApplication())
            val id = repo.insertTrack("Demo Tone", uriString)
            onComplete(id, uriString)
            // update list
            _tracks.postValue(repo.listTracks())
        }
    }

    fun loadTracks() {
        viewModelScope.launch {
            _tracks.postValue(repo.listTracks())
        }
    }

    fun addTrack(displayName: String, uriString: String, durationMs: Long = 0L, onComplete: ((Long) -> Unit)? = null) {
        viewModelScope.launch {
            val id = repo.insertTrack(displayName, uriString, durationMs)
            _tracks.postValue(repo.listTracks())
            onComplete?.invoke(id)
        }
    }
}
