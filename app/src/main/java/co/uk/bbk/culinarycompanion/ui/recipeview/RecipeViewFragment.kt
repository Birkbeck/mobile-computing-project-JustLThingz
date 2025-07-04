package co.uk.bbk.culinarycompanion.ui.recipeview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import co.uk.bbk.culinarycompanion.databinding.FragmentRecipeViewBinding

/**
 * RecipeViewFragment displays the details of a selected recipe
 * Users can view ingredients, instructions, and navigate to edit
 */
class RecipeViewFragment : Fragment() {

    private var _binding: FragmentRecipeViewBinding? = null
    private val binding get() = _binding!!

    private val args: RecipeViewFragmentArgs by navArgs()

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

        // TODO: Load recipe data from ViewModel
        // For now, show placeholder data
        displayPlaceholderData()
    }

    private fun setupButtons() {
        binding.btnBack.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.btnEditRecipe.setOnClickListener {
            navigateToEditRecipe()
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