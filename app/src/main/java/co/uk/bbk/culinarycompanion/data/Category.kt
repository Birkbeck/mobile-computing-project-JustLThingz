package co.uk.bbk.culinarycompanion.data

/**
 * Enum class representing the recipe categories
 * As specified in the assignment requirements
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