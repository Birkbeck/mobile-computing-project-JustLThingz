/**
 * Culinary Companion - Recipe Management App
 * BSc Computing Coursework 2
 *
 * Utility functions for recipe text formatting.
 * Handles numbering for ingredients and instructions.
 *
 * @author Levi GIRARD
 * @studentId 13401249
 */

package co.uk.bbk.culinarycompanion.utils

/**
 * Utility functions for recipe formatting
 */
object RecipeUtils {

    /**
     * Format ingredients string with numbered lines
     * Filters empty lines and adds sequential numbering
     * @param ingredients Raw ingredients text
     * @return Formatted ingredients with numbers
     */
    fun formatIngredients(ingredients: String): String {
        return ingredients.lines()
            .filter { it.isNotBlank() }
            .mapIndexed { index, ingredient ->
                "${index + 1}) ${ingredient.trim()}"
            }
            .joinToString("\n")
    }

    /**
     * Format instructions string with numbered steps
     * Filters empty lines and adds sequential numbering
     * @param instructions Raw instructions text
     * @return Formatted instructions with step numbers
     */
    fun formatInstructions(instructions: String): String {
        return instructions.lines()
            .filter { it.isNotBlank() }
            .mapIndexed { index, instruction ->
                "${index + 1}) ${instruction.trim()}"
            }
            .joinToString("\n")
    }

    /**
     * Parse ingredients from formatted string
     * Removes numbering to restore original format
     * @param formattedIngredients Numbered ingredients text
     * @return Clean ingredients without numbers
     */
    fun parseIngredients(formattedIngredients: String): String {
        return formattedIngredients.lines()
            .map { line ->
                // Remove numbering if present
                line.replaceFirst(Regex("^\\d+\\)\\s*"), "")
            }
            .joinToString("\n")
    }

    /**
     * Parse instructions from formatted string
     * Removes numbering to restore original format
     * @param formattedInstructions Numbered instructions text
     * @return Clean instructions without numbers
     */
    fun parseInstructions(formattedInstructions: String): String {
        return formattedInstructions.lines()
            .map { line ->
                // Remove numbering if present
                line.replaceFirst(Regex("^\\d+\\)\\s*"), "")
            }
            .joinToString("\n")
    }
}