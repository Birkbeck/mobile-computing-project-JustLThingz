package co.uk.bbk.culinarycompanion.ui.editrecipe

import androidx.lifecycle.*
import co.uk.bbk.culinarycompanion.data.Recipe
import co.uk.bbk.culinarycompanion.data.RecipeRepository
import kotlinx.coroutines.launch

/**
 * ViewModel for EditRecipeFragment
 * Handles loading and updating existing recipes
 */
class EditRecipeViewModel(
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

    /**
     * Update an existing recipe
     */
    fun updateRecipe(
        title: String,
        ingredients: String,
        instructions: String,
        imagePath: String? = null,
        protein: Double? = null,
        carbs: Double? = null,
        fat: Double? = null
    ) {
        viewModelScope.launch {
            _recipe.value?.let { currentRecipe ->
                val updatedRecipe = currentRecipe.copy(
                    title = title,
                    ingredients = ingredients,
                    instructions = instructions,
                    imagePath = imagePath ?: currentRecipe.imagePath,
                    protein = protein,
                    carbs = carbs,
                    fat = fat,
                    updatedAt = System.currentTimeMillis()
                )
                repository.updateRecipe(updatedRecipe)
            }
        }
    }
}

/**
 * Factory class for creating EditRecipeViewModel with parameters
 */
class EditRecipeViewModelFactory(
    private val repository: RecipeRepository,
    private val recipeId: Int
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EditRecipeViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return EditRecipeViewModel(repository, recipeId) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}