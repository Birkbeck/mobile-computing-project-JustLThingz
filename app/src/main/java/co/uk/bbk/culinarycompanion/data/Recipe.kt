package co.uk.bbk.culinarycompanion.data

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Entity class representing a Recipe in the database
 * Each recipe must have a title, ingredients, instructions, and category
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