package com.example.proditi.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.proditi.modeldata.Peminjam
import com.example.proditi.repositori.PeminjamanRepository
import com.example.proditi.uicontroller.route.DestinasiPeminjamDetail
import kotlinx.coroutines.launch

sealed interface PeminjamDetailUiState {
    data class Success(val peminjam: Peminjam) : PeminjamDetailUiState
    object Error : PeminjamDetailUiState
    object Loading : PeminjamDetailUiState
}

class PeminjamDetailViewModel(
    savedStateHandle: SavedStateHandle,
    private val repository: PeminjamanRepository
) : ViewModel() {
    private val peminjamId: Int = checkNotNull(savedStateHandle[DestinasiPeminjamDetail.peminjamId])

    var detailUiState: PeminjamDetailUiState by mutableStateOf(PeminjamDetailUiState.Loading)
        private set

    init {
        getPeminjamById()
    }

    fun getPeminjamById() {
        viewModelScope.launch {
            detailUiState = PeminjamDetailUiState.Loading
            detailUiState = try {
                PeminjamDetailUiState.Success(repository.getPeminjamById(peminjamId))
            } catch (e: Exception) {
                PeminjamDetailUiState.Error
            }
        }
    }

    fun deletePeminjam() {
        viewModelScope.launch {
            try {
                repository.deletePeminjam(peminjamId)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
