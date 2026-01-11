package com.example.proditi.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.proditi.modeldata.Kategori
import com.example.proditi.repositori.PeminjamanRepository
import kotlinx.coroutines.launch

sealed interface KategoriHomeUiState {
    data class Success(val kategori: List<Kategori>) : KategoriHomeUiState
    object Error : KategoriHomeUiState
    object Loading : KategoriHomeUiState
}

class KategoriHomeViewModel(private val repository: PeminjamanRepository) : ViewModel() {
    var kategoriUiState: KategoriHomeUiState by mutableStateOf(KategoriHomeUiState.Loading)
        private set

    init {
        getKategori()
    }

    fun getKategori() {
        viewModelScope.launch {
            kategoriUiState = KategoriHomeUiState.Loading
            kategoriUiState = try {
                KategoriHomeUiState.Success(repository.getKategori())
            } catch (e: Exception) {
                KategoriHomeUiState.Error
            }
        }
    }
}

data class KategoriEntryUiState(
    val id: Int = 0,
    val namaKategori: String = ""
)

class KategoriEntryViewModel(private val repository: PeminjamanRepository) : ViewModel() {
    var uiState by mutableStateOf(KategoriEntryUiState())
        private set

    fun updateUiState(newUiState: KategoriEntryUiState) {
        uiState = newUiState
    }

    suspend fun saveKategori() {
        val kategori = Kategori(
            id = 0,
            namaKategori = uiState.namaKategori
        )
        repository.insertKategori(kategori)
    }
}
