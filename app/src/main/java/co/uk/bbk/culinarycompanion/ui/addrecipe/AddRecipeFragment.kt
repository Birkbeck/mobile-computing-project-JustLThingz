package co.uk.bbk.culinarycompanion.ui.addrecipe

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import co.uk.bbk.culinarycompanion.R
import co.uk.bbk.culinarycompanion.CulinaryCompanionApplication
import co.uk.bbk.culinarycompanion.databinding.FragmentAddRecipeBinding
import co.uk.bbk.culinarycompanion.ui.dialog.UnsavedChangesDialog
import co.uk.bbk.culinarycompanion.ui.dialog.ImageUploadDialog

/**
 * AddRecipeFragment allows users to create new recipes
 * Handles input validation and saving to database
 */
class AddRecipeFragment : Fragment() {

    private var _binding: FragmentAddRecipeBinding? = null
    private val binding get() = _binding!!

    private val args: AddRecipeFragmentArgs by navArgs()

    // Initialize ViewModel
    private val viewModel: AddRecipeViewModel by viewModels {
        AddRecipeViewModelFactory(
            (requireActivity().application as CulinaryCompanionApplication).repository
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddRecipeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupButtons()
        setupImagePicker()
    }

    private fun setupButtons() {
        binding.btnBack.setOnClickListener {
            // Check for unsaved changes before navigating back
            if (hasUnsavedChanges()) {
                showUnsavedChangesDialog()
            } else {
                findNavController().navigateUp()
            }
        }

        binding.btnSaveRecipe.setOnClickListener {
            saveRecipe()
        }
    }

    private fun setupImagePicker() {
        binding.ivDishImage.setOnClickListener {
            // Will show image upload dialog
            showImageUploadDialog()
        }
    }

    private fun hasUnsavedChanges(): Boolean {
        return binding.etAddRecipeName.text.isNotEmpty() ||
                binding.etIngredients.text.isNotEmpty() ||
                binding.etRecipeInstructions.text.isNotEmpty() ||
                binding.etProtein.text.isNotEmpty() ||
                binding.etCarbs.text.isNotEmpty() ||
                binding.etFats.text.isNotEmpty()
    }

    private fun showUnsavedChangesDialog() {
        UnsavedChangesDialog {
            findNavController().navigateUp()
        }.show(childFragmentManager, "unsaved_changes")
    }

    private fun showImageUploadDialog() {
        ImageUploadDialog(
            onTakePhoto = {
                // TODO: Implement camera functionality
                Toast.makeText(context, getString(R.string.msg_camera_coming_soon), Toast.LENGTH_SHORT).show()
            },
            onChooseFromGallery = {
                // TODO: Implement gallery selection
                Toast.makeText(context, getString(R.string.msg_gallery_coming_soon), Toast.LENGTH_SHORT).show()
            }
        ).show(childFragmentManager, "image_upload")
    }

    private fun saveRecipe() {
        // Validate input
        val recipeName = binding.etAddRecipeName.text.toString().trim()
        val ingredients = binding.etIngredients.text.toString().trim()
        val instructions = binding.etRecipeInstructions.text.toString().trim()

        if (recipeName.isEmpty()) {
            binding.etAddRecipeName.error = getString(R.string.error_recipe_name_required)
            return
        }

        if (ingredients.isEmpty()) {
            binding.etIngredients.error = getString(R.string.error_ingredients_required)
            return
        }

        if (instructions.isEmpty()) {
            binding.etRecipeInstructions.error = getString(R.string.error_instructions_required)
            return
        }

        // Get optional macro values
        val protein = binding.etProtein.text.toString().toDoubleOrNull()
        val carbs = binding.etCarbs.text.toString().toDoubleOrNull()
        val fat = binding.etFats.text.toString().toDoubleOrNull()

        // Save to database via ViewModel
        viewModel.saveRecipe(
            title = recipeName,
            ingredients = ingredients,
            instructions = instructions,
            category = args.categoryName,
            protein = protein,
            carbs = carbs,
            fat = fat
        )

        Toast.makeText(context, getString(R.string.msg_recipe_saved), Toast.LENGTH_SHORT).show()
        findNavController().navigateUp()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}