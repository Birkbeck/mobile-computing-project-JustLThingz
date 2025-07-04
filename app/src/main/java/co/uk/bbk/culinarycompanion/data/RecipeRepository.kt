package co.uk.bbk.culinarycompanion.data

import kotlinx.coroutines.flow.Flow

/**
 * Repository class that provides a clean API for data access to the rest of the application
 * Acts as a single source of truth for recipe data
 */
class RecipeRepository(private val recipeDao: RecipeDAO) {

    /**
     * Get all recipes from the database
     * @return Flow of list of all recipes
     */
    fun getAllRecipes(): Flow<List<Recipe>> = recipeDao.getAllRecipes()

    /**
     * Get recipes by category
     * @param category The category to filter by
     * @return Flow of list of recipes in the category
     */
    fun getRecipesByCategory(category: String): Flow<List<Recipe>> =
        recipeDao.getRecipesByCategory(category)

    /**
     * Search recipes by query
     * @param searchQuery The search term
     * @return Flow of list of matching recipes
     */
    fun searchRecipes(searchQuery: String): Flow<List<Recipe>> =
        recipeDao.searchRecipes(searchQuery)

    /**
     * Search recipes within a specific category
     * @param category The category to search within
     * @param searchQuery The search term
     * @return Flow of list of matching recipes
     */
    fun searchRecipesInCategory(category: String, searchQuery: String): Flow<List<Recipe>> =
        recipeDao.searchRecipesInCategory(category, searchQuery)

    /**
     * Get a recipe by ID
     * @param recipeId The ID of the recipe
     * @return The recipe if found, null otherwise
     */
    suspend fun getRecipeById(recipeId: Int): Recipe? =
        recipeDao.getRecipeById(recipeId)

    /**
     * Insert a new recipe
     * @param recipe The recipe to insert
     * @return The ID of the inserted recipe
     */
    suspend fun insertRecipe(recipe: Recipe): Long =
        recipeDao.insertRecipe(recipe)

    /**
     * Update an existing recipe
     * @param recipe The recipe with updated values
     */
    suspend fun updateRecipe(recipe: Recipe) =
        recipeDao.updateRecipe(recipe)

    /**
     * Delete a recipe
     * @param recipe The recipe to delete
     */
    suspend fun deleteRecipe(recipe: Recipe) =
        recipeDao.deleteRecipe(recipe)

    /**
     * Get count of recipes in a category
     * @param category The category to count
     * @return The number of recipes in the category
     */
    suspend fun getRecipeCountByCategory(category: String): Int =
        recipeDao.getRecipeCountByCategory(category)
}