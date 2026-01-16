package com.example.proditi.uicontroller.view.peminjam

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.proditi.uicontroller.route.DestinasiPeminjamDetail
import com.example.proditi.viewmodel.peminjam.PeminjamDetailUiState
import com.example.proditi.viewmodel.peminjam.PeminjamDetailViewModel
import com.example.proditi.viewmodel.provider.PenyediaViewModel
import kotlinx.coroutines.delay
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
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        viewModel.getPeminjamById()
    }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        topBar = {
            TopAppBar(
                title = { Text(DestinasiPeminjamDetail.titleRes) },
                navigationIcon = { IconButton(onClick = navigateBack) { Icon(Icons.Default.ArrowBack, "Back") } }
            )
        },
        floatingActionButton = {
            if (viewModel.uiState is PeminjamDetailUiState.Success) {
                FloatingActionButton(
                    onClick = {
                        val id = (viewModel.uiState as PeminjamDetailUiState.Success).peminjam.id
                        navigateToEdit(id)
                    },
                    containerColor = MaterialTheme.colorScheme.primary
                ) { Icon(Icons.Default.Edit, "Edit") }
            }
        }
    ) { innerPadding ->
        Box(Modifier.padding(innerPadding).fillMaxSize()) {
            when (val state = viewModel.uiState) {
                is PeminjamDetailUiState.Loading -> CircularProgressIndicator(Modifier.align(Alignment.Center))
                is PeminjamDetailUiState.Error -> Text("Gagal memuat detail", Modifier.align(Alignment.Center))
                is PeminjamDetailUiState.Success -> {
                    Column(
                        modifier = Modifier
                            .padding(24.dp)
                            .fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        // Icon Profil Besar
                        Box(
                            modifier = Modifier
                                .size(120.dp)
                                .clip(CircleShape)
                                .background(MaterialTheme.colorScheme.tertiaryContainer),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Person,
                                contentDescription = null,
                                modifier = Modifier.size(60.dp),
                                tint = MaterialTheme.colorScheme.tertiary
                            )
                        }

                        Spacer(Modifier.height(24.dp))

                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
                        ) {
                            Column(Modifier.padding(24.dp)) {
                                DetailRow("Nama Lengkap", state.peminjam.namaPeminjam)
                                Spacer(Modifier.height(8.dp))
                                DetailRow("NIM / NIK", state.peminjam.nim)
                                Spacer(Modifier.height(8.dp))
                                DetailRow("Nomor Telepon", state.peminjam.noHp)
                            }
                        }

                        Spacer(Modifier.height(32.dp))

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
                    viewModel.deletePeminjam(onSuccess = {
                        coroutineScope.launch {
                            showDeleteDialog = false
                            snackbarHostState.showSnackbar("Peminjam Dihapus")
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
fun DetailRow(label: String, value: String) {
    Column {
        Text(text = label, style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
        Text(text = value, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
    }
}