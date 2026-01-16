package com.example.proditi.uicontroller.view

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.proditi.viewmodel.DetailUiState
import com.example.proditi.viewmodel.DetailViewModel
import com.example.proditi.viewmodel.provider.PenyediaViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HalamanDetail(
    navigateBack: () -> Unit,
    navigateToEdit: (Int) -> Unit,
    viewModel: DetailViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    // 1. AUTO REFRESH (Penting agar data terupdate setelah edit)
    LaunchedEffect(Unit) {
        viewModel.getById()
    }

    val uiState by viewModel.detailUiState.collectAsState()

    val coroutineScope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    var showDeleteDialog by remember { mutableStateOf(false) }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        topBar = {
            ProdiTITopAppBar(
                title = "Detail Peminjaman",
                canNavigateBack = true,
                navigateUp = navigateBack
            )
        },
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
            is DetailUiState.Loading -> Box(Modifier.fillMaxSize().padding(innerPadding), contentAlignment = androidx.compose.ui.Alignment.Center) {
                CircularProgressIndicator()
            }
            is DetailUiState.Error -> Box(Modifier.fillMaxSize().padding(innerPadding), contentAlignment = androidx.compose.ui.Alignment.Center) {
                Text("Error saat memuat data")
            }
            is DetailUiState.Success -> {
                Column(
                    modifier = Modifier.padding(innerPadding).padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Card(modifier = Modifier.fillMaxWidth()) {
                        Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                            DetailRow("Nama Barang", state.peminjaman.barang?.namaBarang ?: "-")
                            DetailRow("Peminjam", state.peminjaman.peminjam?.namaPeminjam ?: "-")
                            DetailRow("Tanggal Pinjam", state.peminjaman.tanggalPinjam)
                            DetailRow("Tanggal Kembali", state.peminjaman.tanggalKembali)
                        }
                    }

                    Spacer(Modifier.height(24.dp))
                    Button(
                        onClick = { showDeleteDialog = true },
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Hapus Data")
                    }
                }
            }
        }
    }

    // DIALOG KONFIRMASI HAPUS
    if (showDeleteDialog) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog = false },
            title = { Text("Konfirmasi Hapus") },
            text = { Text("Yakin ingin menghapus data ini?") },
            confirmButton = {
                TextButton(onClick = {
                    coroutineScope.launch {
                        viewModel.deletePeminjaman()
                        showDeleteDialog = false
                        snackbarHostState.showSnackbar("Data Berhasil Dihapus")
                        delay(600)
                        navigateBack()
                    }
                }) { Text("Hapus", color = MaterialTheme.colorScheme.error) }
            },
            dismissButton = {
                TextButton(onClick = { showDeleteDialog = false }) { Text("Batal") }
            }
        )
    }
}

@Composable
fun DetailRow(label: String, value: String) {
    Column {
        Text(text = label, style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.primary)
        Text(text = value, style = MaterialTheme.typography.bodyLarge)
    }
}