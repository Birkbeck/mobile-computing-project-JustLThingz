package co.uk.bbk.culinarycompanion.ui.recipeview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import co.uk.bbk.culinarycompanion.R
import co.uk.bbk.culinarycompanion.CulinaryCompanionApplication
import co.uk.bbk.culinarycompanion.databinding.FragmentRecipeViewBinding

/**
 * RecipeViewFragment displays the details of a selected recipe
 * Users can view ingredients, instructions, and navigate to edit
 */
class RecipeViewFragment : Fragment() {

    private var _binding: FragmentRecipeViewBinding? = null
    private val binding get() = _binding!!

    private val args: RecipeViewFragmentArgs by navArgs()

    // Initialise ViewModel
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

    private fun observeRecipe() {
        viewModel.recipe.observe(viewLifecycleOwner) { recipe ->
            recipe?.let {
                displayRecipe(it)
            }
        }
    }

    private fun displayRecipe(recipe: co.uk.bbk.culinarycompanion.data.Recipe) {
        binding.tvRecipeName.text = recipe.title
        binding.tvIngredients.text = recipe.ingredients
        binding.tvRecipeInstructions.text = recipe.instructions

        // Find the TextViews for macros (you'll need to add IDs to them in the layout)
        // For now, let's update the entire macros section
        val macrosText = buildString {
            recipe.protein?.let { append(getString(R.string.macro_protein_format, it.toString())) }
            recipe.carbs?.let {
                if (isNotEmpty()) append("\n")
                append(getString(R.string.macro_carbs_format, it.toString()))
            }
            recipe.fat?.let {
                if (isNotEmpty()) append("\n")
                append(getString(R.string.macro_fats_format, it.toString()))
            }
        }

        // If you have a single TextView for all macros, use:
        // binding.tvMacros.text = macrosText

        // Or hide the macros section if no values
        if (macrosText.isEmpty()) {
            binding.layoutMacros.visibility = View.GONE
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