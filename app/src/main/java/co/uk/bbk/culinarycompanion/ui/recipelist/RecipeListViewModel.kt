package co.uk.bbk.culinarycompanion.ui.recipelist

import androidx.lifecycle.*
import co.uk.bbk.culinarycompanion.data.Recipe
import co.uk.bbk.culinarycompanion.data.RecipeRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

/**
 * ViewModel for RecipeListFragment
 * Manages recipe data for a specific category
 */
class RecipeListViewModel(
    private val repository: RecipeRepository,
    private val category: String
) : ViewModel() {

    // Search query state
    private val searchQuery = MutableStateFlow("")

    // Combine category recipes with search query
    val recipes: LiveData<List<Recipe>> = searchQuery.combine(
        repository.getRecipesByCategory(category)
    ) { query, recipeList ->
        if (query.isEmpty()) {
            recipeList
        } else {
            recipeList.filter { recipe ->
                recipe.title.contains(query, ignoreCase = true) ||
                        recipe.ingredients.contains(query, ignoreCase = true)
            }
        }
    }.asLiveData()

    /**
     * Update search query
     */
    fun searchRecipes(query: String) {
        searchQuery.value = query
    }

    /**
     * Delete a recipe
     */
    fun deleteRecipe(recipe: Recipe) {
        viewModelScope.launch {
            repository.deleteRecipe(recipe)
        }
    }
}

/**
 * Factory class for creating RecipeListViewModel with parameters
 */
class RecipeListViewModelFactory(
    private val repository: RecipeRepository,
    private val category: String
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RecipeListViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return RecipeListViewModel(repository, category) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}