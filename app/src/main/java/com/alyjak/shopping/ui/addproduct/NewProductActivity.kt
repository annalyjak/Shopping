package com.alyjak.shopping.ui.addproduct

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import com.alyjak.shopping.R
import com.alyjak.shopping.ui.base.BaseView
import kotlinx.android.synthetic.main.activity_new_product.*

class NewProductActivity : AppCompatActivity(), BaseView {

    private lateinit var presenter: NewProductPresenter

    companion object {
        const val SELECTED_NAME = "SELECTED_NAME"
        const val LIST_ID = "LIST_ID"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_product)
        val listId = intent.getLongExtra(LIST_ID, -1L)
        presenter = NewProductPresenter(this, listId)

        initButtons()
        productNameTextInputEditText.addTextChangedListener {
            if (it != null && it.isNotEmpty()) {
                productNameTextInputLayout.error = null
            }
        }

        if (savedInstanceState != null) {
            productNameTextInputEditText.setText(savedInstanceState.getString(SELECTED_NAME))
        }
    }

    private fun initButtons() {
        okProductButton.setOnClickListener {
            val newListName = productNameTextInputLayout.editText?.text.toString()
            presenter.performOkButtonClick(newListName)
        }

        cancelProductButton.setOnClickListener {
            presenter.performCancelButtonClick()
        }
    }

    fun showTextInputError() {
        productNameTextInputLayout.error = "Product name cannot be null!"
    }

    fun finishActivity() {
        runOnUiThread {
            finish()
        }
    }
}
