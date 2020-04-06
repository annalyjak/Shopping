package com.alyjak.shopping.ui.details

import androidx.lifecycle.MutableLiveData
import com.alyjak.shopping.database.ShoppingListDatabase
import com.alyjak.shopping.database.model.Product
import com.alyjak.shopping.database.model.ShoppingListWithProducts
import com.alyjak.shopping.ui.base.BasePresenter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ProductsPresenter(private val view: ProductsActivity) : BasePresenter(view) {

    private val shoppingListDao = ShoppingListDatabase.getInstance(view.application).shoppingListDao
    private val productDao = ShoppingListDatabase.getInstance(view.application).productDao

    var shoppingListId: Long = -1L
    private var shoppingListWithProducts = MutableLiveData<ShoppingListWithProducts>()
    var products = MutableLiveData<List<Product>>()
    var selectedProduct: Product? = null

    fun setShoppingList(listId: Long) {
        shoppingListId = listId
    }

    fun showListDetails() {
        coroutineContext.launch {
            val list = shoppingListDao.getShoppingListWithProducts(shoppingListId)
            withContext(Dispatchers.Main) {
                shoppingListWithProducts.value = list
                if (list != null) {
                    products.value = list.products

                }
                view.setListName(shoppingListWithProducts.value?.shoppingList?.listName)
                view.ITEMS.clear()
                if (products.value.isNullOrEmpty()) {
                    view.hideRecycleView()
                    view.showNoProductsView()
                } else {
                    view.showRecycleView()
                    view.hideNoProductsView()
                    view.ITEMS.addAll(products.value!!)
                }
                view.productsAdapter.notifyDataSetChanged()
            }
        }
    }

    fun archiveShoppingList() {
        coroutineContext.launch {
            val shoppingList = shoppingListWithProducts.value?.shoppingList
            if (shoppingList != null) {
                shoppingList.archived = true
                shoppingListDao.update(shoppingList)
            }
            withContext(Dispatchers.Main) {
                view.finish()
            }
        }
    }

    fun setSelectedItem(itemId: Long) {
        coroutineContext.launch {
            selectedProduct = productDao.get(itemId)
        }
    }

    fun setSelectedItem(item: Product) {
        selectedProduct = item
    }

    fun deleteSelectedProduct() {
        if (selectedProduct != null) {
            coroutineContext.launch {
                productDao.delete(selectedProduct!!)
            }
        }
    }

    fun changeListName(newName: String) {
        val shoppingList = shoppingListWithProducts.value?.shoppingList
        shoppingList?.listName = newName
        coroutineContext.launch {
            shoppingList?.let { shoppingListDao.update(it) }
            showListDetails()
        }
    }

}
