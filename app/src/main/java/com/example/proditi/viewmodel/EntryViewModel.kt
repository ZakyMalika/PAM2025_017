package com.example.proditi.viewmodel

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.proditi.modeldata.*
import com.example.proditi.repositori.PeminjamanRepository
import kotlinx.coroutines.launch

class EntryViewModel(private val repository: PeminjamanRepository) : ViewModel() {
    var uiStatePeminjaman by mutableStateOf(PeminjamanUiState())
        private set

    var listBarang by mutableStateOf<List<Barang>>(emptyList())
    var listPeminjam by mutableStateOf<List<Peminjam>>(emptyList())

    init {
        loadDropdownData()
    }

    private fun loadDropdownData() {
        viewModelScope.launch {
            try {
                listBarang = repository.getBarang()
                listPeminjam = repository.getPeminjam()
            } catch (e: Exception) { /* Handle Error */ }
        }
    }

    fun updateUiState(detailPeminjaman: DetailPeminjaman) {
        uiStatePeminjaman = PeminjamanUiState(detailPeminjaman = detailPeminjaman, isEntryValid = validateInput(detailPeminjaman))
    }

    private fun validateInput(uiState: DetailPeminjaman = uiStatePeminjaman.detailPeminjaman): Boolean {
        return with(uiState) {
            tanggalPinjam.isNotBlank() && tanggalKembali.isNotBlank() && barangId != 0 && peminjamId != 0
        }
    }

    suspend fun savePeminjaman() {
        if (validateInput()) {
            repository.insertPeminjaman(uiStatePeminjaman.detailPeminjaman.toPeminjaman())
        }
    }
}

data class PeminjamanUiState(
    val detailPeminjaman: DetailPeminjaman = DetailPeminjaman(),
    val isEntryValid: Boolean = false
)

data class DetailPeminjaman(
    val id: Int = 0,
    val tanggalPinjam: String = "",
    val tanggalKembali: String = "",
    val barangId: Int = 0,
    val peminjamId: Int = 0
)

fun DetailPeminjaman.toPeminjaman(): Peminjaman = Peminjaman(
    id = id,
    tanggalPinjam = tanggalPinjam,
    tanggalKembali = tanggalKembali,
    barangId = barangId,
    peminjamId = peminjamId
)