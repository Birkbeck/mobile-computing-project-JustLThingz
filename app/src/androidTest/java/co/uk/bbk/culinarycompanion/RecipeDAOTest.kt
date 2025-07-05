package co.uk.bbk.culinarycompanion

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import co.uk.bbk.culinarycompanion.data.Recipe
import co.uk.bbk.culinarycompanion.data.RecipeDAO
import co.uk.bbk.culinarycompanion.data.RecipeDatabase
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.Assert.*

/**
 * Unit tests for RecipeDAO
 * Tests database operations including insert, update, delete, and queries
 */
@RunWith(AndroidJUnit4::class)
@SmallTest
class RecipeDAOTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var database: RecipeDatabase
    private lateinit var dao: RecipeDAO

    @Before
    fun setup() {
        // Create an in-memory database for testing
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            RecipeDatabase::class.java
        ).allowMainThreadQueries().build()

        dao = database.recipeDao()
    }

    @After
    fun teardown() {
        database.close()
    }

    /**
     * Test inserting a recipe and retrieving it by ID
     */
    @Test
    fun insertRecipe_and_getRecipeById() = runBlocking {
        // Given
        val recipe = Recipe(
            title = "Test Recipe",
            ingredients = "Test ingredients",
            instructions = "Test instructions",
            category = "Breakfast"
        )

        // When
        val id = dao.insertRecipe(recipe)
        val loaded = dao.getRecipeById(id.toInt())

        // Then
        assertNotNull(loaded)
        assertEquals("Test Recipe", loaded?.title)
        assertEquals("Test ingredients", loaded?.ingredients)
        assertEquals("Test instructions", loaded?.instructions)
        assertEquals("Breakfast", loaded?.category)
    }

    /**
     * Test getting recipes by category
     */
    @Test
    fun getRecipesByCategory_returnsCorrectRecipes() = runBlocking {
        // Given
        val breakfastRecipe1 = Recipe(
            title = "Pancakes",
            ingredients = "Flour, eggs, milk",
            instructions = "Mix and cook",
            category = "Breakfast"
        )
        val breakfastRecipe2 = Recipe(
            title = "Omelette",
            ingredients = "Eggs, cheese",
            instructions = "Beat eggs and cook",
            category = "Breakfast"
        )
        val lunchRecipe = Recipe(
            title = "Sandwich",
            ingredients = "Bread, ham, cheese",
            instructions = "Assemble sandwich",
            category = "Lunch"
        )

        dao.insertRecipe(breakfastRecipe1)
        dao.insertRecipe(breakfastRecipe2)
        dao.insertRecipe(lunchRecipe)

        // When
        val breakfastRecipes = dao.getRecipesByCategory("Breakfast").first()

        // Then
        assertEquals(2, breakfastRecipes.size)
        assertTrue(breakfastRecipes.any { it.title == "Pancakes" })
        assertTrue(breakfastRecipes.any { it.title == "Omelette" })
    }

    /**
     * Test updating a recipe
     */
    @Test
    fun updateRecipe_updatesCorrectly() = runBlocking {
        // Given
        val recipe = Recipe(
            title = "Original Title",
            ingredients = "Original ingredients",
            instructions = "Original instructions",
            category = "Dinner"
        )
        val id = dao.insertRecipe(recipe)

        // When
        val updatedRecipe = recipe.copy(
            id = id.toInt(),
            title = "Updated Title",
            ingredients = "Updated ingredients"
        )
        dao.updateRecipe(updatedRecipe)

        val loaded = dao.getRecipeById(id.toInt())

        // Then
        assertEquals("Updated Title", loaded?.title)
        assertEquals("Updated ingredients", loaded?.ingredients)
        assertEquals("Original instructions", loaded?.instructions)
    }

    /**
     * Test deleting a recipe
     */
    @Test
    fun deleteRecipe_removesFromDatabase() = runBlocking {
        // Given
        val recipe = Recipe(
            title = "To Delete",
            ingredients = "Test",
            instructions = "Test",
            category = "Other"
        )
        val id = dao.insertRecipe(recipe)
        val recipeToDelete = recipe.copy(id = id.toInt())

        // When
        dao.deleteRecipe(recipeToDelete)
        val loaded = dao.getRecipeById(id.toInt())

        // Then
        assertNull(loaded)
    }
}