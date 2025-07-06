[![Review Assignment Due Date](https://classroom.github.com/assets/deadline-readme-button-22041afd0340ce965d47ae6ef1cefeee28c7c493a6346c4f15d667ab976d596c.svg)](https://classroom.github.com/a/8FwP1yxa)

# Culinary Companion

A recipe management Android application that allows users to create, store, and organise their own recipes by categories.

## Features

- **Recipe Management**
  - Create new recipes with title, ingredients, and cooking instructions
  - Edit existing recipes
  - Delete recipes with confirmation dialog
  - Optional macro tracking (protein, carbs, fats)

- **Organisation**
  - Six predefined categories: Breakfast, Brunch, Lunch, Dinner, Desserts, Other
  - View recipes filtered by category
  - Search within categories

- **User Experience**
  - Clean Material Design interface
  - Empty state indicators
  - Confirmation dialogs for destructive actions
  - Unsaved changes warnings
  - Image placeholder for future photo functionality

- **Data Persistence**
  - Local storage using Room database
  - Data survives app restarts
  - No internet connection required

## Architecture

This app follows the **MVVM (Model-View-ViewModel)** architecture pattern with:

- **Model**: Room database with Recipe entity and DAO
- **View**: Fragments with View Binding
- **ViewModel**: Lifecycle-aware ViewModels with LiveData
- **Repository**: Single source of truth for data access

### Key Components:

- **Room Database**: Local data persistence
- **Navigation Component**: Type-safe navigation between screens
- **Coroutines**: Asynchronous database operations
- **LiveData & Flow**: Reactive UI updates
- **View Binding**: Null-safe view references

## Requirements

- Android Studio Hedgehog (2023.1.1) or newer
- Minimum SDK: 24 (Android 7.0)
- Target SDK: 35 (Android 14)
- Kotlin 2.0.21

## Setup Instructions

1. **Clone the repository**
   ```bash
   git clone https://github.com/Birkbeck/mobile-computing-project-JustLThingz.git
   ```

2. **Open in Android Studio**
   - File → Open → Select the project directory

3. **Sync project**
   - Android Studio should automatically prompt to sync
   - If not: File → Sync Project with Gradle Files

4. **Run the app**
   - Connect an Android device or start an emulator
   - Click the "Run" button or press Shift+F10

## Project Structure

```
app/
├── src/
│   ├── main/
│   │   ├── java/co/uk/bbk/culinarycompanion/
│   │   │   ├── data/              # Database layer
│   │   │   │   ├── Recipe.kt      # Entity class
│   │   │   │   ├── RecipeDAO.kt   # Data Access Object
│   │   │   │   ├── RecipeDatabase.kt
│   │   │   │   ├── RecipeRepository.kt
│   │   │   │   └── Category.kt    # Category enum
│   │   │   ├── ui/                # UI layer
│   │   │   │   ├── home/          # Home screen
│   │   │   │   ├── recipelist/    # Recipe list screen
│   │   │   │   ├── recipeview/    # Recipe details screen
│   │   │   │   ├── addrecipe/     # Add recipe screen
│   │   │   │   ├── editrecipe/    # Edit recipe screen
│   │   │   │   └── dialog/        # Dialog fragments
│   │   │   ├── utils/             # Utility classes
│   │   │   ├── MainActivity.kt    # Single activity
│   │   │   └── CulinaryCompanionApplication.kt
│   │   └── res/
│   │       ├── layout/            # XML layouts
│   │       ├── navigation/        # Navigation graph
│   │       └── values/            # Strings, themes, etc.
│   └── test/                      # Unit tests
│       └── androidTest/           # Instrumented tests
```


## Testing

### Unit Tests
Run unit tests with:
```bash
./gradlew test
```

### Instrumented Tests
Run instrumented tests on a device/emulator:
```bash
./gradlew connectedAndroidTest
```

### Test Coverage
- RecipeDAO operations
- Repository methods
- Utility functions
- ViewModel logic

## Navigation Flow

1. **Home Screen** → Select a category
2. **Recipe List** → View recipes in category
   - Search recipes
   - Add new recipe (+)
   - Delete mode
   - Click recipe to view
3. **Recipe View** → View full recipe details
   - Edit recipe
4. **Add/Edit Recipe** → Create or modify recipes
   - Validation
   - Save/Cancel with confirmation

## Future Enhancements

- Image capture and gallery selection
- Recipe sharing functionality
- Export/Import recipes
- Additional categories
- Recipe ratings
- Cooking timers
- Shopping list generation

## Dependencies

- AndroidX Core, AppCompat, Material
- Room Database
- Navigation Component
- Lifecycle & ViewModel
- Coroutines
- Mockito (testing)

## License

This project was created as coursework for BSc Computing at Birkbeck, University of London.

## Author

Levi GIRARD - Student ID: 13401249