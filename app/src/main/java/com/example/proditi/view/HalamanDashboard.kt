package com.example.proditi.uicontroller.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.Assignment
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.Inventory
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.People
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.proditi.R // Pastikan import R ada jika ingin pakai gambar profil (opsional)
import com.example.proditi.uicontroller.route.DestinasiBarangHome
import com.example.proditi.uicontroller.route.DestinasiDashboard
import com.example.proditi.uicontroller.route.DestinasiHome
import com.example.proditi.uicontroller.route.DestinasiKategoriHome
import com.example.proditi.uicontroller.route.DestinasiPeminjamHome

@Composable
fun HalamanDashboard(
    onMenuClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        containerColor = Color(0xFFF8F9FA) // Background abu-abu sangat muda (Clean look)
    ) { innerPadding ->
        Column(
            modifier = modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            // 1. HEADER (Simple & Professional)
            DashboardHeader()

            // 2. BODY CONTENT (Scrollable)
            LazyColumn(
                contentPadding = PaddingValues(20.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                // SECTION: RINGKASAN (Statistik Mockup)
//                item {
//                    Text(
//                        text = "Ringkasan Hari Ini",
//                        style = MaterialTheme.typography.titleMedium,
//                        fontWeight = FontWeight.Bold,
//                        color = MaterialTheme.colorScheme.onBackground
//                    )
//                    Spacer(modifier = Modifier.height(12.dp))
////                    Row(
////                        modifier = Modifier.fillMaxWidth(),
////                        horizontalArrangement = Arrangement.spacedBy(12.dp)
////                    ) {
////                        InfoCard(
////                            title = "Total Aset",
////                            value = "124",
////                            color = Color(0xFF4CAF50),
////                            modifier = Modifier.weight(1f)
////                        )
////                        InfoCard(
////                            title = "Dipinjam",
////                            value = "8",
////                            color = Color(0xFFFF9800), // Orange
////                            modifier = Modifier.weight(1f)
////                        )
////                    }
//                }

                // SECTION: MENU UTAMA
                item {
                    Text(
                        text = "Kelola Data",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }

                items(menuItems) { item ->
                    WideMenuCard(
                        menuItem = item,
                        onClick = { onMenuClick(item.route) }
                    )
                }

                item {
                    Spacer(modifier = Modifier.height(30.dp)) // Padding bawah
                }
            }
        }
    }
}

@Composable
fun DashboardHeader() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(horizontal = 24.dp, vertical = 24.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(
                text = "Halo, Admin",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
            Text(
                text = "Prodi Teknologi Informasi",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray
            )
        }

        // Avatar / Icon Profil
        Box(
            modifier = Modifier
                .size(50.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.primaryContainer),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.People,
                contentDescription = "Profil",
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(28.dp)
            )
        }
    }
}

@Composable
fun InfoCard(
    title: String,
    value: String,
    color: Color,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(8.dp)
                    .clip(CircleShape)
                    .background(color)
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = value,
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = title,
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray
            )
        }
    }
}

@Composable
fun WideMenuCard(
    menuItem: MenuItem,
    onClick: () -> Unit
) {
    Card(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(85.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Icon Container
            Box(
                modifier = Modifier
                    .size(50.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(menuItem.color.copy(alpha = 0.1f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = menuItem.icon,
                    contentDescription = null,
                    tint = menuItem.color,
                    modifier = Modifier.size(26.dp)
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            // Texts
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = menuItem.title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = menuItem.description,
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray
                )
            }

            // Arrow Icon
            Icon(
                imageVector = Icons.Default.ArrowForwardIos,
                contentDescription = null,
                modifier = Modifier.size(16.dp),
                tint = Color.LightGray
            )
        }
    }
}

// Data Class Updated (Added Description)
data class MenuItem(
    val title: String,
    val description: String,
    val icon: ImageVector,
    val route: String,
    val color: Color
)

// Menu Data
val menuItems = listOf(
    MenuItem(
        "Transaksi Peminjaman",
        "Catat barang masuk & keluar",
        Icons.Default.Assignment,
        DestinasiHome.route,
        Color(0xFF4CAF50) // Green
    ),
    MenuItem(
        "Data Barang",
        "Kelola inventaris aset prodi",
        Icons.Default.Inventory,
        DestinasiBarangHome.route,
        Color(0xFF2196F3) // Blue
    ),
    MenuItem(
        "Data Peminjam",
        "Daftar mahasiswa & dosen",
        Icons.Default.People,
        DestinasiPeminjamHome.route,
        Color(0xFFFF9800) // Orange
    ),
    MenuItem(
        "Kategori Barang",
        "Klasifikasi jenis aset",
        Icons.Default.Category,
        DestinasiKategoriHome.route,
        Color(0xFF9C27B0) // Purple
    )
)