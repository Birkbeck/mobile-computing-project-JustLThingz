package co.uk.bbk.culinarycompanion.ui.dialog

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import co.uk.bbk.culinarycompanion.R

/**
 * Dialogue to confirm recipe deletion
 */
class DeleteConfirmationDialog(
    private val recipeName: String,
    private val onConfirm: () -> Unit
) : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(requireContext())
            .setTitle(getString(R.string.dialog_delete_title))
            .setMessage(getString(R.string.dialog_delete_message, recipeName))
            .setPositiveButton(getString(R.string.btn_yes)) { _, _ ->
                onConfirm()
            }
            .setNegativeButton(getString(R.string.btn_no)) { dialog, _ ->
                dialog.dismiss()
            }
            .create()
    }
}