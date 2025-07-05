package co.uk.bbk.culinarycompanion.ui.addrecipe

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import co.uk.bbk.culinarycompanion.data.Recipe
import co.uk.bbk.culinarycompanion.data.RecipeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.any
import org.mockito.kotlin.argumentCaptor

/**
 * Unit tests for AddRecipeViewModel
 * Tests the save recipe functionality
 */
@ExperimentalCoroutinesApi
class AddRecipeViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var mockRepository: RecipeRepository

    private lateinit var viewModel: AddRecipeViewModel
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        Dispatchers.setMain(testDispatcher)
        viewModel = AddRecipeViewModel(mockRepository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    /**
     * Test saving a recipe with all fields
     */
    @Test
    fun saveRecipe_withAllFields_callsRepository() = runTest {
        // Given
        val title = "Test Recipe"
        val ingredients = "Test ingredients"
        val instructions = "Test instructions"
        val category = "Breakfast"
        val protein = 10.5
        val carbs = 20.0
        val fat = 5.5

        // When
        viewModel.saveRecipe(
            title = title,
            ingredients = ingredients,
            instructions = instructions,
            category = category,
            protein = protein,
            carbs = carbs,
            fat = fat
        )
        advanceUntilIdle()

        // Then
        val captor = argumentCaptor<Recipe>()
        verify(mockRepository).insertRecipe(captor.capture())

        val savedRecipe = captor.firstValue
        assert(savedRecipe.title == title)
        assert(savedRecipe.ingredients == ingredients)
        assert(savedRecipe.instructions == instructions)
        assert(savedRecipe.category == category)
        assert(savedRecipe.protein == protein)
        assert(savedRecipe.carbs == carbs)
        assert(savedRecipe.fat == fat)
    }

    /**
     * Test saving a recipe without optional fields
     */
    @Test
    fun saveRecipe_withoutOptionalFields_callsRepository() = runTest {
        // Given
        val title = "Simple Recipe"
        val ingredients = "Basic ingredients"
        val instructions = "Simple instructions"
        val category = "Lunch"

        // When
        viewModel.saveRecipe(
            title = title,
            ingredients = ingredients,
            instructions = instructions,
            category = category
        )
        advanceUntilIdle()

        // Then
        val captor = argumentCaptor<Recipe>()
        verify(mockRepository).insertRecipe(captor.capture())

        val savedRecipe = captor.firstValue
        assert(savedRecipe.title == title)
        assert(savedRecipe.ingredients == ingredients)
        assert(savedRecipe.instructions == instructions)
        assert(savedRecipe.category == category)
        assert(savedRecipe.protein == null)
        assert(savedRecipe.carbs == null)
        assert(savedRecipe.fat == null)
    }
}