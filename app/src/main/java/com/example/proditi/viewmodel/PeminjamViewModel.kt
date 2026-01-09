package com.example.proditi.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.proditi.modeldata.Peminjam
import com.example.proditi.repositori.PeminjamanRepository
import kotlinx.coroutines.launch

sealed interface PeminjamHomeUiState {
    data class Success(val peminjam: List<Peminjam>) : PeminjamHomeUiState
    object Error : PeminjamHomeUiState
    object Loading : PeminjamHomeUiState
}

class PeminjamHomeViewModel(private val repository: PeminjamanRepository) : ViewModel() {
    var peminjamUiState: PeminjamHomeUiState by mutableStateOf(PeminjamHomeUiState.Loading)
        private set

    init {
        getPeminjam()
    }

    fun getPeminjam() {
        viewModelScope.launch {
            peminjamUiState = PeminjamHomeUiState.Loading
            peminjamUiState = try {
                PeminjamHomeUiState.Success(repository.getPeminjam())
            } catch (e: Exception) {
                PeminjamHomeUiState.Error
            }
        }
    }
}

data class PeminjamEntryUiState(
    val id: Int = 0,
    val namaPeminjam: String = "",
    val nimNik: String = "",
    val noHp: String = ""
)

class PeminjamEntryViewModel(private val repository: PeminjamanRepository) : ViewModel() {
    var uiState by mutableStateOf(PeminjamEntryUiState())
        private set

    fun updateUiState(newUiState: PeminjamEntryUiState) {
        uiState = newUiState
    }

    suspend fun savePeminjam() {
        val peminjam = Peminjam(
            id = 0,
            namaPeminjam = uiState.namaPeminjam,
            nimNik = uiState.nimNik,
            noHp = uiState.noHp
        )
        repository.insertPeminjam(peminjam)
    }
}
