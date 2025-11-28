package com.parris.joshtap.nfc

import android.nfc.Tag
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.parris.joshtap.PlayerController
import com.parris.joshtap.data.AppRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import androidx.lifecycle.ViewModelProvider


sealed class NfcUiState {
    object Idle : NfcUiState()
    data class WaitingForWrite(val cardId: Long) : NfcUiState()
    object WaitingForScan : NfcUiState()
    data class WriteSuccess(val cardId: Long) : NfcUiState()
    data class ScanSuccess(val cardId: Long) : NfcUiState()
    data class Error(val message: String) : NfcUiState()
}

class NfcViewModel(private val repo: AppRepository) : ViewModel() {
    private val _state = MutableStateFlow<NfcUiState>(NfcUiState.Idle)
    val state: StateFlow<NfcUiState> = _state

    fun startWrite(cardId: Long) {
        _state.value = NfcUiState.WaitingForWrite(cardId)
    }

    fun startScan() {
        _state.value = NfcUiState.WaitingForScan
    }

    fun reset() {
        _state.value = NfcUiState.Idle
    }

    fun onTag(tag: Tag) {
        val current = _state.value
        when (current) {
            is NfcUiState.WaitingForWrite -> {
                val cardId = current.cardId
                viewModelScope.launch {
                    val token = repo.getOrCreateTokenForCard(cardId)
                    when (val res = NfcHandler.writeToken(tag, token)) {
                        is NfcHandler.NfcWriteResult.Success -> {
                            _state.value = NfcUiState.WriteSuccess(cardId)
                        }
                        is NfcHandler.NfcWriteResult.Error -> {
                            _state.value = NfcUiState.Error(res.message)
                        }
                    }
                }
            }
            is NfcUiState.WaitingForScan -> {
                viewModelScope.launch {
                    when (val r = NfcHandler.readToken(tag)) {
                        is NfcHandler.NfcReadResult.Success -> {
                            val token = r.token
                            val card = repo.getCardByNfcToken(token)
                            if (card != null) {
                                // play card tracks
                                val uris = card.tracks.mapNotNull { try { android.net.Uri.parse(it.localUri) } catch (_: Exception) { null } }
                                if (uris.isNotEmpty()) {
                                    PlayerController.playTracks(uris)
                                    _state.value = NfcUiState.ScanSuccess(card.card.id)
                                } else {
                                    _state.value = NfcUiState.Error("Card has no playable tracks")
                                }
                            } else {
                                _state.value = NfcUiState.Error("Card not found for token: $token")
                            }
                        }
                        is NfcHandler.NfcReadResult.Error -> {
                            _state.value = NfcUiState.Error(r.message)
                        }
                    }
                }
            }
            else -> {
                // ignore
            }
        }
    }
}

class NfcViewModelFactory(private val repo: AppRepository) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : androidx.lifecycle.ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NfcViewModel::class.java)) {
            return NfcViewModel(repo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
