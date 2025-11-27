package com.parris.joshtap

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.parris.joshtap.data.AppDatabase
import com.parris.joshtap.data.AppRepository
import kotlinx.coroutines.launch
import java.util.Base64
import java.security.SecureRandom
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.parris.joshtap.data.CardEntity

class CardsViewModel(application: Application) : AndroidViewModel(application) {
    private val db = AppDatabase.getInstance(application)
    private val repo = AppRepository(db)

    private val rng = SecureRandom()

    fun generateToken(length: Int = 12): String {
        val bytes = ByteArray(length)
        rng.nextBytes(bytes)
        // URL-safe base64 without padding
        return Base64.getUrlEncoder().withoutPadding().encodeToString(bytes).substring(0, length)
    }

    fun createCardWithTrack(name: String, trackId: Long, onComplete: (Long, String) -> Unit) {
        viewModelScope.launch {
            val token = generateToken()
            val cardId = repo.insertCard(name, token)
            repo.setCardTracks(cardId, listOf(trackId))
            onComplete(cardId, token)
        }
    }

    private val _cards = MutableLiveData<List<CardEntity>>(emptyList())
    val cards: LiveData<List<CardEntity>> = _cards

    fun loadCards() {
        viewModelScope.launch {
            _cards.postValue(repo.listCards())
        }
    }

    // Convenience: create card by assigning first available track
    fun createCardUsingFirstTrack(name: String, onComplete: (Long?, String?) -> Unit) {
        viewModelScope.launch {
            val tracks = repo.listTracks()
            if (tracks.isEmpty()) {
                onComplete(null, null)
                return@launch
            }
            val trackId = tracks[0].id
            val token = generateToken()
            val cardId = repo.insertCard(name, token)
            repo.setCardTracks(cardId, listOf(trackId))
            // refresh list
            _cards.postValue(repo.listCards())
            onComplete(cardId, token)
        }
    }
}
