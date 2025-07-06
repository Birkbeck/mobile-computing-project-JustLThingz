/**
 * Culinary Companion - Recipe Management App
 * BSc Computing Coursework 2
 *
 * Image source selection dialog.
 * Placeholder for future camera/gallery implementation.
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
 * Dialog for choosing image source
 *
 * @param onTakePhoto Callback for camera option selection
 * @param onChooseFromGallery Callback for gallery option selection
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