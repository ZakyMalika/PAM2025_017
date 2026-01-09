package com.example.proditi.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.proditi.modeldata.Barang
import com.example.proditi.repositori.PeminjamanRepository
import com.example.proditi.uicontroller.route.DestinasiBarangEdit
import kotlinx.coroutines.launch

class BarangEditViewModel(
    savedStateHandle: SavedStateHandle,
    private val repository: PeminjamanRepository
) : ViewModel() {
    private val barangId: Int = checkNotNull(savedStateHandle[DestinasiBarangEdit.barangId])

    var uiState by mutableStateOf(BarangEntryUiState())
        private set

    init {
        viewModelScope.launch {
            val barang = repository.getBarangById(barangId)
            uiState = BarangEntryUiState(
                id = barang.id,
                namaBarang = barang.namaBarang,
                kondisi = barang.kondisi,
                kategoriId = barang.kategoriId.toString()
            )
        }
    }

    fun updateUiState(newUiState: BarangEntryUiState) {
        uiState = newUiState
    }

    suspend fun updateBarang() {
        val barang = Barang(
            id = barangId,
            namaBarang = uiState.namaBarang,
            kondisi = uiState.kondisi,
            kategoriId = uiState.kategoriId.toIntOrNull() ?: 0
        )
        repository.updateBarang(barangId, barang)
    }
}
