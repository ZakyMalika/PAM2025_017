package com.example.proditi.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.proditi.modeldata.Peminjam
import com.example.proditi.repositori.PeminjamanRepository
import com.example.proditi.uicontroller.route.DestinasiPeminjamEdit
import kotlinx.coroutines.launch

class PeminjamEditViewModel(
    savedStateHandle: SavedStateHandle,
    private val repository: PeminjamanRepository
) : ViewModel() {
    private val peminjamId: Int = checkNotNull(savedStateHandle[DestinasiPeminjamEdit.peminjamId])

    var uiState by mutableStateOf(PeminjamEntryUiState())
        private set

    init {
        viewModelScope.launch {
            val peminjam = repository.getPeminjamById(peminjamId)
            uiState = PeminjamEntryUiState(
                id = peminjam.id,
                namaPeminjam = peminjam.namaPeminjam,
                nimNik = peminjam.nimNik,
                noHp = peminjam.noHp
            )
        }
    }

    fun updateUiState(newUiState: PeminjamEntryUiState) {
        uiState = newUiState
    }

    suspend fun updatePeminjam() {
        val peminjam = Peminjam(
            id = peminjamId,
            namaPeminjam = uiState.namaPeminjam,
            nimNik = uiState.nimNik,
            noHp = uiState.noHp
        )
        repository.updatePeminjam(peminjamId, peminjam)
    }
}
