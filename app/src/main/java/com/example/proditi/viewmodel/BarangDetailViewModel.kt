package com.example.proditi.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.proditi.modeldata.Barang
import com.example.proditi.repositori.PeminjamanRepository
import com.example.proditi.uicontroller.route.DestinasiBarangDetail
import kotlinx.coroutines.launch

sealed interface BarangDetailUiState {
    data class Success(val barang: Barang) : BarangDetailUiState
    object Error : BarangDetailUiState
    object Loading : BarangDetailUiState
}

class BarangDetailViewModel(
    savedStateHandle: SavedStateHandle,
    private val repository: PeminjamanRepository
) : ViewModel() {

    // Pastikan key ini sama persis dengan yang ada di DestinasiNavigasi
    private val barangId: Int = checkNotNull(savedStateHandle[DestinasiBarangDetail.barangId])

    var detailUiState: BarangDetailUiState by mutableStateOf(BarangDetailUiState.Loading)
        private set

    init {
        getBarangById()
    }

    fun getBarangById() {
        viewModelScope.launch {
            detailUiState = BarangDetailUiState.Loading
            detailUiState = try {
                // Mencoba mengambil data
                val result = repository.getBarangById(barangId)
                BarangDetailUiState.Success(result)
            } catch (e: Exception) {
                // --- TAMBAHKAN INI UNTUK MELIHAT ERROR DI LOGCAT ---
                e.printStackTrace() // <--- INI PENTING
                println("ERROR DETAIL: ${e.message}")
                // ---------------------------------------------------
                BarangDetailUiState.Error
            }
        }
    }

    fun deleteBarang() {
        viewModelScope.launch {
            try {
                repository.deleteBarang(barangId)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
