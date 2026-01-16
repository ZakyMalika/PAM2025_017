package com.example.proditi.uicontroller.view

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.proditi.uicontroller.route.DestinasiKategoriDetail
import com.example.proditi.viewmodel.KategoriDetailUiState
import com.example.proditi.viewmodel.KategoriDetailViewModel
import com.example.proditi.viewmodel.provider.PenyediaViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HalamanKategoriDetail(
    navigateBack: () -> Unit,
    navigateToEdit: (Int) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: KategoriDetailViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val coroutineScope = rememberCoroutineScope()
    var showDeleteDialog by remember { mutableStateOf(false) }

    LaunchedEffect (Unit) {
        viewModel.getKategoriById()
    }

    Scaffold(
        topBar = {
            ProdiTITopAppBar(
                title = DestinasiKategoriDetail.titleRes,
                canNavigateBack = true,
                navigateUp = navigateBack
            )
        },
        floatingActionButton = {
            if (viewModel.detailUiState is KategoriDetailUiState.Success) {
                FloatingActionButton(
                    onClick = { navigateToEdit((viewModel.detailUiState as KategoriDetailUiState.Success).kategori.id) }
                ) {
                    Icon(imageVector = Icons.Default.Edit, contentDescription = "Edit Kategori")
                }
            }
        }
    ) { innerPadding ->
        when (val state = viewModel.detailUiState) {
            is KategoriDetailUiState.Loading -> Text("Loading...", Modifier.padding(innerPadding))
            is KategoriDetailUiState.Error -> Text("Error Terjadi", Modifier.padding(innerPadding))
            is KategoriDetailUiState.Success -> {
                Column(
                    modifier = Modifier
                        .padding(innerPadding)
                        .padding(16.dp)
                        .fillMaxWidth()
                ) {
                    Card(modifier = Modifier.fillMaxWidth()) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(text = "ID Kategori: ${state.kategori.id}", style = MaterialTheme.typography.bodyLarge)
                            Spacer(Modifier.height(8.dp))
                            Text(text = "Nama Kategori: ${state.kategori.namaKategori}", style = MaterialTheme.typography.titleLarge)
                        }
                    }

                    Button(
                        onClick = {
                            showDeleteDialog = true
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error),
                        modifier = Modifier.fillMaxWidth().padding(top = 16.dp)
                    ) {
                        Text("Hapus Kategori")
                    }


                    if (showDeleteDialog) {
                        AlertDialog(
                            onDismissRequest = { showDeleteDialog = false },
                            title = { Text(text = "Konfirmasi Hapus") },
                            text = { Text(text = "Apakah Anda yakin ingin menghapus kategori ini? Data yang dihapus tidak dapat dikembalikan.") },
                            confirmButton = {
                                TextButton(
                                    onClick = {
                                        coroutineScope.launch {
                                            viewModel.deleteKategori()
                                            showDeleteDialog = false
                                            navigateBack()
                                        }
                                    }
                                ) {
                                    Text("Hapus", color = MaterialTheme.colorScheme.error)
                                }
                            },
                            dismissButton = {
                                TextButton(onClick = { showDeleteDialog = false }) {
                                    Text("Batal")
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}