package com.alyjak.shopping.ui.history;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import androidx.fragment.app.DialogFragment;
import com.alyjak.shopping.R;
import org.jetbrains.annotations.NotNull;

public class ActivateListDialogFragment extends DialogFragment {

    public interface ActivateListDialogFragmentListener {
        void onYes();
    }

    @NotNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new AlertDialog.Builder(getActivity())
                .setTitle(getString(R.string.title_activate_list))
                .setMessage(getString(R.string.message_activate_list))
                .setPositiveButton(android.R.string.yes, (dialog, which) -> {
                    ((ActivateListDialogFragmentListener) getActivity()).onYes();
                    dialog.cancel();
                })
                .setNegativeButton(android.R.string.no, (dialog, which) -> dialog.cancel())
                .create();
    }
}