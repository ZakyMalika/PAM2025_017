package com.example.proditi.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.proditi.modeldata.Peminjaman
import com.example.proditi.repositori.PeminjamanRepository
import com.example.proditi.uicontroller.route.DestinasiDetail
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed interface DetailUiState {
    data class Success(val peminjaman: Peminjaman) : DetailUiState
    object Error : DetailUiState
    object Loading : DetailUiState
}

class DetailViewModel(
    savedStateHandle: SavedStateHandle,
    private val repository: PeminjamanRepository
) : ViewModel() {

    private val peminjamanId: Int = checkNotNull(savedStateHandle[DestinasiDetail.peminjamanId]).toString().toInt()

    private val _detailUiState = MutableStateFlow<DetailUiState>(DetailUiState.Loading)
    val detailUiState: StateFlow<DetailUiState> = _detailUiState

    init {
        getById()
    }

    // Fungsi untuk mengambil data berdasarkan ID (Dipanggil oleh LaunchedEffect di UI)
    fun getById() {
        viewModelScope.launch {
            _detailUiState.value = DetailUiState.Loading
            try {
                val data = repository.getPeminjamanById(peminjamanId)
                _detailUiState.value = DetailUiState.Success(data)
            } catch (e: Exception) {
                e.printStackTrace()
                _detailUiState.value = DetailUiState.Error
            }
        }
    }

    // Fungsi delete dengan callback onSuccess untuk navigasi
    fun deletePeminjaman(onSuccess: () -> Unit = {}) {
        viewModelScope.launch {
            try {
                repository.deletePeminjaman(peminjamanId)
                onSuccess() // Navigasi kembali hanya jika delete berhasil
            } catch (e: Exception) {
                e.printStackTrace()
                _detailUiState.value = DetailUiState.Error
            }
        }
    }
}