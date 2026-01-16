package com.example.proditi.uicontroller.view.barang

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
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
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HalamanBarangDetail(
    navigateBack: () -> Unit,
    navigateToEdit: (Int) -> Unit,
    viewModel: BarangDetailViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    // Auto-refresh saat halaman ditampilkan (misal kembali dari edit)
    LaunchedEffect(Unit) {
        viewModel.getBarangById()
    }

    var showDeleteDialog by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
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
                is BarangDetailUiState.Loading -> CircularProgressIndicator(Modifier.align(Alignment.Center))
                is BarangDetailUiState.Error -> Text("Gagal memuat detail", Modifier.align(Alignment.Center))
                is BarangDetailUiState.Success -> {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp)
                            .verticalScroll(rememberScrollState()),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        ItemDetailBarang(barang = state.barang)

                        Button(
                            onClick = { showDeleteDialog = true },
                            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Hapus Barang")
                        }
                    }
                }
            }
        }
    }

    if (showDeleteDialog) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog = false },
            title = { Text("Konfirmasi Hapus") },
            text = { Text("Apakah Anda yakin ingin menghapus barang ini?") },
            confirmButton = {
                TextButton(onClick = {
                    viewModel.deleteBarang(onSuccess = {
                        coroutineScope.launch {
                            showDeleteDialog = false
                            snackbarHostState.showSnackbar("Data Barang Berhasil Dihapus")
                            delay(600)
                            navigateBack()
                        }
                    })
                }) { Text("Hapus", color = MaterialTheme.colorScheme.error) }
            },
            dismissButton = {
                TextButton(onClick = { showDeleteDialog = false }) { Text("Batal") }
            }
        )
    }
}

@Composable
fun ItemDetailBarang(barang: Barang, modifier: Modifier = Modifier) {
    Card(modifier = modifier.fillMaxWidth(), elevation = CardDefaults.cardElevation(4.dp)) {
        Column(modifier = Modifier.padding(16.dp)) {
            DetailRow("ID Barang", barang.id.toString())
            DetailRow("Nama Barang", barang.namaBarang)
            DetailRow("Kondisi", barang.kondisi)
            DetailRow("Kategori", barang.kategori?.namaKategori ?: "Tidak Diketahui")
        }
    }
}

@Composable
fun DetailRow(judul: String, isi: String) {
    Column(modifier = Modifier.padding(vertical = 4.dp)) {
        Text(text = judul, fontSize = 12.sp, color = MaterialTheme.colorScheme.secondary)
        Text(text = isi, fontSize = 18.sp, fontWeight = FontWeight.Bold)
    }
}