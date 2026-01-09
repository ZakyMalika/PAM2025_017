package com.example.proditi.uicontroller.view

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.proditi.modeldata.Peminjaman
import com.example.proditi.viewmodel.DetailUiState
import com.example.proditi.viewmodel.DetailViewModel
import com.example.proditi.viewmodel.provider.PenyediaViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HalamanDetail(
    navigateBack: () -> Unit,
    navigateToEdit: (Int) -> Unit,
    viewModel: DetailViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val uiState by viewModel.detailUiState.collectAsState()

    Scaffold(
        topBar = { TopAppBar(title = { Text("Detail Peminjaman") }) },
        floatingActionButton = {
            if (uiState is DetailUiState.Success) {
                val id = (uiState as DetailUiState.Success).peminjaman.id
                FloatingActionButton(onClick = { navigateToEdit(id) }) {
                    Icon(Icons.Default.Edit, contentDescription = "Edit")
                }
            }
        }
    ) { innerPadding ->
        when (val state = uiState) {
            is DetailUiState.Loading -> Text("Loading...", Modifier.padding(innerPadding))
            is DetailUiState.Error -> Text("Error saat memuat data", Modifier.padding(innerPadding))
            is DetailUiState.Success -> {
                Column(
                    modifier = Modifier.padding(innerPadding).padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    DetailRow("Nama Barang", state.peminjaman.barang?.namaBarang ?: "-")
                    DetailRow("Peminjam", state.peminjaman.peminjam?.namaPeminjam ?: "-")
                    DetailRow("Tanggal Pinjam", state.peminjaman.tanggalPinjam)
                    DetailRow("Tanggal Kembali", state.peminjaman.tanggalKembali)

                    Spacer(Modifier.height(24.dp))
                    Button(
                        onClick = {
                            viewModel.deletePeminjaman()
                            navigateBack()
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Hapus Data")
                    }
                }
            }
        }
    }
}

@Composable
fun DetailRow(label: String, value: String) {
    Column {
        Text(text = label, style = MaterialTheme.typography.labelMedium)
        Text(text = value, style = MaterialTheme.typography.bodyLarge)
    }
}