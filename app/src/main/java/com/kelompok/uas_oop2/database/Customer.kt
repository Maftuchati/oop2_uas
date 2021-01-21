package com.kelompok.uas_oop2.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Customer")
data class Customer (
    @PrimaryKey val id: String,
    @ColumnInfo(name = "nama_customer") val nama_customer: String,
    @ColumnInfo(name = "umur") val umur: String,
    @ColumnInfo(name = "no_hp") val no_hp: String,
    @ColumnInfo(name = "alamat") val alamat: String,
    @ColumnInfo(name = "kode") val kode_customer: String,
)