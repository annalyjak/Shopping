package com.alyjak.shopping.ui.details

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.alyjak.shopping.R

class DeleteProductDialogFragment : DialogFragment() {
    interface DeleteProductDialogFragmentListener {
        fun onYesDeleteProduct()
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(activity)
            .setTitle(getString(R.string.title_delete_product))
            .setMessage(getString(R.string.message_delete_product))
            .setPositiveButton(
                android.R.string.yes
            ) { dialog: DialogInterface, which: Int ->
                (activity as DeleteProductDialogFragmentListener?)!!.onYesDeleteProduct()
                dialog.cancel()
            }
            .setNegativeButton(
                android.R.string.no
            ) { dialog: DialogInterface, which: Int -> dialog.cancel() }
            .create()
    }
}