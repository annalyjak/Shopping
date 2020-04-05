package com.alyjak.shopping.database

import androidx.room.*
import com.alyjak.shopping.database.model.Product

@Dao
interface ProductDao {

    @Insert
    fun insert(product: Product)

    @Update
    fun update(product: Product)

    @Delete
    fun delete(product: Product)

    @Delete
    fun delete(products: List<Product>)

    @Query("SELECT * from product_table WHERE productId = :key")
    fun get(key: Long): Product

    @Query("DELETE FROM product_table WHERE shoppingListId = :key")
    fun deleteAllProductsOnList(key: Long)

}