/**
 * Culinary Companion - Recipe Management App
 * BSc Computing Coursework 2
 *
 * Warning dialog for unsaved changes.
 * Prevents accidental loss of user input.
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
 * Dialog to warn about unsaved changes
 *
 * @param onConfirm Callback function executed when user confirms navigation
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