package com.example.proditi.uicontroller.view

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.proditi.uicontroller.route.DestinasiBarangDetail
import com.example.proditi.viewmodel.BarangDetailUiState
import com.example.proditi.viewmodel.BarangDetailViewModel
import com.example.proditi.viewmodel.provider.PenyediaViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HalamanBarangDetail(
    navigateBack: () -> Unit,
    navigateToEdit: (Int) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: BarangDetailViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    Scaffold(
        topBar = {
            ProdiTITopAppBar(
                title = DestinasiBarangDetail.titleRes,
                canNavigateBack = true,
                navigateUp = navigateBack
            )
        },
        floatingActionButton = {
            if (viewModel.detailUiState is BarangDetailUiState.Success) {
                FloatingActionButton(
                    onClick = { navigateToEdit((viewModel.detailUiState as BarangDetailUiState.Success).barang.id) }
                ) {
                    Icon(imageVector = Icons.Default.Edit, contentDescription = "Edit Barang")
                }
            }
        }
    ) { innerPadding ->
        when (val state = viewModel.detailUiState) {
            is BarangDetailUiState.Loading -> Text("Loading...", Modifier.padding(innerPadding))
            is BarangDetailUiState.Error -> Text("Error Terjadi", Modifier.padding(innerPadding))
            is BarangDetailUiState.Success -> {
                Column(
                    modifier = Modifier
                        .padding(innerPadding)
                        .padding(16.dp)
                        .fillMaxWidth()
                ) {
                    Card(modifier = Modifier.fillMaxWidth()) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(text = "ID: ${state.barang.id}", style = MaterialTheme.typography.bodyLarge)
                            Spacer(Modifier.height(8.dp))
                            Text(text = "Nama: ${state.barang.namaBarang}", style = MaterialTheme.typography.titleLarge)
                            Spacer(Modifier.height(8.dp))
                            Text(text = "Kondisi: ${state.barang.kondisi}", style = MaterialTheme.typography.bodyLarge)
                            Spacer(Modifier.height(8.dp))
                            Text(text = "Kategori ID: ${state.barang.kategoriId}", style = MaterialTheme.typography.bodyLarge)
                        }
                    }
                    
                    Button(
                        onClick = {
                            viewModel.deleteBarang()
                            navigateBack()
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error),
                        modifier = Modifier.fillMaxWidth().padding(top = 16.dp)
                    ) {
                        Text("Hapus Barang")
                    }
                }
            }
        }
    }
}
