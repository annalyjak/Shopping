package com.alyjak.shopping.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "shopping_list_table")
data class ShoppingList (
    @PrimaryKey(autoGenerate = true)
    var listId: Long = 0L,

    @ColumnInfo(name = "time_of_creation")
    val timeOfCreation: Long = System.currentTimeMillis(),

    @ColumnInfo(name = "list_name")
    var listName: String? = "",

    @ColumnInfo(name = "archived")
    var archived: Boolean = false
)