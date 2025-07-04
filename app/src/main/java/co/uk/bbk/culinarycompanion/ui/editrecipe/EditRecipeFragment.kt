package co.uk.bbk.culinarycompanion.ui.editrecipe

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import co.uk.bbk.culinarycompanion.databinding.FragmentEditRecipeBinding

/**
 * EditRecipeFragment allows users to modify existing recipes
 * Pre-populates fields with existing recipe data
 */
class EditRecipeFragment : Fragment() {

    private var _binding: FragmentEditRecipeBinding? = null
    private val binding get() = _binding!!

    private val args: EditRecipeFragmentArgs by navArgs()

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

        // TODO: Load recipe data from ViewModel
        // For now, show placeholder data
        loadPlaceholderData()
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

    private fun loadPlaceholderData() {
        // This will be replaced with actual data from the database
        binding.etRecipeName.setText("Recipe_Name...")
        binding.etIngredients.setText("1) 3 Eggs\n2) 200g flour\n3) 1tsp baking powder\nTap to Edit...")
        binding.etRecipeInstructions.setText("1) crack eggs into bowl\n2) add flour\n3) add baking powder\n4) whisk until combined\nTap to Edit...")
    }

    private fun hasUnsavedChanges(): Boolean {
        // TODO: Compare with original values
        // For now, return false
        return false
    }

    private fun showUnsavedChangesDialog() {
        // TODO: Implement dialog
        // For now, just navigate back
        findNavController().navigateUp()
    }

    private fun showImageUploadDialog() {
        // TODO: Implement image upload dialog
        Toast.makeText(context, "Image upload coming soon", Toast.LENGTH_SHORT).show()
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

        // TODO: Update in database via ViewModel
        Toast.makeText(context, "Recipe updated!", Toast.LENGTH_SHORT).show()
        findNavController().navigateUp()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}