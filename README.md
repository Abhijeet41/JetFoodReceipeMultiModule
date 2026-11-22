JetFoodRecipeAppMultiModule/
├── app/                                     # Entry point module (DI, Navigation graphs, App theme & Host Activity)
│   └── src/main/java/com/abhi41/jetfoodrecipeappmultimodule/
│       ├── di/                              # Dependency Injection modules (ImageLoaderModule, etc.)
│       ├── navigation/                      # App-level Navigation graphs & custom NavTypes (BaseNavGraph, RecipeNavGraph)
│       ├── ui/theme/                        # Material 3 theme styling, colors, and typography
│       ├── MainActivity.kt                  # Single-activity host for Compose UI
│       └── MyApplication.kt                 # Application class for Hilt / DI initialization
│
├── core-database/                           # Local database core module (Room persistence & caching)
│   └── src/main/java/com/abhi41/recipe/core_database/
│       ├── converters/                      # Room type converters for complex database columns
│       ├── dao/                             # Data Access Objects (RecipesDao, FoodJokeDao)
│       ├── database/                        # Room database instance configuration (RecipesDatabase)
│       ├── di/                              # Database dependency injection module (DatabaseModule)
│       ├── entity/                          # Room database entity tables (FavoriteEntity, ResultEntity, FoodJokeEntity)
│       └── utils/                           # JSON parsers and database constants (GsonParser, JsonParser)
│
├── core-network/                            # Network core module (Retrofit, API services, and Gemini AI integration)
│   └── src/main/java/com/abhi41/core_network/
│       ├── di/                              # Retrofit, OkHttpClient, and Network DI module (NetworkModule)
│       ├── dtos/                            # API Response Data Transfer Objects (FoodRecipeDto, ResultDto, FoodJokeDto)
│       ├── gemini/                          # Gemini AI API integration services and LLM models
│       ├── service/                         # Retrofit API interface definitions (FoodRecipesApi)
│       └── Constants.kt                     # Network base URLs, API keys, and query constants
│
├── receipe/                                 # Feature modules for Recipe domain (Clean Architecture implementation)
│   ├── data/                                # Data Layer: Repositories implementation, Local/Remote sources, and Mappers
│   │   └── src/main/java/com/abhi41/receipe/data/
│   │       ├── agent/                       # AI Agent implementation (RecipeAgentImpl)
│   │       ├── di/                          # Data layer Hilt modules (AgentModule, AnalyticsModule, RecipeDataModule)
│   │       ├── mappers/                     # Mappers transforming DTOs/Entities to Domain models
│   │       └── repository/                  # Repository implementations (RecipesRepositoryImpl, PreferencesRepositoryImpl)
│   │
│   ├── domain/                              # Domain Layer: Pure business logic, UseCases, Repository interfaces, & Models
│   │   └── src/main/java/com/abhi41/receipe/domain/
│   │       ├── agent/                       # AI Agent interface definition (RecipeAgent)
│   │       ├── di/                          # Domain layer DI modules (DispatcherModule)
│   │       ├── models/                      # Domain data models (RecipeResult, FoodJoke, RecipeAnalyticsEvent)
│   │       ├── repository/                  # Repository contracts/interfaces (RecipesRepository, AnalyticsTracker)
│   │       ├── useCases/                    # Business use case interactors (GetAllRecipesUseCase, SearchResultUseCase)
│   │       └── utils/                       # Domain utilities (Resource wrapper, DispatcherProvider, MealAndDietType)
│   │
│   └── presentation/                        # Presentation Layer: Jetpack Compose UI, ViewModels, and UI States
│       └── src/main/java/com/abhi41/receipe/
│           ├── presentation/
│           │   ├── chat/                    # AI Chat screen, ChatViewModel, and message models
│           │   ├── common/                  # Reusable UI components (Chips, BackPressHandler)
│           │   ├── dashBoard/               # Dashboard screen hosting bottom navigation
│           │   ├── favorite/                # Favorites screen, FavoritesViewModel, and top bar
│           │   ├── joke/                    # Food joke screen, FoodJokeViewModel, and top bar
│           │   ├── navigation/              # Bottom navigation item definitions (BottomNavScreen)
│           │   ├── new_recipe_ui/           # Custom recipe creation UI screens
│           │   ├── recipeDetail/            # Recipe details screen, DetailViewModel, and tab screens (Overview, Ingredients, Instructions)
│           │   ├── recipes/                 # Main recipe listing screen, RecipesViewModel, and UI state models
│           │   ├── search/                  # Recipe search screen and SearchViewModel
│           │   ├── splash/                  # Splash screen animation/launch flow
│           │   └── utils/                   # Presentation helpers, composition locals, and color converters
│           └── ui/theme/                    # Feature-specific theme extensions and dimension values
│
└── dsa/                                     # Utility module for Data Structures & Algorithms practice
    └── src/main/java/com/abhi41/dsa/
        └── TestProgramm/                    # Array sorting, number problems, string manipulation, and search algorithms
