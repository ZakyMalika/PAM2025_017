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
        getPeminjaman()
    }

    fun getPeminjaman() {
        viewModelScope.launch {
            _detailUiState.value = DetailUiState.Loading
            _detailUiState.value = try {
                val data = repository.getPeminjamanById(peminjamanId)
                DetailUiState.Success(data)
            } catch (e: Exception) {
                DetailUiState.Error
            }
        }
    }

    fun deletePeminjaman() {
        viewModelScope.launch {
            try {
                repository.deletePeminjaman(peminjamanId)
            } catch (e: Exception) {
                _detailUiState.value = DetailUiState.Error
            }
        }
    }
}