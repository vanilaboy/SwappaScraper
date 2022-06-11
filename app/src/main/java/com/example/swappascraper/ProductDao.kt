package com.example.swappascraper

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ProductDao {

    @Query("SELECT * FROM ${Product.TABLE_NAME}")
    fun getAll(): LiveData<List<Product>>

    @Delete
    suspend fun delete(item: Product)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun add(item: Product)

}