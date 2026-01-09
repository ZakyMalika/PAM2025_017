package com.example.proditi.uicontroller.route

interface DestinasiNavigasi {
    val route: String
    val titleRes: String
}

object DestinasiDashboard : DestinasiNavigasi {
    override val route = "dashboard"
    override val titleRes = "Dashboard Prodi TI"
}

object DestinasiHome : DestinasiNavigasi {
    override val route = "home"
    override val titleRes = "Daftar Peminjaman"
}

object DestinasiEntry : DestinasiNavigasi {
    override val route = "entry"
    override val titleRes = "Tambah Peminjaman"
}

object DestinasiDetail : DestinasiNavigasi {
    override val route = "detail"
    override val titleRes = "Detail Peminjaman"
    const val peminjamanId = "itemId"
    val routeWithArgs = "$route/{$peminjamanId}"
}

object DestinasiEdit : DestinasiNavigasi {
    override val route = "edit"
    override val titleRes = "Edit Peminjaman"
    const val peminjamanId = "itemId"
    val routeWithArgs = "$route/{$peminjamanId}"
}