package co.uk.bbk.culinarycompanion.utils

/**
 * Utility functions for recipe formatting
 */
object RecipeUtils {

    /**
     * Format ingredients string with numbered lines
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