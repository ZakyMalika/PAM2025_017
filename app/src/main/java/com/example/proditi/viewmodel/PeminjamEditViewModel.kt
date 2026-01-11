package com.example.proditi.viewmodel.peminjam

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

    // Mengambil ID Peminjam dari argumen navigasi
    private val id: Int = checkNotNull(savedStateHandle[DestinasiPeminjamEdit.peminjamId])

    // State form input (menggunakan nim dan noHp)
    var uiState by mutableStateOf(PeminjamEntryUiState())
        private set

    init {
        loadPeminjam()
    }

    // Mengambil data lama dari server untuk ditampilkan di form
    private fun loadPeminjam() {
        viewModelScope.launch {
            try {
                val data = repository.getPeminjamById(id)
                uiState = PeminjamEntryUiState(
                    namaPeminjam = data.namaPeminjam,
                    nim = data.nim,   // Pastikan di DataPeminjaman.kt sudah menggunakan @SerialName("nim_nik")
                    noHp = data.noHp  // Pastikan di DataPeminjaman.kt sudah menggunakan @SerialName("no_hp")
                )
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    // Update tampilan saat user mengetik
    fun updateUiState(newState: PeminjamEntryUiState) {
        uiState = newState
    }

    // Simpan perubahan ke database
    fun updatePeminjam(onSuccess: () -> Unit) {
        viewModelScope.launch {
            try {
                repository.updatePeminjam(
                    id,
                    Peminjam(
                        id = id,
                        namaPeminjam = uiState.namaPeminjam,
                        nim = uiState.nim,
                        noHp = uiState.noHp
                    )
                )
                onSuccess()
            } catch (e: Exception) {
                e.printStackTrace()
                println("Gagal update peminjam: ${e.message}")
            }
        }
    }
}