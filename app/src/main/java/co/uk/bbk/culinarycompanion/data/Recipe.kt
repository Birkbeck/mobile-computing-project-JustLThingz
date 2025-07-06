/**
 * Culinary Companion - Recipe Management App
 * BSc Computing Coursework 2
 *
 * Entity class representing a Recipe in the Room database.
 * Contains all recipe information including optional nutritional macros.
 *
 * @author Levi GIRARD
 * @studentId 13401249
 */
package co.uk.bbk.culinarycompanion.data

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Entity class representing a Recipe in the database
 * Each recipe must have a title, ingredients, instructions, and category
 *
 * @property id Unique identifier for the recipe (auto-generated)
 * @property title Name of the recipe
 * @property ingredients List of ingredients stored as newline-separated string
 * @property instructions Cooking steps stored as newline-separated string
 * @property category One of the predefined categories (Breakfast, Brunch, etc.)
 * @property imagePath Optional path to recipe image
 * @property protein Optional protein content in grams
 * @property carbs Optional carbohydrate content in grams
 * @property fat Optional fat content in grams
 * @property createdAt Timestamp when recipe was created
 * @property updatedAt Timestamp when recipe was last modified
 */

@Entity(tableName = "recipes")
data class Recipe(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val title: String,

    val ingredients: String, // Stored as a single string, separated by newlines

    val instructions: String, // Stored as a single string, separated by newlines

    val category: String, // One of: Breakfast, Brunch, Lunch, Dinner, Desserts, Other

    val imagePath: String? = null, // Optional image path

    // Optional macros
    val protein: Double? = null,
    val carbs: Double? = null,
    val fat: Double? = null,

    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis()
)