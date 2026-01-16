package com.example.proditi.uicontroller.view.peminjaman

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.proditi.modeldata.Peminjaman
import com.example.proditi.viewmodel.HomeViewModel
import com.example.proditi.viewmodel.HomeUiState
import com.example.proditi.viewmodel.provider.PenyediaViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HalamanHome(
    navigateBack: () -> Unit,
    navigateToEntry: () -> Unit,
    onDetailClick: (Int) -> Unit,
    viewModel: HomeViewModel = viewModel(factory = PenyediaViewModel.Factory),
//    navigateToItemEntry: () -> Unit
) {
    // Auto Refresh saat halaman dibuka/kembali
    LaunchedEffect(Unit) {
        viewModel.getPeminjaman()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Daftar Peminjaman") },
                navigationIcon = {
                    IconButton(onClick = navigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = navigateToEntry) {
                Icon(Icons.Default.Add, contentDescription = "Tambah")
            }
        }
    ) { innerPadding ->
        when (val state = viewModel.homeUiState) {
            is HomeUiState.Loading -> Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) { CircularProgressIndicator() }
            is HomeUiState.Error -> Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) { Text("Gagal memuat data") }
            is HomeUiState.Success -> {
                if (state.peminjaman.isEmpty()) {
                    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) { Text("Belum ada transaksi") }
                } else {
                    LazyColumn(modifier = Modifier.padding(innerPadding).padding(16.dp)) {
                        items(state.peminjaman) { item ->
                            PeminjamanCard(
                                peminjaman = item,
                                onClick = { onDetailClick(item.id) }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun PeminjamanCard(peminjaman: Peminjaman, onClick: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp).clickable { onClick() },
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column(Modifier.padding(16.dp)) {
            Text(text = "Barang: ${peminjaman.barang?.namaBarang ?: "Loading..."}", style = MaterialTheme.typography.titleMedium)
            Text(text = "Peminjam: ${peminjaman.peminjam?.namaPeminjam ?: "Loading..."}", style = MaterialTheme.typography.bodyMedium)
            Spacer(modifier = Modifier.height(4.dp))
            Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
                Text(text = "Pinjam: ${peminjaman.tanggalPinjam}", style = MaterialTheme.typography.bodySmall)
                Text(text = "Kembali: ${peminjaman.tanggalKembali}", style = MaterialTheme.typography.bodySmall)
            }
        }
    }
}