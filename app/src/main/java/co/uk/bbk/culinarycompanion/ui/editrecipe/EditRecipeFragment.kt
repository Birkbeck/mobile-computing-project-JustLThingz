package co.uk.bbk.culinarycompanion.ui.editrecipe

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import co.uk.bbk.culinarycompanion.CulinaryCompanionApplication
import co.uk.bbk.culinarycompanion.databinding.FragmentEditRecipeBinding
import co.uk.bbk.culinarycompanion.ui.dialog.UnsavedChangesDialog
import co.uk.bbk.culinarycompanion.ui.dialog.ImageUploadDialog

/**
 * EditRecipeFragment allows users to modify existing recipes
 * Pre-populates fields with existing recipe data
 */
class EditRecipeFragment : Fragment() {

    private var _binding: FragmentEditRecipeBinding? = null
    private val binding get() = _binding!!

    private val args: EditRecipeFragmentArgs by navArgs()

    // Initialize ViewModel
    private val viewModel: EditRecipeViewModel by viewModels {
        EditRecipeViewModelFactory(
            (requireActivity().application as CulinaryCompanionApplication).repository,
            args.recipeId
        )
    }

    private var originalRecipe: co.uk.bbk.culinarycompanion.data.Recipe? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEditRecipeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupButtons()
        setupImagePicker()
        observeRecipe()
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

    private fun observeRecipe() {
        viewModel.recipe.observe(viewLifecycleOwner) { recipe ->
            recipe?.let {
                originalRecipe = it
                loadRecipeData(it)
            }
        }
    }

    private fun loadRecipeData(recipe: co.uk.bbk.culinarycompanion.data.Recipe) {
        binding.etRecipeName.setText(recipe.title)
        binding.etIngredients.setText(recipe.ingredients)
        binding.etRecipeInstructions.setText(recipe.instructions)

        // Load macros if available
        recipe.protein?.let { binding.etProtein.setText(it.toString()) }
        recipe.carbs?.let { binding.etCarbs.setText(it.toString()) }
        recipe.fat?.let { binding.etFats.setText(it.toString()) }
    }

    private fun loadPlaceholderData() {
        // This will be replaced with actual data from the database
        binding.etRecipeName.setText("Recipe_Name...")
        binding.etIngredients.setText("1) 3 Eggs\n2) 200g flour\n3) 1tsp baking powder\nTap to Edit...")
        binding.etRecipeInstructions.setText("1) crack eggs into bowl\n2) add flour\n3) add baking powder\n4) whisk until combined\nTap to Edit...")
    }

    private fun hasUnsavedChanges(): Boolean {
        originalRecipe?.let { original ->
            return binding.etRecipeName.text.toString() != original.title ||
                    binding.etIngredients.text.toString() != original.ingredients ||
                    binding.etRecipeInstructions.text.toString() != original.instructions ||
                    binding.etProtein.text.toString() != (original.protein?.toString() ?: "") ||
                    binding.etCarbs.text.toString() != (original.carbs?.toString() ?: "") ||
                    binding.etFats.text.toString() != (original.fat?.toString() ?: "")
        }
        return false
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
                Toast.makeText(context, "Camera feature coming soon", Toast.LENGTH_SHORT).show()
            },
            onChooseFromGallery = {
                // TODO: Implement gallery selection
                Toast.makeText(context, "Gallery feature coming soon", Toast.LENGTH_SHORT).show()
            }
        ).show(childFragmentManager, "image_upload")
    }

    private fun saveRecipe() {
        // Validate input
        val recipeName = binding.etRecipeName.text.toString().trim()
        val ingredients = binding.etIngredients.text.toString().trim()
        val instructions = binding.etRecipeInstructions.text.toString().trim()

        if (recipeName.isEmpty()) {
            binding.etRecipeName.error = "Recipe name is required"
            return
        }

        if (ingredients.isEmpty()) {
            binding.etIngredients.error = "Ingredients are required"
            return
        }

        if (instructions.isEmpty()) {
            binding.etRecipeInstructions.error = "Instructions are required"
            return
        }

        // Get optional macro values
        val protein = binding.etProtein.text.toString().toDoubleOrNull()
        val carbs = binding.etCarbs.text.toString().toDoubleOrNull()
        val fat = binding.etFats.text.toString().toDoubleOrNull()

        // Update in database via ViewModel
        viewModel.updateRecipe(
            title = recipeName,
            ingredients = ingredients,
            instructions = instructions,
            protein = protein,
            carbs = carbs,
            fat = fat
        )

        Toast.makeText(context, "Recipe updated!", Toast.LENGTH_SHORT).show()
        findNavController().navigateUp()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}