package co.uk.bbk.culinarycompanion.ui.dialog

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import co.uk.bbk.culinarycompanion.R

/**
 * Dialog to warn about unsaved changes
 */
class UnsavedChangesDialog(
    private val onConfirm: () -> Unit
) : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(requireContext())
            .setTitle(getString(R.string.dialog_unsaved_title))
            .setMessage(getString(R.string.dialog_unsaved_message))
            .setPositiveButton(getString(R.string.btn_yes)) { _, _ ->
                onConfirm()
            }
            .setNegativeButton(getString(R.string.btn_no)) { dialog, _ ->
                dialog.dismiss()
            }
            .create()
    }
}