package com.alyjak.shopping.ui.details

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.alyjak.shopping.R
import kotlinx.android.synthetic.main.layout_change_list_name.*
import kotlinx.android.synthetic.main.layout_change_list_name.view.*

class EditListNameDialogFragment : DialogFragment() {
    interface EditListNameDialogFragmentListener {
        fun onYesChangeName(newName: String)
    }

    @SuppressLint("InflateParams")
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val view = activity?.layoutInflater?.inflate(R.layout.layout_change_list_name, null)
        val alert = AlertDialog.Builder(activity)
            .setView(view)
            .setTitle(getString(R.string.title_change_list_name))
            .create()
        view?.buttonOk?.setOnClickListener {
            if (view.changeNameTextInputEditText?.text.isNullOrEmpty()) {
                displayError()
            } else {
                (activity as EditListNameDialogFragmentListener?)!!
                    .onYesChangeName(view.changeNameTextInputEditText?.text.toString())
                alert.cancel()
            }
        }
        view?.buttonCancel?.setOnClickListener {
            alert.cancel()
        }
        return alert
    }

    private fun displayError() {
        changeNameTextInputLayout.error = "New name cannot be empty!"
    }
}