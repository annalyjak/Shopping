package com.alyjak.shopping.ui.history

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.alyjak.shopping.R
import com.alyjak.shopping.database.model.ShoppingListWithProducts
import com.alyjak.shopping.ui.base.BaseView
import kotlinx.android.synthetic.main.fragment_history_list.*
import kotlinx.android.synthetic.main.fragment_history_list.view.*

class HistoryFragment : Fragment(), BaseView {

    private var listener: OnHistoryListFragmentInteractionListener? = null

    private lateinit var presenter: HistoryPresenter
    lateinit var historyRecyclerViewAdapter: HistoryRecyclerViewAdapter

    val ITEMS: MutableList<ShoppingListWithProducts> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_history_list, container, false)
        presenter = HistoryPresenter(this)
        historyRecyclerViewAdapter = HistoryRecyclerViewAdapter(ITEMS, listener)

        with(view.archiveList) {
            layoutManager = LinearLayoutManager(context)
            adapter = historyRecyclerViewAdapter
        }

        view.deleteAllArchived.setOnClickListener {
            presenter.deleteAllArchived()
        }

        return view
    }

    override fun onResume() {
        super.onResume()
        presenter.getAllArchivedList()
    }

    fun updateView() {
        presenter.getAllArchivedList()
    }

    fun showInfoNoElementsOnList() {
        infoNoArchivedList.visibility = View.VISIBLE
    }

    fun hideInfoNoElementsOnList() {
        infoNoArchivedList.visibility = View.GONE
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnHistoryListFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException("$context must implement OnListFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    interface OnHistoryListFragmentInteractionListener {
        fun onHistoryListFragmentInteraction(item: ShoppingListWithProducts?)
    }

    companion object {

        @JvmStatic
        fun newInstance() = HistoryFragment()
    }
}
