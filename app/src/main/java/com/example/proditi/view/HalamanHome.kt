package com.example.proditi.uicontroller.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.proditi.uicontroller.route.DestinasiHome
import com.example.proditi.viewmodel.HomeUiState
import com.example.proditi.viewmodel.HomeViewModel
import com.example.proditi.viewmodel.provider.PenyediaViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HalamanHome(
    navigateToItemEntry: () -> Unit,
    onDetailClick: (Int) -> Unit,
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    // Refresh data setiap kali halaman ini muncul
    LaunchedEffect(Unit) {
        viewModel.getPeminjaman()
    }

    Scaffold(
        topBar = {
            ProdiTITopAppBar(
                title = DestinasiHome.titleRes,
                canNavigateBack = true,
                navigateUp = navigateBack
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = navigateToItemEntry) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Tambah")
            }
        },
    ) { innerPadding ->
        when (val state = viewModel.homeUiState) {
            is HomeUiState.Loading -> Box(Modifier.fillMaxSize().padding(innerPadding), contentAlignment = androidx.compose.ui.Alignment.Center) {
                CircularProgressIndicator()
            }
            is HomeUiState.Error -> Box(Modifier.fillMaxSize().padding(innerPadding), contentAlignment = androidx.compose.ui.Alignment.Center) {
                Text("Error Terjadi")
            }
            is HomeUiState.Success -> {
                if (state.peminjaman.isEmpty()) {
                    Box(Modifier.fillMaxSize().padding(innerPadding), contentAlignment = androidx.compose.ui.Alignment.Center) {
                        Text("Tidak ada data peminjaman")
                    }
                } else {
                    LazyColumn(modifier = Modifier.padding(innerPadding)) {
                        items(state.peminjaman) { data ->
                            Card(
                                onClick = { onDetailClick(data.id) },
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
                                    Text(text = "Tgl: ${data.tanggalPinjam} s/d ${data.tanggalKembali}")
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
