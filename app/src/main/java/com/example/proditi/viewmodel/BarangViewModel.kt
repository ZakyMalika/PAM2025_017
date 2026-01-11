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
import com.example.proditi.uicontroller.route.DestinasiBarangDetail
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

// --- 1. VIEW MODEL HOME (LIST & DELETE) ---
sealed interface BarangUiState {
    data class Success(val barang: List<Barang>) : BarangUiState
    object Error : BarangUiState
    object Loading : BarangUiState
}

class BarangHomeViewModel(private val repository: PeminjamanRepository) : ViewModel() {
    var barangUiState: BarangUiState by mutableStateOf(BarangUiState.Loading)
        private set

    init {
        getBarang()
    }

    fun getBarang() {
        viewModelScope.launch {
            barangUiState = BarangUiState.Loading
            barangUiState = try {
                BarangUiState.Success(repository.getBarang())
            } catch (e: IOException) {
                BarangUiState.Error
            } catch (e: HttpException) {
                BarangUiState.Error
            }
        }
    }

    fun deleteBarang(id: Int) {
        viewModelScope.launch {
            try {
                repository.deleteBarang(id)
                getBarang() // Refresh data
            } catch (e: Exception) {
                barangUiState = BarangUiState.Error
            }
        }
    }
}

// --- 2. VIEW MODEL ENTRY (TAMBAH DATA & DROPDOWN) ---
data class BarangEntryUiState(
    val namaBarang: String = "",
    val kondisi: String = "",
    val kategoriId: Int? = null // Pakai Int nullable agar defaultnya kosong
)

class BarangEntryViewModel(private val repository: PeminjamanRepository) : ViewModel() {
    var uiState by mutableStateOf(BarangEntryUiState())
        private set

    // List untuk Dropdown Kategori
    var kategoriList by mutableStateOf<List<Kategori>>(emptyList())
        private set

    init {
        loadKategori()
    }

    private fun loadKategori() {
        viewModelScope.launch {
            try {
                kategoriList = repository.getKategori()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun updateUiState(newState: BarangEntryUiState) {
        uiState = newState
    }

    fun saveBarang(onSuccess: () -> Unit) {
        if (uiState.namaBarang.isNotBlank() && uiState.kondisi.isNotBlank() && uiState.kategoriId != null) {
            viewModelScope.launch {
                try {
                    val barang = Barang(
                        namaBarang = uiState.namaBarang,
                        kondisi = uiState.kondisi,
                        kategoriId = uiState.kategoriId!!
                    )
                    repository.insertBarang(barang)
                    onSuccess()
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }
}

// --- 3. VIEW MODEL DETAIL (LIHAT DATA) ---
sealed interface BarangDetailUiState {
    data class Success(val barang: Barang) : BarangDetailUiState
    object Error : BarangDetailUiState
    object Loading : BarangDetailUiState
}

class BarangDetailViewModel(
    savedStateHandle: SavedStateHandle,
    private val repository: PeminjamanRepository
) : ViewModel() {

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
                BarangDetailUiState.Success(repository.getBarangById(barangId))
            } catch (e: Exception) {
                BarangDetailUiState.Error
            }
        }
    }

    fun deleteBarang(onSuccess: () -> Unit) {
        viewModelScope.launch {
            try {
                repository.deleteBarang(barangId)
                onSuccess()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}