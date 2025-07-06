/**
 * Culinary Companion - Recipe Management App
 * BSc Computing Coursework 2
 *
 * Recipe detail view screen showing full recipe information.
 * Displays ingredients, instructions, and optional macros.
 *
 * @author Levi GIRARD
 * @studentId 13401249
 */

package co.uk.bbk.culinarycompanion.ui.recipeview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import co.uk.bbk.culinarycompanion.CulinaryCompanionApplication
import co.uk.bbk.culinarycompanion.R
import co.uk.bbk.culinarycompanion.databinding.FragmentRecipeViewBinding

/**
 * RecipeViewFragment displays the details of a selected recipe
 * Users can view ingredients, instructions, and navigate to edit
 */
class RecipeViewFragment : Fragment() {

    private var _binding: FragmentRecipeViewBinding? = null
    private val binding get() = _binding!!

    private val args: RecipeViewFragmentArgs by navArgs()

    // Initialize ViewModel
    private val viewModel: RecipeViewViewModel by viewModels {
        RecipeViewViewModelFactory(
            (requireActivity().application as CulinaryCompanionApplication).repository,
            args.recipeId
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRecipeViewBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupButtons()
        observeRecipe()
    }

    private fun setupButtons() {
        binding.btnBack.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.btnEditRecipe.setOnClickListener {
            navigateToEditRecipe()
        }
    }

    /**
     * Observe recipe data from ViewModel
     * Updates UI when recipe is loaded
     */
    private fun observeRecipe() {
        viewModel.recipe.observe(viewLifecycleOwner) { recipe ->
            recipe?.let {
                displayRecipe(it)
            }
        }
    }

    /**
     * Display recipe data in UI elements
     * @param recipe The recipe to display
     */
    private fun displayRecipe(recipe: co.uk.bbk.culinarycompanion.data.Recipe) {
        binding.tvRecipeName.text = recipe.title
        binding.tvIngredients.text = recipe.ingredients
        binding.tvRecipeInstructions.text = recipe.instructions

        // Update macros - check if the TextViews have IDs
        try {
            // If you have individual TextViews with IDs
            binding.tvProtein.text = if (recipe.protein != null) {
                getString(R.string.macro_protein_format, recipe.protein.toString())
            } else {
                "Protein: - g"
            }

            binding.tvCarbs.text = if (recipe.carbs != null) {
                getString(R.string.macro_carbs_format, recipe.carbs.toString())
            } else {
                "Carbs: - g"
            }

            binding.tvFats.text = if (recipe.fat != null) {
                getString(R.string.macro_fats_format, recipe.fat.toString())
            } else {
                "Fats: - g"
            }
        } catch (e: Exception) {
            // If the TextViews don't have IDs, the macros won't update
            // You need to add the IDs to the layout file
        }
    }

    private fun displayPlaceholderData() {
        // This will be replaced with actual data from the database
        binding.tvRecipeName.text = "Recipe_Name"
        binding.tvIngredients.text = "1) 3 Eggs\n2) 200g flour\n3) 1tsp baking powder"
        binding.tvRecipeInstructions.text = "1) crack eggs into bowl\n2) add flour\n3) add baking powder\n4) whisk until combined"

        // Macros - show only if available
        // For now, showing placeholder values
    }

    /**
     * Navigate to edit screen for current recipe
     */
    private fun navigateToEditRecipe() {
        val action = RecipeViewFragmentDirections
            .actionRecipeViewFragmentToEditRecipeFragment(args.recipeId)
        findNavController().navigate(action)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}