package co.uk.bbk.culinarycompanion.data

import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import org.junit.Assert.*

/**
 * Unit tests for RecipeRepository
 * Tests repository methods using mocked DAO
 */
class RecipeRepositoryTest {

    @Mock
    private lateinit var mockDao: RecipeDAO

    private lateinit var repository: RecipeRepository

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        repository = RecipeRepository(mockDao)
    }

    /**
     * Test inserting a recipe calls DAO method
     */
    @Test
    fun insertRecipe_callsDAOInsert() = runBlocking {
        // Given
        val recipe = Recipe(
            title = "Test Recipe",
            ingredients = "Test ingredients",
            instructions = "Test instructions",
            category = "Lunch"
        )
        `when`(mockDao.insertRecipe(recipe)).thenReturn(1L)

        // When
        val result = repository.insertRecipe(recipe)

        // Then
        verify(mockDao).insertRecipe(recipe)
        assertEquals(1L, result)
    }

    /**
     * Test getting recipes by category
     */
    @Test
    fun getRecipesByCategory_returnsFlowFromDAO() {
        // Given
        val category = "Breakfast"
        val recipes = listOf(
            Recipe(id = 1, title = "Pancakes", ingredients = "Flour", instructions = "Cook", category = category),
            Recipe(id = 2, title = "Eggs", ingredients = "Eggs", instructions = "Fry", category = category)
        )
        `when`(mockDao.getRecipesByCategory(category)).thenReturn(flowOf(recipes))

        // When
        val result = repository.getRecipesByCategory(category)

        // Then
        verify(mockDao).getRecipesByCategory(category)
        assertNotNull(result)
    }

    /**
     * Test deleting a recipe
     */
    @Test
    fun deleteRecipe_callsDAODelete() = runBlocking {
        // Given
        val recipe = Recipe(
            id = 1,
            title = "To Delete",
            ingredients = "Test",
            instructions = "Test",
            category = "Other"
        )

        // When
        repository.deleteRecipe(recipe)

        // Then
        verify(mockDao).deleteRecipe(recipe)
    }

    /**
     * Test getting recipe count by category
     */
    @Test
    fun getRecipeCountByCategory_returnsCountFromDAO() = runBlocking {
        // Given
        val category = "Dinner"
        `when`(mockDao.getRecipeCountByCategory(category)).thenReturn(5)

        // When
        val result = repository.getRecipeCountByCategory(category)

        // Then
        verify(mockDao).getRecipeCountByCategory(category)
        assertEquals(5, result)
    }
}