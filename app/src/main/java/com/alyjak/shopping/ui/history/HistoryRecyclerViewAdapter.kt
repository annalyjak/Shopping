package com.alyjak.shopping.ui.history


import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.alyjak.shopping.R
import com.alyjak.shopping.database.model.ShoppingListWithProducts
import com.alyjak.shopping.formatDateToString
import com.alyjak.shopping.ui.history.HistoryFragment.OnHistoryListFragmentInteractionListener
import kotlinx.android.synthetic.main.fragment_history.view.*

class HistoryRecyclerViewAdapter(
    private val mValues: List<ShoppingListWithProducts>,
    private val mListener: OnHistoryListFragmentInteractionListener?
) : RecyclerView.Adapter<HistoryRecyclerViewAdapter.ViewHolder>() {

    private val mOnClickListener: View.OnClickListener

    init {
        mOnClickListener = View.OnClickListener { v ->
            val item = v.tag as ShoppingListWithProducts
            mListener?.onHistoryListFragmentInteraction(item)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_history, parent, false)
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
            mView.date.text = "(${item.shoppingList.timeOfCreation.formatDateToString()})"
            val products = item.products.size
            if (products == 1) {
                mView.content.text = "$products item"
            } else {
                mView.content.text = "$products items"
            }
        }

        override fun toString(): String {
            return super.toString() + " '" + mView.content.text + "'"
        }
    }
}
