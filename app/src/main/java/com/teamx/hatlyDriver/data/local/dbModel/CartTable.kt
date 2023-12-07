package com.teamx.hatlyDriver.data.local.dbModel

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cart_table")
data class CartTable(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo
    val productCount: Int = 1,
    @ColumnInfo(name = "name")
    val name: String?,
    @ColumnInfo(name = "modifier")
    val modifier: String,
    @ColumnInfo(name = "price")
    val price: Double = 0.0,
    @ColumnInfo(name = "imageUrl")
    val imageUrl: String,
    @ColumnInfo(name = "quantity")
    var quantity: Int = 1,
    @ColumnInfo(name = "variationId")
    var variationId: String = ""
)