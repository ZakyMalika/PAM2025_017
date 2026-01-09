package com.example.proditi.uicontroller.view

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.proditi.uicontroller.route.DestinasiPeminjamDetail
import com.example.proditi.viewmodel.PeminjamDetailUiState
import com.example.proditi.viewmodel.PeminjamDetailViewModel
import com.example.proditi.viewmodel.provider.PenyediaViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HalamanPeminjamDetail(
    navigateBack: () -> Unit,
    navigateToEdit: (Int) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: PeminjamDetailViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    Scaffold(
        topBar = {
            ProdiTITopAppBar(
                title = DestinasiPeminjamDetail.titleRes,
                canNavigateBack = true,
                navigateUp = navigateBack
            )
        },
        floatingActionButton = {
            if (viewModel.detailUiState is PeminjamDetailUiState.Success) {
                FloatingActionButton(
                    onClick = { navigateToEdit((viewModel.detailUiState as PeminjamDetailUiState.Success).peminjam.id) }
                ) {
                    Icon(imageVector = Icons.Default.Edit, contentDescription = "Edit Peminjam")
                }
            }
        }
    ) { innerPadding ->
        when (val state = viewModel.detailUiState) {
            is PeminjamDetailUiState.Loading -> Text("Loading...", Modifier.padding(innerPadding))
            is PeminjamDetailUiState.Error -> Text("Error Terjadi", Modifier.padding(innerPadding))
            is PeminjamDetailUiState.Success -> {
                Column(
                    modifier = Modifier
                        .padding(innerPadding)
                        .padding(16.dp)
                        .fillMaxWidth()
                ) {
                    Card(modifier = Modifier.fillMaxWidth()) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(text = "ID: ${state.peminjam.id}", style = MaterialTheme.typography.bodyLarge)
                            Spacer(Modifier.height(8.dp))
                            Text(text = "Nama: ${state.peminjam.namaPeminjam}", style = MaterialTheme.typography.titleLarge)
                            Spacer(Modifier.height(8.dp))
                            Text(text = "NIM/NIK: ${state.peminjam.nimNik}", style = MaterialTheme.typography.bodyLarge)
                            Spacer(Modifier.height(8.dp))
                            Text(text = "No HP: ${state.peminjam.noHp}", style = MaterialTheme.typography.bodyLarge)
                        }
                    }
                    
                    Button(
                        onClick = {
                            viewModel.deletePeminjam()
                            navigateBack()
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error),
                        modifier = Modifier.fillMaxWidth().padding(top = 16.dp)
                    ) {
                        Text("Hapus Peminjam")
                    }
                }
            }
        }
    }
}
