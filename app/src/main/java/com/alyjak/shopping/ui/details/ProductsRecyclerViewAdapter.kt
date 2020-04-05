package com.alyjak.shopping.ui.details


import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.alyjak.shopping.R
import com.alyjak.shopping.database.model.Product
import kotlinx.android.synthetic.main.layout_product.view.*

class ProductsRecyclerViewAdapter(
    private val mValues: List<Product>,
    private val activity: ProductsActivity
) : RecyclerView.Adapter<ProductsRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_product, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = mValues[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int = mValues.size

    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {

        @SuppressLint("SetTextI18n")
        fun bind(item: Product) {
            mView.productName.text = item.name
            mView.deleteButton.setOnClickListener {
                activity.setSelectedItem(item)
                DeleteProductDialogFragment()
                    .show(activity.supportFragmentManager, this@ProductsRecyclerViewAdapter::class.java.simpleName)
            }
        }

        override fun toString(): String {
            return super.toString() + " '" + mView.productName.text + "'"
        }
    }
}
