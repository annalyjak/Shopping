package com.alyjak.shopping.ui.details

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.lifecycle.Observer
import com.alyjak.shopping.R
import com.alyjak.shopping.database.model.Product
import com.alyjak.shopping.ui.addproduct.NewProductActivity
import com.alyjak.shopping.ui.addproduct.NewProductActivity.Companion.LIST_ID
import com.alyjak.shopping.ui.base.BaseView
import kotlinx.android.synthetic.main.activity_products.*

class ProductsActivity : AppCompatActivity(), BaseView,
    EditListNameDialogFragment.EditListNameDialogFragmentListener,
    DeleteProductDialogFragment.DeleteProductDialogFragmentListener {

    private lateinit var presenter: ProductsPresenter
    lateinit var productsAdapter: ProductsRecyclerViewAdapter

    val ITEMS: MutableList<Product> = ArrayList()

    companion object {
        const val SELECTED_LIST_ID = "SELECTED_LIST_ID"
        const val SELECTED_PRODUCT_ITEM = "SELECTED_PRODUCT_ITEM"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_products)

        presenter = ProductsPresenter(this)
        productsAdapter = ProductsRecyclerViewAdapter(ITEMS, this)

        val listId = intent.getLongExtra(SELECTED_LIST_ID, -1L)
        presenter.setShoppingList(listId)

        with(productsRecyclerView) {
            layoutManager = androidx.recyclerview.widget.LinearLayoutManager(context)
            adapter = productsAdapter
        }

        floatingProductActionButton.setOnClickListener {
            val openNewProductActivity = Intent(this, NewProductActivity::class.java)
            openNewProductActivity.putExtra(LIST_ID, listId)
            startActivity(openNewProductActivity)
        }
    }

    override fun onResume() {
        super.onResume()
        presenter.showListDetails()

        presenter.products.observe(this, Observer {
            if (it.isNullOrEmpty()) {
                hideRecycleView()
                showNoProductsView()
            } else {
                showRecycleView()
                hideNoProductsView()
            }
        })
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        var selectedId = presenter.selectedProduct?.productId
        if (selectedId != null) {
            outState.putLong(SELECTED_PRODUCT_ITEM, selectedId)
        }
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        val long = savedInstanceState.getLong(SELECTED_PRODUCT_ITEM)
        presenter.setSelectedItem(long)
        super.onRestoreInstanceState(savedInstanceState)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.products_activity_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.action_edit_list_name -> {
                openEditListDialog()
                true
            }
            R.id.action_delete_list -> {
                presenter.archiveShoppingList()
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }

    private fun openEditListDialog() {
        EditListNameDialogFragment().show(supportFragmentManager, EditListNameDialogFragment::class.java.simpleName)
    }

    fun setListName(listName: String?) {
        listNameTextView.text = listName
    }

    fun hideRecycleView() {
        productsRecyclerView.visibility = View.GONE
    }

    fun showRecycleView() {
        productsRecyclerView.visibility = View.VISIBLE
    }

    fun hideNoProductsView() {
        noProductsView.visibility = View.GONE
    }

    fun showNoProductsView() {
        noProductsView.visibility = View.VISIBLE
    }

    fun setSelectedItem(item: Product) {
        presenter.setSelectedItem(item)
    }

    override fun onYesDeleteProduct() {
        presenter.deleteSelectedProduct()
        presenter.showListDetails()
    }

    override fun onYesChangeName(newName: String) {
        presenter.changeListName(newName)
    }
}
