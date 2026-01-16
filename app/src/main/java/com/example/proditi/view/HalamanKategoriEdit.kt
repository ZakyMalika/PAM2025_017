package com.example.proditi.uicontroller.view

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.proditi.uicontroller.route.DestinasiKategoriEdit
import com.example.proditi.viewmodel.KategoriEditViewModel
import com.example.proditi.viewmodel.provider.PenyediaViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HalamanKategoriEdit(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: KategoriEditViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val coroutineScope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    var isError by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        topBar = {
            ProdiTITopAppBar(
                title = DestinasiKategoriEdit.titleRes,
                canNavigateBack = true,
                navigateUp = navigateBack
            )
        }
    ) { innerPadding ->
        Column(
            modifier = modifier
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            OutlinedTextField(
                value = viewModel.uiState.namaKategori,
                onValueChange = {
                    viewModel.updateUiState(viewModel.uiState.copy(namaKategori = it))
                    isError = false
                },
                label = { Text("Nama Kategori") },
                isError = isError,
                supportingText = {
                    if (isError) {
                        Text(text = errorMessage, color = MaterialTheme.colorScheme.error)
                    }
                },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = {
                    if (viewModel.uiState.namaKategori.isBlank()) {
                        isError = true
                        errorMessage = "Nama Kategori tidak boleh kosong!"
                    } else {
                        coroutineScope.launch {
                            viewModel.updateKategori()
                            snackbarHostState.showSnackbar("Data berhasil diubah")
                            kotlinx.coroutines.delay(100)
                            navigateBack()
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Simpan Perubahan")
            }
        }
    }
}
