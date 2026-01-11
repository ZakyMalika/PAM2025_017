package com.example.proditi.viewmodel.barang

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.proditi.modeldata.Barang
import com.example.proditi.modeldata.Kategori
import com.example.proditi.repositori.PeminjamanRepository
import com.example.proditi.uicontroller.route.DestinasiBarangEdit
import kotlinx.coroutines.launch

class BarangEditViewModel(
    savedStateHandle: SavedStateHandle,
    private val repository: PeminjamanRepository
) : ViewModel() {

    // Ambil ID dari Argument Navigasi
    private val barangId: Int = checkNotNull(savedStateHandle[DestinasiBarangEdit.barangId])

    // State Form Input (Sama seperti Entry)
    var uiState by mutableStateOf(BarangEntryUiState())
        private set

    // List Kategori untuk Dropdown
    var kategoriList by mutableStateOf<List<Kategori>>(emptyList())
        private set

    init {
        loadKategori()
        loadBarangData()
    }

    // 1. Ambil List Kategori untuk Dropdown
    private fun loadKategori() {
        viewModelScope.launch {
            try {
                kategoriList = repository.getKategori()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    // 2. Ambil Data Barang Lama (Pre-fill Form)
    private fun loadBarangData() {
        viewModelScope.launch {
            try {
                val barang = repository.getBarangById(barangId)
                // Masukkan data db ke state UI
                uiState = BarangEntryUiState(
                    namaBarang = barang.namaBarang,
                    kondisi = barang.kondisi,
                    kategoriId = barang.kategoriId
                )
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    // Update state saat ketik
    fun updateUiState(newState: BarangEntryUiState) {
        uiState = newState
    }

    // Simpan Perubahan (UPDATE)
    fun updateBarang(onSuccess: () -> Unit) {
        if (uiState.namaBarang.isNotBlank() && uiState.kondisi.isNotBlank() && uiState.kategoriId != null) {
            viewModelScope.launch {
                try {
                    val barangUpdated = Barang(
                        id = barangId, // ID Lama
                        namaBarang = uiState.namaBarang,
                        kondisi = uiState.kondisi,
                        kategoriId = uiState.kategoriId!!
                    )

                    // Panggil fungsi Update di Repository
                    repository.updateBarang(barangId, barangUpdated)
                    onSuccess()
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }
}