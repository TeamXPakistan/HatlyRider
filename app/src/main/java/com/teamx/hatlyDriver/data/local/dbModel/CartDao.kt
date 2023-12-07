package com.teamx.hatlyDriver.data.local.dbModel

import androidx.lifecycle.LiveData
import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface CartDao {

    @Query("SELECT * FROM cart_table")
    fun getAllProducts(): LiveData<List<CartTable>>

    @Query("SELECT * FROM cart_table")
    fun getAllProducts2(): List<CartTable>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(product: CartTable)

    @Query("DELETE FROM cart_table")
    suspend fun deleteAllCart()

    @Update
    suspend fun update(cart: CartTable)


    @Query("DELETE FROM cart_table WHERE id = :userId")
    suspend fun deleteByProductId(userId: Int)

    @Query("SELECT * FROM cart_table")
    fun getAllCartItems2(): Flow<List<CartTable>>
}