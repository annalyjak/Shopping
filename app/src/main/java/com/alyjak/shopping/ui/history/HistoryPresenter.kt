package com.alyjak.shopping.ui.history

import com.alyjak.shopping.ui.base.BasePresenter
import com.alyjak.shopping.database.ShoppingListDatabase
import kotlinx.coroutines.*

class HistoryPresenter(private val view: HistoryFragment): BasePresenter(view) {

    private val dataSource = view.activity?.application?.let { ShoppingListDatabase.getInstance(it).shoppingListDao }
    private val productDao = view.activity?.application?.let { ShoppingListDatabase.getInstance(it).productDao }

    fun getAllArchivedList() {
        coroutineContext.launch {
            getList()
        }
    }

    private suspend fun getList() {
        val allActiveShoppingList = dataSource?.getAllArchiveShoppingList()
        withContext(Dispatchers.Main) {
            view.ITEMS.clear()
            if (allActiveShoppingList != null) {
                view.ITEMS.addAll(allActiveShoppingList)
            }
            if (view.ITEMS.isNullOrEmpty()) {
                view.showInfoNoElementsOnList()
            } else {
                view.hideInfoNoElementsOnList()
            }
            view.historyRecyclerViewAdapter.notifyDataSetChanged()
        }
    }

    fun deleteAllArchived() {
        coroutineContext.launch {
            val allActiveShoppingList = dataSource?.getAllArchiveShoppingList()
            allActiveShoppingList?.forEach {
                productDao?.delete(it.products)
                dataSource?.delete(it.shoppingList)
            }
            getList()
        }
    }

}