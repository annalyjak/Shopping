package com.alyjak.shopping.ui.createnewlist

import com.alyjak.shopping.ui.base.BasePresenter
import com.alyjak.shopping.database.ShoppingListDatabase
import com.alyjak.shopping.database.model.ShoppingList
import kotlinx.coroutines.*

class CreateShoppingListPresenter(private val view: CreateShoppingListActivity) : BasePresenter(view) {

    private val dataSource = ShoppingListDatabase.getInstance(view.application).shoppingListDao

    fun performOkButtonClick(newListName: String) {
        if (newListName.isBlank()) {
            view.showTextInputError()
        } else {
            coroutineContext.launch {
                val newList = ShoppingList(listName = newListName)
                val listId = dataSource.insert(newList)
                withContext(Dispatchers.Main) {
                    view.showListDetails(listId)
                    view.finish()
                }
            }
        }
    }

    fun performCancelButtonClick() {
        view.finish()
    }

}