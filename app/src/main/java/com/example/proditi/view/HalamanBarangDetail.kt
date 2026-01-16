package com.example.proditi.uicontroller.view.barang

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
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
    LaunchedEffect(Unit) { viewModel.getBarangById() }

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
                    onClick = { navigateToEdit((viewModel.detailUiState as BarangDetailUiState.Success).barang.id) },
                    containerColor = MaterialTheme.colorScheme.primary
                ) {
                    Icon(Icons.Default.Edit, contentDescription = "Edit Barang")
                }
            }
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding).fillMaxSize()) {
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
            dismissButton = { TextButton(onClick = { showDeleteDialog = false }) { Text("Batal") } }
        )
    }
}

@Composable
fun ItemDetailBarang(barang: Barang) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(modifier = Modifier.padding(24.dp)) {
            // Header Nama Barang
            Text(
                text = barang.namaBarang,
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(16.dp))
            Divider()
            Spacer(modifier = Modifier.height(16.dp))

            // Baris Detail dengan Layout Row
            DetailRowWithBadge("Kondisi", barang.kondisi,
                isGood = barang.kondisi.equals("Baik", ignoreCase = true))

            Spacer(modifier = Modifier.height(12.dp))

            DetailRowWithBadge("Kategori", barang.kategori?.namaKategori ?: "-")

            Spacer(modifier = Modifier.height(12.dp))

            DetailRowSimple("ID Barang", barang.id.toString())
        }
    }
}

// Komponen Badge Status
@Composable
fun DetailRowWithBadge(label: String, value: String, isGood: Boolean = true) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(text = "$label : ", style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.SemiBold)
        Spacer(modifier = Modifier.width(8.dp))
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(8.dp))
                .background(
                    if (isGood) MaterialTheme.colorScheme.primaryContainer
                    else MaterialTheme.colorScheme.errorContainer
                )
                .padding(horizontal = 12.dp, vertical = 4.dp)
        ) {
            Text(
                text = value,
                style = MaterialTheme.typography.bodyMedium,
                color = if (isGood) MaterialTheme.colorScheme.onPrimaryContainer else MaterialTheme.colorScheme.onErrorContainer
            )
        }
    }
}

@Composable
fun DetailRowSimple(label: String, value: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(text = "$label : ", style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.SemiBold)
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = value, style = MaterialTheme.typography.bodyLarge)
    }
}