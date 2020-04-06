package com.alyjak.shopping.ui.addproduct

import com.alyjak.shopping.database.ShoppingListDatabase
import com.alyjak.shopping.database.model.Product
import com.alyjak.shopping.ui.base.BasePresenter
import kotlinx.coroutines.launch

class NewProductPresenter(private val view: NewProductActivity, val listId: Long) : BasePresenter(view) {

    private val dataSource = ShoppingListDatabase.getInstance(view.application).productDao

    fun performOkButtonClick(newListName: String) {
        if (newListName.isBlank()) {
            view.showTextInputError()
        } else {
            coroutineContext.launch {
                val newProduct = Product(name = newListName, shoppingListId = listId)
                dataSource.insert(newProduct)
                //TODO consider add some elements like amount to view
                view.finishActivity()
            }
        }
    }

    fun performCancelButtonClick() {
        view.finishActivity()
    }

}