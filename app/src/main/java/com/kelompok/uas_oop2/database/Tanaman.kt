package com.kelompok.uas_oop2.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Tanaman")
data class Tanaman(
    @PrimaryKey val id: String,
    @ColumnInfo(name = "nama_tanaman") val nama_tanaman: String,
    @ColumnInfo(name = "jenis") val jenis: String,
    @ColumnInfo(name = "ukuran") val ukuran: String,
    @ColumnInfo(name = "tinggi") val tinggi: String,
    @ColumnInfo(name = "harga") val harga: String,

)