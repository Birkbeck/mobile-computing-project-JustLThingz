package co.uk.bbk.culinarycompanion.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object for Recipe operations
 * Provides methods to perform CRUD operations on the recipes table
 */
@Dao
interface RecipeDAO {

    /**
     * Insert a new recipe into the database
     * @param recipe The recipe to insert
     * @return The ID of the inserted recipe
     */
    @Insert
    suspend fun insertRecipe(recipe: Recipe): Long

    /**
     * Update an existing recipe
     * @param recipe The recipe with updated values
     */
    @Update
    suspend fun updateRecipe(recipe: Recipe)

    /**
     * Delete a recipe from the database
     * @param recipe The recipe to delete
     */
    @Delete
    suspend fun deleteRecipe(recipe: Recipe)

    /**
     * Get a recipe by its ID
     * @param recipeId The ID of the recipe
     * @return The recipe if found, null otherwise
     */
    @Query("SELECT * FROM recipes WHERE id = :recipeId")
    suspend fun getRecipeById(recipeId: Int): Recipe?

    /**
     * Get all recipes for a specific category
     * @param category The category to filter by
     * @return Flow of list of recipes in the category
     */
    @Query("SELECT * FROM recipes WHERE category = :category ORDER BY title ASC")
    fun getRecipesByCategory(category: String): Flow<List<Recipe>>

    /**
     * Get all recipes
     * @return Flow of list of all recipes
     */
    @Query("SELECT * FROM recipes ORDER BY category, title ASC")
    fun getAllRecipes(): Flow<List<Recipe>>

    /**
     * Search recipes by title or ingredients
     * @param searchQuery The search term
     * @return Flow of list of matching recipes
     */
    @Query("""
        SELECT * FROM recipes 
        WHERE title LIKE '%' || :searchQuery || '%' 
        OR ingredients LIKE '%' || :searchQuery || '%'
        ORDER BY title ASC
    """)
    fun searchRecipes(searchQuery: String): Flow<List<Recipe>>

    /**
     * Search recipes within a specific category
     * @param category The category to search within
     * @param searchQuery The search term
     * @return Flow of list of matching recipes
     */
    @Query("""
        SELECT * FROM recipes 
        WHERE category = :category 
        AND (title LIKE '%' || :searchQuery || '%' 
        OR ingredients LIKE '%' || :searchQuery || '%')
        ORDER BY title ASC
    """)
    fun searchRecipesInCategory(category: String, searchQuery: String): Flow<List<Recipe>>

    /**
     * Get count of recipes in a category
     * @param category The category to count
     * @return The number of recipes in the category
     */
    @Query("SELECT COUNT(*) FROM recipes WHERE category = :category")
    suspend fun getRecipeCountByCategory(category: String): Int
}