package com.example.proditi.uicontroller.view.barang

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.proditi.modeldata.Barang
import com.example.proditi.uicontroller.route.DestinasiBarangDetail
import com.example.proditi.viewmodel.barang.BarangDetailUiState
import com.example.proditi.viewmodel.barang.BarangDetailViewModel
import com.example.proditi.viewmodel.provider.PenyediaViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HalamanBarangDetail(
    navigateBack: () -> Unit,
    navigateToEdit: (Int) -> Unit,
    viewModel: BarangDetailViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(DestinasiBarangDetail.titleRes) },
                navigationIcon = {
                    IconButton(onClick = navigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        },
        floatingActionButton = {
            // FAB sekarang digunakan untuk Edit, sama seperti Kategori
            if (viewModel.detailUiState is BarangDetailUiState.Success) {
                FloatingActionButton(
                    onClick = {
                        val id = (viewModel.detailUiState as BarangDetailUiState.Success).barang.id
                        navigateToEdit(id)
                    },
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                ) {
                    Icon(Icons.Default.Edit, contentDescription = "Edit Barang")
                }
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            when (val state = viewModel.detailUiState) {
                is BarangDetailUiState.Loading -> {
                    CircularProgressIndicator(Modifier.align(Alignment.Center))
                }
                is BarangDetailUiState.Error -> {
                    Text(
                        text = "Gagal memuat detail",
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
                is BarangDetailUiState.Success -> {
                    // Struktur Column berisi Card Detail dan Tombol Hapus
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp)
                            .verticalScroll(rememberScrollState()), // Tambahkan scroll agar aman di layar kecil
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        // Kartu Detail
                        ItemDetailBarang(barang = state.barang)

                        // Tombol Hapus (Dipindah ke sini agar mirip Kategori)
                        Button(
                            onClick = {
                                viewModel.deleteBarang(onSuccess = navigateBack)
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.error
                            ),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Hapus Barang")
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ItemDetailBarang(barang: Barang, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            DetailRow(judul = "ID Barang", isi = barang.id.toString())
            DetailRow(judul = "Nama Barang", isi = barang.namaBarang)
            DetailRow(judul = "Kondisi", isi = barang.kondisi)
            DetailRow(judul = "Kategori", isi = barang.kategori?.namaKategori ?: "Tidak Diketahui")
        }
    }
}

@Composable
fun DetailRow(judul: String, isi: String) {
    Column(modifier = Modifier.padding(vertical = 8.dp)) {
        Text(text = judul, fontSize = 12.sp, color = MaterialTheme.colorScheme.secondary)
        Text(text = isi, fontSize = 18.sp, fontWeight = FontWeight.Bold)
    }
}