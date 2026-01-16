package com.example.proditi.uicontroller.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Inventory
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.proditi.uicontroller.route.DestinasiEntry
import com.example.proditi.viewmodel.EntryViewModel
import com.example.proditi.viewmodel.provider.PenyediaViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HalamanEntry(
    navigateBack: () -> Unit,
    viewModel: EntryViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val coroutineScope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    var isError by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        topBar = {
            ProdiTITopAppBar(
                title = DestinasiEntry.titleRes,
                canNavigateBack = true,
                navigateUp = navigateBack
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Dropdown Barang (Icon Inventory)
            DropdownMenuField(
                label = "Pilih Barang",
                options = viewModel.listBarang.map { it.id.toString() to it.namaBarang },
                selectedId = if (viewModel.uiStatePeminjaman.detailPeminjaman.barangId == 0) "" else viewModel.uiStatePeminjaman.detailPeminjaman.barangId.toString(),
                isError = isError && viewModel.uiStatePeminjaman.detailPeminjaman.barangId == 0,
                leadingIcon = { Icon(Icons.Default.Inventory, contentDescription = null) },
                onSelected = { id ->
                    viewModel.updateUiState(viewModel.uiStatePeminjaman.detailPeminjaman.copy(barangId = id.toInt()))
                }
            )

            // Dropdown Peminjam (Icon Person)
            DropdownMenuField(
                label = "Pilih Peminjam",
                options = viewModel.listPeminjam.map { it.id.toString() to it.namaPeminjam },
                selectedId = if (viewModel.uiStatePeminjaman.detailPeminjaman.peminjamId == 0) "" else viewModel.uiStatePeminjaman.detailPeminjaman.peminjamId.toString(),
                isError = isError && viewModel.uiStatePeminjaman.detailPeminjaman.peminjamId == 0,
                leadingIcon = { Icon(Icons.Default.Person, contentDescription = null) },
                onSelected = { id ->
                    viewModel.updateUiState(viewModel.uiStatePeminjaman.detailPeminjaman.copy(peminjamId = id.toInt()))
                }
            )

            // Date Picker Pinjam
            DatePickerField(
                label = "Tanggal Pinjam",
                value = viewModel.uiStatePeminjaman.detailPeminjaman.tanggalPinjam,
                onDateSelected = { date ->
                    viewModel.updateUiState(viewModel.uiStatePeminjaman.detailPeminjaman.copy(tanggalPinjam = date))
                },
                isError = isError && viewModel.uiStatePeminjaman.detailPeminjaman.tanggalPinjam.isBlank()
            )

            // Date Picker Kembali
            DatePickerField(
                label = "Tanggal Kembali",
                value = viewModel.uiStatePeminjaman.detailPeminjaman.tanggalKembali,
                onDateSelected = { date ->
                    viewModel.updateUiState(viewModel.uiStatePeminjaman.detailPeminjaman.copy(tanggalKembali = date))
                },
                isError = isError && viewModel.uiStatePeminjaman.detailPeminjaman.tanggalKembali.isBlank()
            )

            if (isError) {
                Text(text = errorMessage, color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.bodySmall)
            }

            Button(
                onClick = {
                    if (viewModel.uiStatePeminjaman.detailPeminjaman.barangId == 0 ||
                        viewModel.uiStatePeminjaman.detailPeminjaman.peminjamId == 0 ||
                        viewModel.uiStatePeminjaman.detailPeminjaman.tanggalPinjam.isBlank() ||
                        viewModel.uiStatePeminjaman.detailPeminjaman.tanggalKembali.isBlank()) {
                        isError = true
                        errorMessage = "Semua data wajib diisi!"
                    } else {
                        coroutineScope.launch {
                            viewModel.savePeminjaman()
                            snackbarHostState.showSnackbar("Data Berhasil Disimpan")
                            delay(600)
                            navigateBack()
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(Icons.Default.Save, contentDescription = null)
                Spacer(Modifier.width(8.dp))
                Text("Simpan Peminjaman")
            }
        }
    }
}

// --- Helper Components with Icon Support ---

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropdownMenuField(
    label: String,
    options: List<Pair<String, String>>,
    selectedId: String,
    isError: Boolean = false,
    leadingIcon: @Composable (() -> Unit)? = null, // Tambah support Icon
    onSelected: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    val selectedText = options.find { it.first == selectedId }?.second ?: ""

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded },
        modifier = Modifier.fillMaxWidth()
    ) {
        OutlinedTextField(
            value = selectedText,
            onValueChange = {},
            readOnly = true,
            label = { Text(label) },
            leadingIcon = leadingIcon, // Pasang Icon
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            isError = isError,
            modifier = Modifier.menuAnchor().fillMaxWidth()
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            options.forEach { option ->
                DropdownMenuItem(
                    text = { Text(option.second) },
                    onClick = { onSelected(option.first); expanded = false }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerField(
    label: String,
    value: String,
    onDateSelected: (String) -> Unit,
    isError: Boolean = false
) {
    var showDatePicker by remember { mutableStateOf(false) }
    val datePickerState = rememberDatePickerState()

    OutlinedTextField(
        value = value,
        onValueChange = {},
        readOnly = true,
        label = { Text(label) },
        leadingIcon = { Icon(Icons.Default.DateRange, contentDescription = null) }, // Icon Kalender di Kiri
        trailingIcon = { IconButton(onClick = { showDatePicker = true }) { Icon(Icons.Default.DateRange, contentDescription = null) } },
        isError = isError,
        modifier = Modifier.fillMaxWidth().clickable { showDatePicker = true },
        enabled = false,
        colors = OutlinedTextFieldDefaults.colors(
            disabledTextColor = MaterialTheme.colorScheme.onSurface,
            disabledBorderColor = if (isError) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.outline,
            disabledLabelColor = if (isError) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.onSurfaceVariant,
            disabledTrailingIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
            disabledLeadingIconColor = MaterialTheme.colorScheme.onSurfaceVariant
        )
    )

    if (showDatePicker) {
        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            confirmButton = {
                TextButton(onClick = {
                    datePickerState.selectedDateMillis?.let { millis ->
                        val dateFormatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                        onDateSelected(dateFormatter.format(Date(millis)))
                    }
                    showDatePicker = false
                }) { Text("OK") }
            },
            dismissButton = { TextButton(onClick = { showDatePicker = false }) { Text("Cancel") } }
        ) { DatePicker(state = datePickerState) }
    }
}