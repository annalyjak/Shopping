package com.alyjak.shopping.database.model

import androidx.room.Embedded
import androidx.room.Relation

data class ShoppingListWithProducts (
    @Embedded val shoppingList: ShoppingList,
    @Relation(
        parentColumn = "listId",
        entityColumn = "shoppingListId"
    )
    val products: List<Product>
)