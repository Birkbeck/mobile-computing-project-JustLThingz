/**
 * Culinary Companion - Recipe Management App
 * BSc Computing Coursework 2
 *
 * ViewModel for RecipeListFragment managing recipe list data.
 * Handles search functionality and delete operations.
 *
 * @author Levi GIRARD
 * @studentId 13401249
 */

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
 *
 * @param repository The repository for data operations
 * @param category The category to filter recipes
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
     * Filters recipes based on title and ingredients
     * @param query The search term
     */
    fun searchRecipes(query: String) {
        searchQuery.value = query
    }

    /**
     * Delete a recipe
     * Removes recipe from database asynchronously
     * @param recipe The recipe to delete
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