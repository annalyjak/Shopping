package com.alyjak.shopping.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "product_table")
data class Product (
    @PrimaryKey(autoGenerate = true)
    var productId: Long = 0L,

    @ColumnInfo(name = "shoppingListId")
    val shoppingListId: Long,

    @ColumnInfo(name = "name")
    var name: String? = "",

    @ColumnInfo(name = "price")
    var price: Double? = 0.0,

    @ColumnInfo(name = "amount")
    var amount: Int = 1,

    @ColumnInfo(name = "bought")
    var bought: Boolean = false
)