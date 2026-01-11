package com.example.proditi.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.proditi.modeldata.Kategori
import com.example.proditi.repositori.PeminjamanRepository
import com.example.proditi.uicontroller.route.DestinasiKategoriDetail
import kotlinx.coroutines.launch

sealed interface KategoriDetailUiState {
    data class Success(val kategori: Kategori) : KategoriDetailUiState
    object Error : KategoriDetailUiState
    object Loading : KategoriDetailUiState
}

class KategoriDetailViewModel(
    savedStateHandle: SavedStateHandle,
    private val repository: PeminjamanRepository
) : ViewModel() {
    // Perbaikan pengambilan ID: ambil sebagai string dulu lalu konversi ke Int
    private val kategoriId: Int = checkNotNull(savedStateHandle[DestinasiKategoriDetail.kategoriId]).toString().toInt()

    var detailUiState: KategoriDetailUiState by mutableStateOf(KategoriDetailUiState.Loading)
        private set

    init {
        getKategoriById()
    }

    fun getKategoriById() {
        viewModelScope.launch {
            detailUiState = KategoriDetailUiState.Loading
            detailUiState = try {
                val kategori = repository.getKategoriById(kategoriId)
                KategoriDetailUiState.Success(kategori)
            } catch (e: Exception) {
                android.util.Log.e("KategoriDetail", "Error: ${e.message}")
                KategoriDetailUiState.Error
            }
        }
    }

    fun deleteKategori() {
        viewModelScope.launch {
            try {
                repository.deleteKategori(kategoriId)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}