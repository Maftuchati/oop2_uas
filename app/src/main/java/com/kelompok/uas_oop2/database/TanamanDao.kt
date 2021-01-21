package com.kelompok.uas_oop2.database

import androidx.room.*


@Dao
interface TanamanDao {
    @Query("SELECT * FROM tanaman ORDER BY id ASC")
    suspend fun getTanaman(): List<Tanaman>

    @Query("SELECT * FROM tanaman WHERE id=:tanaman_id")
    suspend fun getTanamanId(tanaman_id: String): List<Tanaman>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(tanaman: Tanaman)

    @Update
    suspend fun updateTanaman(tanaman: Tanaman)

    @Delete
    suspend fun deleteTanaman(tanaman: Tanaman)


}