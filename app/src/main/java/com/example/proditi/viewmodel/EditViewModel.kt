package com.example.proditi.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.proditi.modeldata.Barang
import com.example.proditi.modeldata.Peminjam
import com.example.proditi.repositori.PeminjamanRepository
import com.example.proditi.uicontroller.route.DestinasiEdit
import kotlinx.coroutines.launch

class EditViewModel(
    savedStateHandle: SavedStateHandle,
    private val repository: PeminjamanRepository
) : ViewModel() {

    var uiStatePeminjaman by mutableStateOf(PeminjamanUiState())
        private set

    var listBarang by mutableStateOf<List<Barang>>(emptyList())
    var listPeminjam by mutableStateOf<List<Peminjam>>(emptyList())

    private val peminjamanId: Int = checkNotNull(savedStateHandle[DestinasiEdit.peminjamanId]).toString().toInt()

    init {
        loadDropdownData()
        loadPeminjaman()
    }

    private fun loadDropdownData() {
        viewModelScope.launch {
            try {
                listBarang = repository.getBarang()
                listPeminjam = repository.getPeminjam()
            } catch (e: Exception) { }
        }
    }

    private fun loadPeminjaman() {
        viewModelScope.launch {
            try {
                val data = repository.getPeminjamanById(peminjamanId)
                uiStatePeminjaman = PeminjamanUiState(
                    detailPeminjaman = DetailPeminjaman(
                        id = data.id,
                        tanggalPinjam = data.tanggalPinjam,
                        tanggalKembali = data.tanggalKembali,
                        barangId = data.barangId,
                        peminjamId = data.peminjamId
                    ),
                    isEntryValid = true
                )
            } catch (e: Exception) { }
        }
    }

    fun updateUiState(detailPeminjaman: DetailPeminjaman) {
        uiStatePeminjaman = PeminjamanUiState(detailPeminjaman = detailPeminjaman, isEntryValid = true)
    }

    suspend fun updatePeminjaman() {
        repository.updatePeminjaman(peminjamanId, uiStatePeminjaman.detailPeminjaman.toPeminjaman())
    }
}