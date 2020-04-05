package com.alyjak.shopping.ui.home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alyjak.shopping.R
import com.alyjak.shopping.ui.base.BaseView
import com.alyjak.shopping.database.model.ShoppingListWithProducts
import com.alyjak.shopping.ui.createnewlist.CreateShoppingListActivity
import kotlinx.android.synthetic.main.fragment_shoppinglist_list.view.*
import java.util.*

class ShoppingListsFragment : Fragment(), BaseView {

    private var columnCount = 1

    private var listener: OnShoppingListFragmentInteractionListener? = null

    private lateinit var presenter: ShoppingListPresenter
    lateinit var shoppingListAdapter: ShoppingListRecyclerViewAdapter
    val ITEMS: MutableList<ShoppingListWithProducts> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            columnCount = it.getInt(ARG_COLUMN_COUNT)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_shoppinglist_list, container, false)
        presenter = ShoppingListPresenter(this)

        shoppingListAdapter = ShoppingListRecyclerViewAdapter(ITEMS, listener)
        // Set the adapter
        with(view.list) {
            layoutManager = when {
                columnCount <= 1 -> LinearLayoutManager(context)
                else -> GridLayoutManager(context, columnCount)
            }
            adapter = shoppingListAdapter
        }

        view.floatingActionButton.setOnClickListener {
            val openCreatingNewList = Intent(activity, CreateShoppingListActivity::class.java)
            startActivity(openCreatingNewList)
        }

        view.list.addOnScrollListener(object: RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dy > 0 && view.floatingActionButton.visibility == View.VISIBLE) {
                    view.floatingActionButton.hide()
                } else if (dy < 0 && view.floatingActionButton.visibility != View.VISIBLE){
                    view.floatingActionButton.show()
                }
            }
        })

        return view
    }

    override fun onResume() {
        super.onResume()
        presenter.getAllActiveList()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnShoppingListFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnListFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    interface OnShoppingListFragmentInteractionListener {
        fun onShoppingListFragmentInteraction(item: ShoppingListWithProducts?)
    }

    companion object {

        const val ARG_COLUMN_COUNT = "column-count"

        @JvmStatic
        fun newInstance(columnCount: Int) =
            ShoppingListsFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_COLUMN_COUNT, columnCount)
                }
            }
    }
}
