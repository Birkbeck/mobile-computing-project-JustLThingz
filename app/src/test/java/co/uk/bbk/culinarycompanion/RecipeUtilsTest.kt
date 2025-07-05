package co.uk.bbk.culinarycompanion.utils

import org.junit.Test
import org.junit.Assert.*

/**
 * Unit tests for RecipeUtils
 * Tests formatting and parsing functions for ingredients and instructions
 */
class RecipeUtilsTest {

    /**
     * Test formatting ingredients with numbering
     */
    @Test
    fun formatIngredients_addsNumbering() {
        // Given
        val ingredients = "Eggs\nFlour\nMilk\nSugar"

        // When
        val formatted = RecipeUtils.formatIngredients(ingredients)

        // Then
        val expected = "1) Eggs\n2) Flour\n3) Milk\n4) Sugar"
        assertEquals(expected, formatted)
    }

    /**
     * Test formatting handles empty lines
     */
    @Test
    fun formatIngredients_ignoresEmptyLines() {
        // Given
        val ingredients = "Eggs\n\nFlour\n\n\nMilk"

        // When
        val formatted = RecipeUtils.formatIngredients(ingredients)

        // Then
        val expected = "1) Eggs\n2) Flour\n3) Milk"
        assertEquals(expected, formatted)
    }

    /**
     * Test parsing ingredients removes numbering
     */
    @Test
    fun parseIngredients_removesNumbering() {
        // Given
        val formatted = "1) Eggs\n2) Flour\n3) Milk"

        // When
        val parsed = RecipeUtils.parseIngredients(formatted)

        // Then
        val expected = "Eggs\nFlour\nMilk"
        assertEquals(expected, parsed)
    }

    /**
     * Test formatting instructions with steps
     */
    @Test
    fun formatInstructions_addsStepNumbers() {
        // Given
        val instructions = "Mix ingredients\nBake for 20 minutes\nLet cool"

        // When
        val formatted = RecipeUtils.formatInstructions(instructions)

        // Then
        val expected = "1) Mix ingredients\n2) Bake for 20 minutes\n3) Let cool"
        assertEquals(expected, formatted)
    }
}