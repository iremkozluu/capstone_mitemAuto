package com.ikozlu.capstone_mitemauto.data.source.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ikozlu.capstone_mitemauto.data.model.ProductEntity

@Dao
interface ProductDao {

    @Query("SELECT * FROM favproducts")
    suspend fun getFavoriteProducts(): List<ProductEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addToFavorites(product: ProductEntity)

    @Delete
    suspend fun removeFromFavorites(product: ProductEntity)

    @Query("SELECT id FROM favproducts")
    suspend fun getFavoriteIds(): List<Int>

}