package com.example.proditi.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.proditi.modeldata.Barang
import com.example.proditi.repositori.PeminjamanRepository
import kotlinx.coroutines.launch

sealed interface BarangHomeUiState {
    data class Success(val barang: List<Barang>) : BarangHomeUiState
    object Error : BarangHomeUiState
    object Loading : BarangHomeUiState
}

class BarangHomeViewModel(private val repository: PeminjamanRepository) : ViewModel() {
    var barangUiState: BarangHomeUiState by mutableStateOf(BarangHomeUiState.Loading)
        private set

    init {
        getBarang()
    }

    fun getBarang() {
        viewModelScope.launch {
            barangUiState = BarangHomeUiState.Loading
            barangUiState = try {
                BarangHomeUiState.Success(repository.getBarang())
            } catch (e: Exception) {
                BarangHomeUiState.Error
            }
        }
    }
}

data class BarangEntryUiState(
    val id: Int = 0,
    val namaBarang: String = "",
    val kondisi: String = "",
    val kategoriId: String = ""
)

class BarangEntryViewModel(private val repository: PeminjamanRepository) : ViewModel() {
    var uiState by mutableStateOf(BarangEntryUiState())
        private set

    fun updateUiState(newUiState: BarangEntryUiState) {
        uiState = newUiState
    }

    suspend fun saveBarang() {
        val barang = Barang(
            id = 0,
            namaBarang = uiState.namaBarang,
            kondisi = uiState.kondisi,
            kategoriId = uiState.kategoriId.toIntOrNull() ?: 0
        )
        repository.insertBarang(barang)
    }
}
