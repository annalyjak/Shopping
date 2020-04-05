package com.alyjak.shopping.ui.home

import android.annotation.SuppressLint
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.alyjak.shopping.R
import com.alyjak.shopping.database.model.ShoppingListWithProducts
import com.alyjak.shopping.formatDateToString


import com.alyjak.shopping.ui.home.ShoppingListsFragment.OnShoppingListFragmentInteractionListener

import kotlinx.android.synthetic.main.fragment_shoppinglist.view.*

class ShoppingListRecyclerViewAdapter(
    private val mValues: List<ShoppingListWithProducts>,
    private val mListener: OnShoppingListFragmentInteractionListener?
) : RecyclerView.Adapter<ShoppingListRecyclerViewAdapter.ViewHolder>() {

    private val mOnClickListener: View.OnClickListener

    init {
        mOnClickListener = View.OnClickListener { v ->
            val item = v.tag as ShoppingListWithProducts
            mListener?.onShoppingListFragmentInteraction(item)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_shoppinglist, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = mValues[position]
        holder.bind(item)

        with(holder.mView) {
            tag = item
            setOnClickListener(mOnClickListener)
        }
    }

    override fun getItemCount(): Int = mValues.size

    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {

        @SuppressLint("SetTextI18n")
        fun bind(item: ShoppingListWithProducts) {
            mView.item_number.text = item.shoppingList.listName
            mView.content.text = "${item.products.size} products"
            mView.dateTextView.text = "Date of creation: ${item.shoppingList.timeOfCreation.formatDateToString()}"
        }

        override fun toString(): String {
            return super.toString() + " '" + mView.content.text + "'"
        }
    }
}
