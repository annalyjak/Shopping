package com.alyjak.shopping.database

import androidx.room.*
import com.alyjak.shopping.database.model.ShoppingList
import com.alyjak.shopping.database.model.ShoppingListWithProducts

@Dao
interface ShoppingListDao {

    @Insert
    fun insert(shoppingList: ShoppingList): Long

    @Update
    fun update(shoppingList: ShoppingList)

    @Delete
    fun delete(shoppingList: ShoppingList)

    @Query("SELECT * from shopping_list_table WHERE listId = :key")
    fun get(key: Long): ShoppingList

    @Transaction
    @Query("SELECT * from shopping_list_table WHERE listId = :key")
    fun getShoppingListWithProducts(key: Long): ShoppingListWithProducts

    @Transaction
    @Query("SELECT * FROM shopping_list_table WHERE archived = 0 ORDER BY time_of_creation DESC")
    fun getAllActiveShoppingList(): List<ShoppingListWithProducts>

    @Transaction
    @Query("SELECT * FROM shopping_list_table WHERE archived ORDER BY time_of_creation DESC")
    fun getAllArchiveShoppingList(): List<ShoppingListWithProducts>

    @Query("DELETE FROM shopping_list_table")
    fun clear()

}