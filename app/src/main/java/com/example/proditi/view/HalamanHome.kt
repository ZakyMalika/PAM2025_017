package com.example.proditi.uicontroller.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.proditi.modeldata.Peminjaman
import com.example.proditi.uicontroller.route.DestinasiHome
import com.example.proditi.viewmodel.HomeUiState
import com.example.proditi.viewmodel.HomeViewModel
import com.example.proditi.viewmodel.provider.PenyediaViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HalamanHome(
    navigateToItemEntry: () -> Unit,
    onDetailClick: (Int) -> Unit, // Parameter navigasi ke detail
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    Scaffold(
        topBar = { TopAppBar(title = { Text(DestinasiHome.titleRes) }) },
        floatingActionButton = {
            FloatingActionButton(onClick = navigateToItemEntry) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Tambah")
            }
        },
    ) { innerPadding ->
        when (val state = viewModel.homeUiState) {
            is HomeUiState.Loading -> Text("Loading...", Modifier.padding(innerPadding))
            is HomeUiState.Error -> Text("Error Terjadi", Modifier.padding(innerPadding))
            is HomeUiState.Success -> {
                LazyColumn(modifier = Modifier.padding(innerPadding)) {
                    items(state.peminjaman) { data ->
                        // PERBAIKAN DI SINI: Tambahkan onClick pada Card
                        Card(
                            onClick = { onDetailClick(data.id) }, // <--- Panggil fungsi navigasi di sini
                            modifier = Modifier
                                .padding(8.dp)
                                .fillMaxWidth()
                        ) {
                            Column(Modifier.padding(16.dp)) {
                                Text(
                                    text = data.barang?.namaBarang ?: "Barang",
                                    style = MaterialTheme.typography.titleMedium
                                )
                                Text(text = "Peminjam: ${data.peminjam?.namaPeminjam}")
                                Text(text = "${data.tanggalPinjam} - ${data.tanggalKembali}")
                            }
                        }
                    }
                }
            }
        }
    }
}