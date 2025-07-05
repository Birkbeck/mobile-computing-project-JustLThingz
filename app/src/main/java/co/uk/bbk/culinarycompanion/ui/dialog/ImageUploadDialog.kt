package co.uk.bbk.culinarycompanion.ui.dialog

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment

/**
 * Dialog for choosing image source
 */
class ImageUploadDialog(
    private val onTakePhoto: () -> Unit,
    private val onChooseFromGallery: () -> Unit
) : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(requireContext())
            .setTitle(getString(R.string.dialog_add_photo_title))
            .setItems(arrayOf(getString(R.string.btn_take_photo), getString(R.string.btn_choose_gallery))) { _, which ->
                when (which) {
                    0 -> onTakePhoto()
                    1 -> onChooseFromGallery()
                }
            }
            .setNegativeButton(getString(R.string.btn_cancel)) { dialog, _ ->
                dialog.dismiss()
            }
            .create()
    }
}