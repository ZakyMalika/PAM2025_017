package com.example.proditi.uicontroller.view

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.proditi.uicontroller.route.DestinasiBarangEdit
import com.example.proditi.viewmodel.BarangEditViewModel
import com.example.proditi.viewmodel.provider.PenyediaViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HalamanBarangEdit(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: BarangEditViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val coroutineScope = rememberCoroutineScope()
    Scaffold(
        topBar = {
            ProdiTITopAppBar(
                title = DestinasiBarangEdit.titleRes,
                canNavigateBack = true,
                navigateUp = navigateBack
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            OutlinedTextField(
                value = viewModel.uiState.namaBarang,
                onValueChange = { viewModel.updateUiState(viewModel.uiState.copy(namaBarang = it)) },
                label = { Text("Nama Barang") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(8.dp))
            OutlinedTextField(
                value = viewModel.uiState.kondisi,
                onValueChange = { viewModel.updateUiState(viewModel.uiState.copy(kondisi = it)) },
                label = { Text("Kondisi") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(8.dp))
            OutlinedTextField(
                value = viewModel.uiState.kategoriId,
                onValueChange = { viewModel.updateUiState(viewModel.uiState.copy(kategoriId = it)) },
                label = { Text("Kategori ID") },
                modifier = Modifier.fillMaxWidth()
            )
            Button(
                onClick = {
                    coroutineScope.launch {
                        viewModel.updateBarang()
                        navigateBack()
                    }
                },
                modifier = Modifier.fillMaxWidth().padding(top = 16.dp)
            ) {
                Text("Simpan Perubahan")
            }
        }
    }
}
