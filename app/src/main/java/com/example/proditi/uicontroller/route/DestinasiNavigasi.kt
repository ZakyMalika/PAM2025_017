package com.example.proditi.uicontroller.route

interface DestinasiNavigasi {
    val route: String
    val titleRes: String
}

object DestinasiDashboard : DestinasiNavigasi {
    override val route = "dashboard"
    override val titleRes = "Dashboard Prodi TI"
}

// Destinasi Peminjaman
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

// Destinasi Barang
object DestinasiBarangHome : DestinasiNavigasi {
    override val route = "barang_home"
    override val titleRes = "Data Barang"
}
object DestinasiBarangEntry : DestinasiNavigasi {
    override val route = "barang_entry"
    override val titleRes = "Tambah Barang"
}
object DestinasiBarangDetail : DestinasiNavigasi {
    override val route = "barang_detail"
    override val titleRes = "Detail Barang"
    const val barangId = "barangId"
    val routeWithArgs = "$route/{$barangId}"
}
object DestinasiBarangEdit : DestinasiNavigasi {
    override val route = "barang_edit"
    override val titleRes = "Edit Barang"
    const val barangId = "barangId"
    val routeWithArgs = "$route/{$barangId}"
}

// Destinasi Peminjam
object DestinasiPeminjamHome : DestinasiNavigasi {
    override val route = "peminjam_home"
    override val titleRes = "Data Peminjam"
}
object DestinasiPeminjamEntry : DestinasiNavigasi {
    override val route = "peminjam_entry"
    override val titleRes = "Tambah Peminjam"
}
object DestinasiPeminjamDetail : DestinasiNavigasi {
    override val route = "peminjam_detail"
    override val titleRes = "Detail Peminjam"
    const val peminjamId = "peminjamId"
    val routeWithArgs = "$route/{$peminjamId}"
}
object DestinasiPeminjamEdit : DestinasiNavigasi {
    override val route = "peminjam_edit"
    override val titleRes = "Edit Peminjam"
    const val peminjamId = "peminjamId"
    val routeWithArgs = "$route/{$peminjamId}"
}

// Destinasi Kategori
object DestinasiKategoriHome : DestinasiNavigasi {
    override val route = "kategori_home"
    override val titleRes = "Data Kategori"
}
object DestinasiKategoriEntry : DestinasiNavigasi {
    override val route = "kategori_entry"
    override val titleRes = "Tambah Kategori"
}
object DestinasiKategoriDetail : DestinasiNavigasi {
    override val route = "kategori_detail"
    override val titleRes = "Detail Kategori"
    const val kategoriId = "kategoriId"
    val routeWithArgs = "$route/{$kategoriId}"
}
object DestinasiKategoriEdit : DestinasiNavigasi {
    override val route = "kategori_edit"
    override val titleRes = "Edit Kategori"
    const val kategoriId = "kategoriId"
    val routeWithArgs = "$route/{$kategoriId}"
}
