package com.alyjak.shopping.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.alyjak.shopping.R
import com.alyjak.shopping.database.model.ShoppingListWithProducts
import com.alyjak.shopping.ui.base.BaseView
import com.alyjak.shopping.ui.details.ProductsActivity
import com.alyjak.shopping.ui.details.ProductsActivity.Companion.SELECTED_LIST_ID
import com.alyjak.shopping.ui.history.ActivateListDialogFragment
import com.alyjak.shopping.ui.history.HistoryFragment
import com.alyjak.shopping.ui.home.ShoppingListsFragment
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainActivity : AppCompatActivity(),
    BaseView,
    ActivateListDialogFragment.ActivateListDialogFragmentListener,
    ShoppingListsFragment.OnShoppingListFragmentInteractionListener,
    HistoryFragment.OnHistoryListFragmentInteractionListener {

    lateinit var presenter: MainPresenter

    var selectedFragment: Fragment? = null

    companion object{
        private const val SELECTED_FRAGMENT = "SELECTED_FRAGMENT"
        private const val SELECTED_ITEM = "SELECTED_ITEM"
    }

    private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                if (selectedFragment !is ShoppingListsFragment) {
                    selectedFragment = ShoppingListsFragment.newInstance(1)
                    supportFragmentManager.beginTransaction().replace(R.id.contentLayout, selectedFragment!!).commit()
                }
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_history -> {
                if (selectedFragment !is HistoryFragment) {
                    selectedFragment = HistoryFragment.newInstance()
                    supportFragmentManager.beginTransaction().replace(R.id.contentLayout, selectedFragment!!).commit()
                }
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        presenter = MainPresenter(this)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        navView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)
        if (savedInstanceState == null) {
            // inflate first view if orientation not changed
            selectedFragment = ShoppingListsFragment.newInstance(1)
            supportFragmentManager.beginTransaction().replace(R.id.contentLayout, selectedFragment!!).commit()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        if (selectedFragment != null) {
            val selectedFragmentName = selectedFragment!!::class.java.simpleName
            outState.putString(SELECTED_FRAGMENT, selectedFragmentName)
        }
        var selectedId = presenter.selectedItem?.shoppingList?.listId
        if (selectedId != null) {
            outState.putLong(SELECTED_ITEM, selectedId)
        }
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        when (savedInstanceState.getString(SELECTED_FRAGMENT)) {
            HistoryFragment::class.java.simpleName -> {
                selectedFragment = HistoryFragment.newInstance()
                supportFragmentManager.beginTransaction().replace(R.id.contentLayout, selectedFragment!!).commit()
            }
            else -> {
                selectedFragment = ShoppingListsFragment.newInstance(1)
                supportFragmentManager.beginTransaction().replace(R.id.contentLayout, selectedFragment!!).commit()
            }
        }
        val long = savedInstanceState.getLong(SELECTED_ITEM)
        presenter.setSelectedShoppingListWithProducts(long)
        super.onRestoreInstanceState(savedInstanceState)
    }

    override fun onShoppingListFragmentInteraction(item: ShoppingListWithProducts?) {
        val showProductsActivity = Intent(this, ProductsActivity::class.java)
        showProductsActivity.putExtra(SELECTED_LIST_ID, item?.shoppingList?.listId as Long)
        startActivity(showProductsActivity)
    }

    override fun onHistoryListFragmentInteraction(item: ShoppingListWithProducts?) {
        presenter.setSelectedShoppingListWithProducts(item)
        ActivateListDialogFragment().show(supportFragmentManager, this::class.java.simpleName)
    }

    override fun onYes() {
        presenter.updateShoppingList()
    }
}
