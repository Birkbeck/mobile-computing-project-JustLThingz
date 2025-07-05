package co.uk.bbk.culinarycompanion.ui.recipeview

import androidx.lifecycle.*
import co.uk.bbk.culinarycompanion.data.Recipe
import co.uk.bbk.culinarycompanion.data.RecipeRepository
import kotlinx.coroutines.launch

/**
 * ViewModel for RecipeViewFragment
 * Loads and manages a single recipe
 */
class RecipeViewViewModel(
    private val repository: RecipeRepository,
    private val recipeId: Int
) : ViewModel() {

    private val _recipe = MutableLiveData<Recipe?>()
    val recipe: LiveData<Recipe?> = _recipe

    init {
        loadRecipe()
    }

    /**
     * Load recipe from database
     */
    private fun loadRecipe() {
        viewModelScope.launch {
            _recipe.value = repository.getRecipeById(recipeId)
        }
    }
}

/**
 * Factory class for creating RecipeViewViewModel with parameters
 */
class RecipeViewViewModelFactory(
    private val repository: RecipeRepository,
    private val recipeId: Int
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RecipeViewViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return RecipeViewViewModel(repository, recipeId) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}