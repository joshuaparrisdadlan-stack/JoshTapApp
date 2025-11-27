package com.parris.yotolite

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.parris.yotolite.data.AppDatabase
import com.parris.yotolite.data.AppRepository
import kotlinx.coroutines.launch

class LibraryViewModel(application: Application) : AndroidViewModel(application) {
    private val db = AppDatabase.getInstance(application)
    private val repo = AppRepository(db)
    private val _tracks = MutableLiveData<List<com.parris.yotolite.data.TrackEntity>>(emptyList())
    val tracks: LiveData<List<com.parris.yotolite.data.TrackEntity>> = _tracks

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
}
