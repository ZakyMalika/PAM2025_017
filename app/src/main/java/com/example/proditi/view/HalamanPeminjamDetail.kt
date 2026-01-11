package com.example.proditi.uicontroller.view.peminjam

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.proditi.uicontroller.route.DestinasiPeminjamDetail
import com.example.proditi.viewmodel.peminjam.PeminjamDetailUiState
import com.example.proditi.viewmodel.peminjam.PeminjamDetailViewModel
import com.example.proditi.viewmodel.provider.PenyediaViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HalamanPeminjamDetail(
    navigateBack: () -> Unit,
    navigateToEdit: (Int) -> Unit,
    viewModel: PeminjamDetailViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
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
                        Card(Modifier.fillMaxWidth()) {
                            Column(Modifier.padding(16.dp)) {
                                Text("Nama: ${state.peminjam.namaPeminjam}", style = MaterialTheme.typography.titleLarge)
                                Text("Alamat: ${state.peminjam.nim}")
                                Text("No Telp: ${state.peminjam.noHp}")
                            }
                        }
                        Button(
                            onClick = { viewModel.deletePeminjam(onSuccess = navigateBack) },
                            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error),
                            modifier = Modifier.fillMaxWidth()
                        ) { Text("Hapus Peminjam") }
                    }
                }
            }
        }
    }
}