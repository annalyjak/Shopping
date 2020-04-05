package com.alyjak.shopping.ui

import com.alyjak.shopping.database.ShoppingListDatabase
import com.alyjak.shopping.database.model.ShoppingListWithProducts
import com.alyjak.shopping.ui.base.BasePresenter
import com.alyjak.shopping.ui.history.HistoryFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainPresenter(private val view: MainActivity) : BasePresenter(view) {

    private val shoppingListDao = ShoppingListDatabase.getInstance(view.application).shoppingListDao

    var selectedItem: ShoppingListWithProducts? = null

    fun updateShoppingList() {
        coroutineContext.launch {
            var shoppingList = selectedItem?.shoppingList
            shoppingList?.archived = false
            shoppingList?.let { shoppingListDao.update(it) }
            withContext(Dispatchers.Main) {
                if (view.selectedFragment is HistoryFragment) {
                    (view.selectedFragment as HistoryFragment).updateView()
                }
            }
            selectedItem = null
        }
    }

    fun setSelectedShoppingListWithProducts(item: ShoppingListWithProducts?) {
        selectedItem = item
    }

    fun setSelectedShoppingListWithProducts(itemId: Long) {
        coroutineContext.launch {
            selectedItem = shoppingListDao.getShoppingListWithProducts(itemId)
        }
    }
}