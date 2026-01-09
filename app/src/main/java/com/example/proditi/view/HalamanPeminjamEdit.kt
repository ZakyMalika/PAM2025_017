package com.example.proditi.uicontroller.view

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.proditi.uicontroller.route.DestinasiPeminjamEdit
import com.example.proditi.viewmodel.PeminjamEditViewModel
import com.example.proditi.viewmodel.provider.PenyediaViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HalamanPeminjamEdit(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: PeminjamEditViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val coroutineScope = rememberCoroutineScope()
    Scaffold(
        topBar = {
            ProdiTITopAppBar(
                title = DestinasiPeminjamEdit.titleRes,
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
                value = viewModel.uiState.namaPeminjam,
                onValueChange = { viewModel.updateUiState(viewModel.uiState.copy(namaPeminjam = it)) },
                label = { Text("Nama Peminjam") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(8.dp))
            OutlinedTextField(
                value = viewModel.uiState.nimNik,
                onValueChange = { viewModel.updateUiState(viewModel.uiState.copy(nimNik = it)) },
                label = { Text("NIM/NIK") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(8.dp))
            OutlinedTextField(
                value = viewModel.uiState.noHp,
                onValueChange = { viewModel.updateUiState(viewModel.uiState.copy(noHp = it)) },
                label = { Text("No HP") },
                modifier = Modifier.fillMaxWidth()
            )
            Button(
                onClick = {
                    coroutineScope.launch {
                        viewModel.updatePeminjam()
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
