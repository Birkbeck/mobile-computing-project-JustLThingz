/**
 * Culinary Companion - Recipe Management App
 * BSc Computing Coursework 2
 *
 * Enum class defining the six recipe categories as per assignment requirements.
 * Provides utility methods for category name conversions.
 *
 * @author Levi GIRARD
 * @studentId 13401249
 */

package co.uk.bbk.culinarycompanion.data

/**
 * Enum class representing the recipe categories
 * As specified in the assignment requirements
 *
 * @property displayName User-friendly name for UI display
 */

enum class Category(val displayName: String) {
    BREAKFAST("Breakfast"),
    BRUNCH("Brunch"),
    LUNCH("Lunch"),
    DINNER("Dinner"),
    DESSERTS("Desserts"),
    OTHER("Other");

    companion object {
        /**
         * Get a Category from its display name
         * @param displayName The display name of the category
         * @return The matching Category, or OTHER if not found
         */
        fun fromDisplayName(displayName: String): Category {
            return values().find { it.displayName == displayName } ?: OTHER
        }

        /**
         * Get all category display names
         * @return List of all category display names
         */
        fun getAllDisplayNames(): List<String> {
            return values().map { it.displayName }
        }
    }
}