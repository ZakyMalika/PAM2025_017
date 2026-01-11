package com.example.proditi.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.proditi.modeldata.Kategori
import com.example.proditi.repositori.PeminjamanRepository
import com.example.proditi.uicontroller.route.DestinasiKategoriEdit
import kotlinx.coroutines.launch

class KategoriEditViewModel(
    savedStateHandle: SavedStateHandle,
    private val repository: PeminjamanRepository
) : ViewModel() {
    // Perbaikan pengambilan ID
    private val kategoriId: Int = checkNotNull(savedStateHandle[DestinasiKategoriEdit.kategoriId]).toString().toInt()

    var uiState by mutableStateOf(KategoriEntryUiState())
        private set

    init {
        viewModelScope.launch {
            try {
                val kategori = repository.getKategoriById(kategoriId)
                uiState = KategoriEntryUiState(
                    id = kategori.id,
                    namaKategori = kategori.namaKategori
                )
            } catch (e: Exception) {
                android.util.Log.e("KategoriEdit", "Error loading: ${e.message}")
            }
        }
    }

    fun updateUiState(newUiState: KategoriEntryUiState) {
        uiState = newUiState
    }

    suspend fun updateKategori() {
        try {
            val kategori = Kategori(
                id = kategoriId,
                namaKategori = uiState.namaKategori
            )
            repository.updateKategori(kategoriId, kategori)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}