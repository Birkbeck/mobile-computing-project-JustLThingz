package co.uk.bbk.culinarycompanion.ui.recipelist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import co.uk.bbk.culinarycompanion.data.Recipe
import co.uk.bbk.culinarycompanion.databinding.FragmentRecipeListBinding

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

        // For now, show empty state
        showEmptyState()
    }

    private fun setupUI() {
        // Set category name
        binding.tvCategoryName.text = args.categoryName

        // Update search hint
        binding.searchRecipes.queryHint = "Search ${args.categoryName} Recipes"
    }

    private fun setupRecyclerView() {
        recipeAdapter = RecipeAdapter(
            onRecipeClick = { recipe ->
                if (!isDeleteMode) {
                    navigateToRecipeView(recipe.id)
                }
            },
            onDeleteClick = { recipe ->
                // Will implement delete functionality with ViewModel
            }
        )

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = recipeAdapter
        }
    }

    private fun setupSearchView() {
        binding.searchRecipes.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                // Will implement search with ViewModel
                return true
            }
        })
    }

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

    private fun toggleDeleteMode() {
        isDeleteMode = !isDeleteMode
        recipeAdapter.setDeleteMode(isDeleteMode)

        if (isDeleteMode) {
            binding.btnDeleteRecipe.text = "Finish Delete"
        } else {
            binding.btnDeleteRecipe.text = "Delete"
        }
    }

    private fun showEmptyState() {
        binding.recyclerView.visibility = View.GONE
        binding.layoutEmptyState.visibility = View.VISIBLE

        // Update empty state message with category name
        binding.tvEmptyMessage.text = "Tap the + button below to add your first\n${args.categoryName} recipe!"
    }

    private fun showRecipeList() {
        binding.recyclerView.visibility = View.VISIBLE
        binding.layoutEmptyState.visibility = View.GONE
    }

    private fun navigateToRecipeView(recipeId: Int) {
        val action = RecipeListFragmentDirections
            .actionRecipeListFragmentToRecipeViewFragment(recipeId)
        findNavController().navigate(action)
    }

    private fun navigateToAddRecipe() {
        val action = RecipeListFragmentDirections
            .actionRecipeListFragmentToAddRecipeFragment(args.categoryName)
        findNavController().navigate(action)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}