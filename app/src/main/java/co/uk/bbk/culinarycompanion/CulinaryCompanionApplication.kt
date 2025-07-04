package co.uk.bbk.culinarycompanion

import android.app.Application
import co.uk.bbk.culinarycompanion.data.RecipeDatabase
import co.uk.bbk.culinarycompanion.data.RecipeRepository

/**
 * Application class for Culinary Companion
 * Provides a single instance of the database and repository throughout the app
 */
class CulinaryCompanionApplication : Application() {

    // Using lazy so the database and repository are only created when needed
    val database by lazy { RecipeDatabase.getDatabase(this) }
    val repository by lazy { RecipeRepository(database.recipeDao()) }
}