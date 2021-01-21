package com.kelompok.uas_oop2.database

import androidx.room.*

@Dao
interface CustomerDao {
    @Query("SELECT * FROM customer ORDER BY id ASC")
    fun getCustomer(): List<Customer>

    @Query("SELECT * FROM customer WHERE id=:customer_id")
    suspend fun getCustomerId(customer_id: String): List<Customer>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(customer: Customer)

    @Update
    fun updateCustomer(customer: Customer)

    @Delete
    fun deleteCustomer(customer: Customer)

}