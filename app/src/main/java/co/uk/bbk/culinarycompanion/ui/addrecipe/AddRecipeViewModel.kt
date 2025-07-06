/**
 * Culinary Companion - Recipe Management App
 * BSc Computing Coursework 2
 *
 * ViewModel for AddRecipeFragment handling new recipe creation.
 * Manages recipe saving to the database.
 *
 * @author Levi GIRARD
 * @studentId 13401249
 */

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
 *
 * @param repository The repository for data operations
 */
class AddRecipeViewModel(
    private val repository: RecipeRepository
) : ViewModel() {

    /**
     * Save a new recipe to the database
     * Creates recipe entity and persists asynchronously
     *
     * @param title Recipe name
     * @param ingredients Newline-separated list of ingredients
     * @param instructions Newline-separated cooking steps
     * @param category Recipe category
     * @param imagePath Optional path to recipe image
     * @param protein Optional protein content in grams
     * @param carbs Optional carbohydrate content in grams
     * @param fat Optional fat content in grams
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