package com.alyjak.shopping.ui.home

import com.alyjak.shopping.ui.base.BasePresenter
import com.alyjak.shopping.database.ShoppingListDatabase
import kotlinx.coroutines.*

class ShoppingListPresenter(private val view: ShoppingListsFragment): BasePresenter(view) {

    private val dataSource = view.activity?.application?.let { ShoppingListDatabase.getInstance(it).shoppingListDao }

    fun getAllActiveList() {
        coroutineContext.launch {
            val allActiveShoppingList = dataSource?.getAllActiveShoppingList()
            withContext(Dispatchers.Main) {
                view.ITEMS.clear()
                if (allActiveShoppingList != null) {
                    view.ITEMS.addAll(allActiveShoppingList)
                }
                view.shoppingListAdapter.notifyDataSetChanged()
                showOrHideInfoAboutNoShoppingList()
            }
        }
    }

    private fun showOrHideInfoAboutNoShoppingList() {
        if (view.ITEMS.isNullOrEmpty()) {
            view.showNoShoppingListLayout()
        } else {
            view.hideNoShoppingListLayout()
        }
    }

}