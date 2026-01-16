package com.example.proditi.uicontroller.view.peminjam

import androidx.compose.foundation.layout.*
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
import com.example.proditi.uicontroller.route.DestinasiPeminjamDetail
import com.example.proditi.viewmodel.peminjam.PeminjamDetailUiState
import com.example.proditi.viewmodel.peminjam.PeminjamDetailViewModel
import com.example.proditi.viewmodel.provider.PenyediaViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HalamanPeminjamDetail(
    navigateBack: () -> Unit,
    navigateToEdit: (Int) -> Unit,
    viewModel: PeminjamDetailViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    var showDeleteDialog by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        viewModel.getPeminjamById()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(DestinasiPeminjamDetail.titleRes) },
                navigationIcon = { IconButton(onClick = navigateBack) { Icon(Icons.Default.ArrowBack, "Back") } }
            )
        },
        floatingActionButton = {
            if (viewModel.uiState is PeminjamDetailUiState.Success) {
                FloatingActionButton(onClick = {
                    val id = (viewModel.uiState as PeminjamDetailUiState.Success).peminjam.id
                    navigateToEdit(id)
                }) { Icon(Icons.Default.Edit, "Edit") }
            }
        }
    ) { innerPadding ->
        Box(Modifier.padding(innerPadding).fillMaxSize()) {
            when (val state = viewModel.uiState) {
                is PeminjamDetailUiState.Loading -> CircularProgressIndicator(Modifier.align(Alignment.Center))
                is PeminjamDetailUiState.Error -> Text("Gagal memuat detail", Modifier.align(Alignment.Center))
                is PeminjamDetailUiState.Success -> {
                    Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
                        Card(Modifier.fillMaxWidth(), elevation = CardDefaults.cardElevation(4.dp)) {
                            Column(Modifier.padding(16.dp)) {
                                DetailRow("Nama Peminjam", state.peminjam.namaPeminjam)
                                DetailRow("NIM / NIK", state.peminjam.nim)
                                DetailRow("No HP", state.peminjam.noHp)
                            }
                        }
                        Button(
                            onClick = { showDeleteDialog = true },
                            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error),
                            modifier = Modifier.fillMaxWidth()
                        ) { Text("Hapus Peminjam") }
                    }
                }
            }
        }
    }

    if (showDeleteDialog) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog = false },
            title = { Text("Konfirmasi Hapus") },
            text = { Text("Apakah Anda yakin ingin menghapus data peminjam ini?") },
            confirmButton = {
                TextButton(onClick = {
                    coroutineScope.launch {
                        viewModel.deletePeminjam(onSuccess = navigateBack)
                        showDeleteDialog = false
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
    Column(Modifier.padding(vertical = 4.dp)) {
        Text(text = label, fontSize = 12.sp, color = MaterialTheme.colorScheme.secondary)
        Text(text = value, fontSize = 18.sp, fontWeight = FontWeight.Bold)
    }
}