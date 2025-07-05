package co.uk.bbk.culinarycompanion.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import co.uk.bbk.culinarycompanion.R
import co.uk.bbk.culinarycompanion.data.Category
import co.uk.bbk.culinarycompanion.databinding.FragmentHomeBinding

/**
 * HomeFragment displays the main menu with recipe categories
 * Users can select a category to view recipes
 */
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupCategoryButtons()
    }

    /**
     * Set up click listeners for category buttons
     */
    private fun setupCategoryButtons() {
        binding.btnBreakfast.setOnClickListener {
            navigateToRecipeList(Category.BREAKFAST.displayName)
        }

        binding.btnBrunch.setOnClickListener {
            navigateToRecipeList(Category.BRUNCH.displayName)
        }

        binding.btnLunch.setOnClickListener {
            navigateToRecipeList(Category.LUNCH.displayName)
        }

        binding.btnDinner.setOnClickListener {
            navigateToRecipeList(Category.DINNER.displayName)
        }

        binding.btnDessert.setOnClickListener {
            navigateToRecipeList(Category.DESSERTS.displayName)
        }

        binding.btnSnacksDrinksOther.setOnClickListener {
            navigateToRecipeList(Category.OTHER.displayName)
        }
    }

    /**
     * Navigate to RecipeListFragment with the selected category
     */
    private fun navigateToRecipeList(categoryName: String) {
        val action = HomeFragmentDirections
            .actionHomeFragmentToRecipeListFragment(categoryName)
        findNavController().navigate(action)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}