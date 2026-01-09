package com.example.proditi.uicontroller.route

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.proditi.uicontroller.view.*

@Composable
fun PengelolaHalaman(
    navController: NavHostController = rememberNavController(),
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = DestinasiDashboard.route,
        modifier = modifier
    ) {
        // Halaman Dashboard
        composable(DestinasiDashboard.route) {
            HalamanDashboard(
                onMenuClick = { route ->
                    navController.navigate(route)
                }
            )
        }

        // Halaman Home (Daftar Peminjaman)
        composable(DestinasiHome.route) {
            HalamanHome(
                navigateToItemEntry = { navController.navigate(DestinasiEntry.route) },
                onDetailClick = { itemId ->
                    navController.navigate("${DestinasiDetail.route}/$itemId")
                }
            )
        }

        // Halaman Entry (Tambah Peminjaman)
        composable(DestinasiEntry.route) {
            HalamanEntry(
                navigateBack = { navController.popBackStack() }
            )
        }

        // Halaman Detail
        composable(
            route = DestinasiDetail.routeWithArgs,
            arguments = listOf(navArgument(DestinasiDetail.peminjamanId) { type = NavType.IntType })
        ) {
            HalamanDetail(
                navigateBack = { navController.popBackStack() },
                navigateToEdit = { itemId ->
                    navController.navigate("${DestinasiEdit.route}/$itemId")
                }
            )
        }

        // Halaman Edit
        composable(
            route = DestinasiEdit.routeWithArgs,
            arguments = listOf(navArgument(DestinasiEdit.peminjamanId) { type = NavType.IntType })
        ) {
            HalamanEdit(
                navigateBack = { navController.popBackStack() }
            )
        }
        
        // Placeholder untuk Barang dan Peminjam
        composable("barang") {
            // Anda bisa membuat HalamanBarang yang serupa dengan HalamanHome
            Text("Halaman Data Barang (Segera Hadir)")
        }
        
        composable("peminjam") {
            // Anda bisa membuat HalamanPeminjam yang serupa dengan HalamanHome
            Text("Halaman Data Peminjam (Segera Hadir)")
        }
    }
}