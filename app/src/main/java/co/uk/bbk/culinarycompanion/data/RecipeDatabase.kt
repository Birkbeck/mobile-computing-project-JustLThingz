package co.uk.bbk.culinarycompanion.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

/**
 * Room database for the Culinary Companion app
 * This class defines the database configuration and serves as the main access point
 * for the underlying connection to the app's persisted data
 */
@Database(
    entities = [Recipe::class],
    version = 1,
    exportSchema = false
)
abstract class RecipeDatabase : RoomDatabase() {

    /**
     * Get the Recipe DAO
     */
    abstract fun recipeDao(): RecipeDAO

    companion object {
        // Singleton prevents multiple instances of database opening at the same time
        @Volatile
        private var INSTANCE: RecipeDatabase? = null

        /**
         * Get the singleton instance of the database
         * @param context The application context
         * @return The database instance
         */
        fun getDatabase(context: Context): RecipeDatabase {
            // If the INSTANCE is not null, return it
            // Otherwise, create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    RecipeDatabase::class.java,
                    "recipe_database"
                )
                    // Wipes and rebuilds instead of migrating if no Migration object
                    // Migration is not part of this assignment
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                // Return instance
                instance
            }
        }
    }
}