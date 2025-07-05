package co.uk.bbk.culinarycompanion.ui.addrecipe

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import co.uk.bbk.culinarycompanion.data.Recipe
import co.uk.bbk.culinarycompanion.data.RecipeRepository
import kotlinx.coroutines.launch

/**
 * ViewModel for AddRecipeFragment
 * Handles creating new recipes
 */
class AddRecipeViewModel(
    private val repository: RecipeRepository
) : ViewModel() {

    /**
     * Save a new recipe to the database
     */
    fun saveRecipe(
        title: String,
        ingredients: String,
        instructions: String,
        category: String,
        imagePath: String? = null,
        protein: Double? = null,
        carbs: Double? = null,
        fat: Double? = null
    ) {
        viewModelScope.launch {
            val recipe = Recipe(
                title = title,
                ingredients = ingredients,
                instructions = instructions,
                category = category,
                imagePath = imagePath,
                protein = protein,
                carbs = carbs,
                fat = fat
            )
            repository.insertRecipe(recipe)
        }
    }
}

/**
 * Factory class for creating AddRecipeViewModel
 */
class AddRecipeViewModelFactory(
    private val repository: RecipeRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AddRecipeViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AddRecipeViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}