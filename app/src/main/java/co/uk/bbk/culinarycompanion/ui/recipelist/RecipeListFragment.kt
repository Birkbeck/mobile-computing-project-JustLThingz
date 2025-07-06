/**
 * Culinary Companion - Recipe Management App
 * BSc Computing Coursework 2
 *
 * Recipe list screen showing all recipes for a selected category.
 * Supports search, add, view, and delete operations.
 *
 * @author Levi GIRARD
 * @studentId 13401249
 */

package co.uk.bbk.culinarycompanion.ui.recipelist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import co.uk.bbk.culinarycompanion.R
import co.uk.bbk.culinarycompanion.CulinaryCompanionApplication
import co.uk.bbk.culinarycompanion.data.Recipe
import co.uk.bbk.culinarycompanion.databinding.FragmentRecipeListBinding
import co.uk.bbk.culinarycompanion.ui.dialog.DeleteConfirmationDialog

/**
 * RecipeListFragment displays a list of recipes for a specific category
 * Users can search, add new recipes, or enter delete mode
 */
class RecipeListFragment : Fragment() {

    private var _binding: FragmentRecipeListBinding? = null
    private val binding get() = _binding!!

    private val args: RecipeListFragmentArgs by navArgs()
    private lateinit var recipeAdapter: RecipeAdapter
    private var isDeleteMode = false

    // Initialize ViewModel with factory
    private val viewModel: RecipeListViewModel by viewModels {
        RecipeListViewModelFactory(
            (requireActivity().application as CulinaryCompanionApplication).repository,
            args.categoryName
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRecipeListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupUI()
        setupRecyclerView()
        setupSearchView()
        setupButtons()
        observeRecipes()
    }

    /**
     * Configure UI elements with category-specific information
     */
    private fun setupUI() {
        // Set category name
        binding.tvCategoryName.text = args.categoryName

        // Update search hint
        binding.searchRecipes.queryHint = getString(R.string.hint_search_recipes, args.categoryName)
    }

    /**
     * Initialise RecyclerView with adapter and layout manager
     */
    private fun setupRecyclerView() {
        recipeAdapter = RecipeAdapter(
            onRecipeClick = { recipe ->
                if (!isDeleteMode) {
                    navigateToRecipeView(recipe.id)
                }
            },
            onDeleteClick = { recipe ->
                showDeleteConfirmation(recipe)
            }
        )

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = recipeAdapter
        }
    }

    /**
     * Configure search functionality with text change listener
     */
    private fun setupSearchView() {
        binding.searchRecipes.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                viewModel.searchRecipes(newText ?: "")
                return true
            }
        })
    }

    /**
     * Set up click listeners for navigation and mode buttons
     */
    private fun setupButtons() {
        binding.btnHome.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.btnDeleteRecipe.setOnClickListener {
            toggleDeleteMode()
        }

        binding.fabAddRecipe.setOnClickListener {
            navigateToAddRecipe()
        }
    }

    /**
     * Toggle between normal and delete mode
     * Updates UI and adapter state accordingly
     */
    private fun toggleDeleteMode() {
        isDeleteMode = !isDeleteMode
        recipeAdapter.setDeleteMode(isDeleteMode)

        if (isDeleteMode) {
            binding.btnDeleteRecipe.text = getString(R.string.btn_finish_delete)
        } else {
            binding.btnDeleteRecipe.text = getString(R.string.btn_delete)
        }
    }

    /**
     * Observe recipe list changes and update UI
     * Shows empty state when no recipes exist
     */
    private fun observeRecipes() {
        viewModel.recipes.observe(viewLifecycleOwner) { recipes ->
            if (recipes.isEmpty()) {
                showEmptyState()
            } else {
                showRecipeList()
                recipeAdapter.submitList(recipes)
            }
        }
    }

    /**
     * Display empty state with instructions to add first recipe
     */
    private fun showEmptyState() {
        binding.recyclerView.visibility = View.GONE
        binding.layoutEmptyState.visibility = View.VISIBLE

        // Update empty state message with category name
        binding.tvEmptyMessage.text = getString(R.string.empty_recipes_message, args.categoryName)
    }

    /**
     * Display recipe list and hide empty state
     */
    private fun showRecipeList() {
        binding.recyclerView.visibility = View.VISIBLE
        binding.layoutEmptyState.visibility = View.GONE
    }

    /**
     * Navigate to recipe detail view
     * @param recipeId The ID of the recipe to display
     */
    private fun navigateToRecipeView(recipeId: Int) {
        val action = RecipeListFragmentDirections
            .actionRecipeListFragmentToRecipeViewFragment(recipeId)
        findNavController().navigate(action)
    }

    /**
     * Navigate to add recipe screen
     * Passes current category as argument
     */
    private fun navigateToAddRecipe() {
        val action = RecipeListFragmentDirections
            .actionRecipeListFragmentToAddRecipeFragment(args.categoryName)
        findNavController().navigate(action)
    }

    /**
     * Show delete confirmation dialog
     * @param recipe The recipe to be deleted
     */
    private fun showDeleteConfirmation(recipe: Recipe) {
        DeleteConfirmationDialog(recipe.title) {
            viewModel.deleteRecipe(recipe)
        }.show(childFragmentManager, "delete_confirmation")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}