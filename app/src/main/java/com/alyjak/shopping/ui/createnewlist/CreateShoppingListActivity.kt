package com.alyjak.shopping.ui.createnewlist

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.widget.addTextChangedListener
import com.alyjak.shopping.R
import com.alyjak.shopping.ui.base.BaseView
import com.alyjak.shopping.ui.details.ProductsActivity
import kotlinx.android.synthetic.main.activity_create_shopping_list.*

class CreateShoppingListActivity : AppCompatActivity(), BaseView {

    lateinit var presenter: CreateShoppingListPresenter

    companion object {
        const val SELECTED_NAME = "SELECTED_NAME"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_shopping_list)
        presenter = CreateShoppingListPresenter(this)

        initButtons()
        textInputEditText.addTextChangedListener {
            if (it != null && it.isNotEmpty()) {
                textInputLayout.error = null
            }
        }

        if (savedInstanceState != null) {
            textInputEditText.setText(savedInstanceState.getString(SELECTED_NAME))
        }
    }

    private fun initButtons() {
        okButton.setOnClickListener {
            val newListName = textInputLayout.editText?.text.toString()
            presenter.performOkButtonClick(newListName)
        }

        cancelButton.setOnClickListener {
            presenter.performCancelButtonClick()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(SELECTED_NAME, textInputLayout.editText?.text.toString())
    }

    fun showTextInputError() {
        textInputLayout.error = "List name cannot be empty!"
    }

    fun showListDetails(listId: Long) {
        val showProductsActivity = Intent(this, ProductsActivity::class.java)
        showProductsActivity.putExtra(ProductsActivity.SELECTED_LIST_ID, listId)
        startActivity(showProductsActivity)
    }
}
