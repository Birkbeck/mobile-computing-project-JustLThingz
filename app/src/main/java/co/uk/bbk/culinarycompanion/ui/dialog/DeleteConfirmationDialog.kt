/**
 * Culinary Companion - Recipe Management App
 * BSc Computing Coursework 2
 *
 * Confirmation dialog for recipe deletion.
 * Prevents accidental deletion of recipes.
 *
 * @author Levi GIRARD
 * @studentId 13401249
 */

package co.uk.bbk.culinarycompanion.ui.dialog

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import co.uk.bbk.culinarycompanion.R

/**
 * Dialog to confirm recipe deletion
 *
 * @param recipeName The name of the recipe to be deleted
 * @param onConfirm Callback function executed when user confirms deletion
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