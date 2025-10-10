# Pokemon Application

A modern Android application built with Jetpack Compose that displays Pokemon data from the PokeAPI. The app features a clean, intuitive interface for browsing Pokemon lists and viewing detailed information about individual Pokemon.

## 🚀 Features

- **Pokemon List View**: Browse through a paginated list of Pokemon with names and IDs
- **Pokemon Details**: View detailed information about individual Pokemon including:
  - Pokemon name and ID
  - Height
  - High-quality Pokemon images
- **Navigation**: Smooth navigation between list and details screens
- **Modern UI**: Built with Material Design 3 and Jetpack Compose
- **Image Loading**: Efficient image loading with Coil
- **Error Handling**: Graceful error handling with user-friendly messages
- **Loading States**: Proper loading indicators during data fetching
- **Dependency Injection**: Clean architecture with Hilt DI

## 🏗️ Architecture

The app follows **Clean Architecture** with **multi-module organization** for optimal scalability, testability, and build performance:

### Modular Architecture
- **7 Gradle Modules**: Organized by layer and feature for parallel development
- **Clean Architecture Layers**: Domain, Data, Presentation clearly separated
- **Feature Modules**: Independent, self-contained feature implementations
- **Dependency Inversion**: Domain layer is pure Kotlin with no Android dependencies

### Architecture Principles
- **MVVM Pattern**: Model-View-ViewModel for UI layer
- **Use Cases**: Business logic encapsulation in domain layer
- **Repository Pattern**: Data layer abstraction
- **Dependency Injection**: Hilt for compile-time safe DI
- **State Management**: Reactive state with StateFlow and Kotlin Result type
- **Mapper Pattern**: Clean separation between DTOs and Domain entities

### Module Dependency Graph
```
:app (Application entry point)
 ├─→ :feature:pokemon-list
 ├─→ :feature:pokemon-details
 ├─→ :data
 ├─→ :core:ui
 └─→ :core:common

:feature:pokemon-list & :feature:pokemon-details
 ├─→ :domain (Use cases)
 ├─→ :data (Repository implementations)
 ├─→ :core:ui (Shared UI components)
 └─→ :core:common (Utilities, models)

:data
 ├─→ :domain (Repository interfaces)
 └─→ :core:common (Shared models)

:core:ui
 └─→ :core:common (Shared models)

:domain
 └─→ Pure Kotlin module (no dependencies)

:core:common
 └─→ Android library (shared utilities)
```

## 📱 Screenshots

<details>
<summary>Click to view app screenshots</summary>

![Pokemon List Screen](static/PokemonListScreen.png)
*Pokemon List Screen - Browse through Pokemon with names and IDs*

![Pokemon Details Screen](static/PokemonDetailsScreen.png)
*Pokemon Details Screen - View detailed Pokemon information*

The app consists of two main screens:

1. **Pokemon List Screen**: Displays a grid of Pokemon cards with names and IDs
2. **Pokemon Details Screen**: Shows comprehensive Pokemon information in a beautiful card layout

</details>

## 🛠️ Tech Stack

### Core Technologies
- **Kotlin**: Primary programming language
- **Android SDK**: Target SDK 35, Minimum SDK 24
- **Jetpack Compose**: Modern UI toolkit
- **Material Design 3**: Design system

### Libraries & Dependencies

#### UI & Navigation
- **Jetpack Compose BOM**: `2024.12.01`
- **Material 3**: Modern Material Design components
- **Navigation Compose**: `2.9.5` - Type-safe navigation
- **Activity Compose**: `1.11.0` - Compose integration
- **Lifecycle ViewModel Compose**: `2.9.4` - ViewModel integration

#### Dependency Injection
- **Hilt Android**: `2.48` - Dependency injection framework
- **Hilt Navigation Compose**: `1.2.0` - Navigation integration
- **KSP**: `1.9.22-1.0.17` - Kotlin Symbol Processing

#### Networking & Data
- **Retrofit**: `2.9.0` - HTTP client for API calls
- **Gson Converter**: `2.9.0` - JSON serialization/deserialization
- **Coil Compose**: `2.4.0` - Image loading library

#### Core Android
- **AndroidX Core**: `1.17.0` - Core Android functionality
- **Lifecycle Runtime KTX**: `2.9.4` - Lifecycle-aware components
- **Kotlin Coroutines Core**: `1.5.0` - Coroutine support

#### Testing
- **JUnit**: `4.13.2` - Unit testing framework
- **Mockito**: `5.1.1` - Mocking framework
- **Mockito Kotlin**: `4.1.0` - Kotlin-friendly mocking
- **Mockito Inline**: `5.1.1` - Inline mocking for final classes
- **Kotlin Coroutines Test**: `1.7.3` - Coroutine testing utilities
- **AndroidX Core Testing**: `2.2.0` - Android testing utilities
- **MockWebServer**: `4.11.0` - HTTP server for testing
- **Espresso**: `3.7.0` - UI testing framework
- **Compose UI Test**: UI testing for Compose


### Installation

1. **Clone the repository**
   ```bash
   git clone https://github.com/kingrocfella/pokemonapp.git
   cd pokemonapp
   ```

2. **Open in Android Studio**
   - Open Android Studio
   - Select "Open an existing project"
   - Navigate to the project directory

3. **Sync Project**
   - Android Studio will automatically sync the project
   - Wait for Gradle sync to complete

4. **Run the App**
   - Connect an Android device or start an emulator
   - Click the "Run" button or press `Shift + F10`

### Building

#### Build Entire App
```bash
# Debug build (all modules)
./gradlew assembleDebug

# Release build (all modules)
./gradlew assembleRelease
```

#### Build Individual Modules
```bash
# Build only domain module (fastest - pure Kotlin)
./gradlew :domain:build

# Build only data module
./gradlew :data:assembleDebug

# Build only a feature module
./gradlew :feature:pokemon-list:assembleDebug
```

#### Parallel & Cached Builds
The project is configured for optimal build performance:
- **Parallel builds**: Multiple modules build simultaneously
- **Build caching**: Unchanged modules use cached outputs
- **Incremental compilation**: Only changed files recompile

For best performance, ensure `gradle.properties` includes:
```properties
org.gradle.caching=true
org.gradle.parallel=true
org.gradle.configureondemand=true
```

## 📁 Project Structure

The project is organized into **7 Gradle modules** following Clean Architecture principles:

```
Mypokemonapplication/
├── app/                                    # 📱 Application Module
│   ├── src/main/
│   │   ├── java/.../
│   │   │   ├── MainActivity.kt            # Navigation host
│   │   │   └── PokemonApplication.kt      # @HiltAndroidApp entry point
│   │   ├── res/                           # App resources
│   │   └── AndroidManifest.xml
│   └── build.gradle.kts                   # App module configuration
│
├── domain/                                 # 🧠 Domain Module (Pure Kotlin)
│   ├── src/main/java/.../domain/
│   │   ├── entities/
│   │   │   ├── Pokemon.kt                 # Core domain entities
│   │   │   ├── PokemonDetail.kt
│   │   │   └── PokemonListResult.kt
│   │   ├── repository/
│   │   │   └── PokemonRepository.kt       # Repository interface
│   │   └── usecase/
│   │       ├── GetPokemonListUseCase.kt   # Business logic
│   │       └── GetPokemonDetailsUseCase.kt
│   └── build.gradle.kts                   # Pure Kotlin module (java-library)
│
├── data/                                   # 💾 Data Module
│   ├── src/main/java/.../data/
│   │   ├── api/
│   │   │   ├── ApiService.kt              # Retrofit API interface
│   │   │   └── ApiRoutes.kt               # API endpoints
│   │   ├── remote/dto/
│   │   │   ├── PokemonListDto.kt          # Network DTOs
│   │   │   └── PokemonDetailsDto.kt
│   │   ├── mapper/
│   │   │   └── PokemonMapper.kt           # DTO → Domain mapping
│   │   ├── repository/
│   │   │   ├── PokemonRepositoryImpl.kt   # Repository implementation
│   │   │   └── PokemonUrlRepository.kt
│   │   ├── di/
│   │   │   └── DataModule.kt              # Hilt bindings
│   │   └── modules/
│   │       └── NetworkModule.kt           # Retrofit configuration
│   └── build.gradle.kts
│
├── core/
│   ├── common/                             # 🔧 Core Common Module
│   │   ├── src/main/java/.../
│   │   │   ├── common/
│   │   │   │   ├── screens/ScreenNames.kt # Navigation routes
│   │   │   │   └── utils/Utils.kt         # Utility functions
│   │   │   ├── models/
│   │   │   │   ├── ViewModelState.kt      # Shared state wrapper
│   │   │   │   └── UISkeletonData.kt      # UI state data
│   │   │   └── services/
│   │   │       └── NavigationService.kt   # Navigation helper
│   │   └── build.gradle.kts
│   │
│   └── ui/                                 # 🎨 Core UI Module
│       ├── src/main/java/.../
│       │   ├── feature/
│       │   │   └── UISkeleton.kt          # Shared UI components
│       │   └── ui/theme/
│       │       ├── Color.kt               # App colors
│       │       ├── Theme.kt               # Material theme
│       │       └── Type.kt                # Typography
│       └── build.gradle.kts
│
├── feature/
│   ├── pokemon-list/                       # 📋 Pokemon List Feature Module
│   │   ├── src/main/java/.../pokemonlist/
│   │   │   ├── PokemonListScreen.kt       # Screen entry point
│   │   │   ├── PokemonListViewModel.kt    # ViewModel
│   │   │   ├── DisplayPokemonList.kt      # List UI
│   │   │   └── PokemonItem.kt             # Item composable
│   │   └── build.gradle.kts
│   │
│   └── pokemon-details/                    # 📄 Pokemon Details Feature Module
│       ├── src/main/java/.../pokemondetails/
│       │   ├── PokemonDetailsScreen.kt    # Screen entry point
│       │   ├── PokemonDetailsViewModel.kt # ViewModel
│       │   ├── DisplayPokemonDetails.kt   # Details UI
│       │   └── PokemonDetailRow.kt        # Row composable
│       └── build.gradle.kts
│
├── static/                                 # 📸 Documentation Assets
│   ├── PokemonListScreen.png
│   └── PokemonDetailsScreen.png
│
├── build.gradle.kts                        # Root build configuration
├── settings.gradle.kts                     # Module declarations
├── gradle.properties                       # Build optimization
├── gradle/
│   └── libs.versions.toml                  # Centralized version catalog
├── ARCHITECTURE_DIAGRAM.md                 # Architecture documentation
├── CLEAN_ARCHITECTURE.md                   # Clean architecture guide
└── README.md                               # This file
```

### Module Responsibilities

| Module | Type | Purpose | Dependencies |
|--------|------|---------|--------------|
| `:app` | application | App entry point, navigation | All modules |
| `:domain` | java-library | Business logic, entities, use cases | None (pure Kotlin) |
| `:data` | library | Repository impl, API, DTOs, mappers | :domain, :core:common |
| `:core:common` | library | Shared utilities, models, services | None |
| `:core:ui` | library | Shared UI components, theme | :core:common |
| `:feature:pokemon-list` | library | Pokemon list feature | :domain, :data, :core:* |
| `:feature:pokemon-details` | library | Pokemon details feature | :domain, :data, :core:* |

## 🔧 Configuration

### API Configuration
The app uses the PokeAPI for Pokemon data:
- **Base URL**: `https://pokeapi.co/api/v2/`
- **Endpoints**: 
  - `/pokemon` - Pokemon list with pagination
  - `/pokemon/{id}` - Individual Pokemon details

### Build Configuration
- **Compile SDK**: 36
- **Target SDK**: 35 (only in :app module)
- **Minimum SDK**: 24
- **Java Version**: 17
- **Kotlin Version**: 1.9.22
- **Compose Compiler**: 1.5.8
- **Gradle**: 8.11.1
- **AGP (Android Gradle Plugin)**: 8.9.1
- **Module Count**: 7 (1 app + 6 libraries)

### Module Build Types
- **Application Module** (`:app`): Uses `com.android.application` plugin
- **Android Libraries** (5 modules): Use `com.android.library` plugin
- **Pure Kotlin Library** (`:domain`): Uses `java-library` + `kotlin-jvm` plugin

## 🧪 Testing

The modular architecture enables **fast, isolated testing** of individual modules:

### Running Tests

#### Test All Modules
```bash
# Run all unit tests
./gradlew test

# Run all Android unit tests
./gradlew testDebugUnitTest
```

#### Test Individual Modules (Recommended for TDD)
```bash
# Test domain layer only (super fast - pure JVM)
./gradlew :domain:test

# Test data layer only
./gradlew :data:testDebugUnitTest

# Test specific feature
./gradlew :feature:pokemon-list:testDebugUnitTest

# Test with auto-rerun on file changes (TDD mode)
./gradlew :feature:pokemon-list:testDebugUnitTest --continuous
```

#### Test Only Changed Modules
```bash
# Gradle automatically skips unchanged modules
./gradlew test --parallel
```

### Test Structure by Module

```
:app/src/test/                      # Integration tests
├── PokemonListViewModelTest.kt
└── PokemonDetailsViewModelTest.kt

:domain/src/test/                   # Pure unit tests (JVM)
└── usecase/
    ├── GetPokemonListUseCaseTest.kt
    └── GetPokemonDetailsUseCaseTest.kt

:data/src/test/                     # Repository tests
└── repository/
    └── PokemonRepositoryImplTest.kt

:feature:pokemon-list/src/test/     # Feature tests
└── PokemonListViewModelTest.kt

:core:common/src/test/              # Utility tests
└── utils/UtilsTest.kt
```

### Test Coverage
- **Unit Tests**: Test business logic in isolation
- **ViewModel Tests**: Test state management and UI logic
- **Repository Tests**: Test data layer with MockWebServer
- **Mockito Integration**: Mock external dependencies
- **Coroutine Testing**: Test async operations with `StandardTestDispatcher`
- **Test Fixtures**: Reusable fakes and test data

## 🚀 Development Workflow

### Fast Iteration with Incremental Builds

The modular architecture ensures you only rebuild what changed:

```bash
# Scenario 1: Change UI in pokemon-list feature
# → Only rebuilds :feature:pokemon-list and :app
# Time: ~8-12 seconds ⚡

# Scenario 2: Change domain logic
# → Rebuilds :domain + dependent modules
# Time: ~15-20 seconds ⚡

# Scenario 3: Change common utilities
# → Only rebuilds modules that use those utilities
# Time: ~10-15 seconds ⚡
```

### Android Studio Features

**Apply Changes (⚡ Lightning Bolt)**
- **Shortcut**: `Ctrl+Alt+F10` (Windows/Linux) or `Cmd+Shift+R` (Mac)
- **Use for**: Method body changes, UI tweaks
- **Speed**: 1-2 seconds (no rebuild!)

**Jetpack Compose Previews**
- Preview composables without running the app
- Instant feedback on UI changes
- No emulator needed for UI development

### Recommended Workflow

**During Active Development:**
```bash
# Use Apply Changes for most edits
# Let Gradle's incremental compilation handle rebuilds when needed
# Only run full builds before committing
```

**TDD (Test-Driven Development):**
```bash
# Run tests in continuous mode
./gradlew :feature:pokemon-list:testDebugUnitTest --continuous

# Make changes → Tests auto-run → Instant feedback
```

## ⚡ Performance Optimizations

### Build Performance
- **Parallel Builds**: Enabled via `org.gradle.parallel=true`
- **Build Cache**: Enabled via `org.gradle.caching=true`
- **Configuration on Demand**: Enabled via `org.gradle.configureondemand=true`
- **Kotlin Incremental Compilation**: Automatic with modular structure

### Build Time Comparison

| Change Type | Single Module | Modularized App | Savings |
|-------------|--------------|-----------------|---------|
| UI Change | 30-45s 🐌 | 8-12s ⚡ | ~70% faster |
| Domain Logic | 30-45s 🐌 | 15-20s ⚡⚡ | ~50% faster |
| Method Body | 30-45s 🐌 | 1-2s (Apply Changes) ⚡⚡⚡ | ~95% faster |
| Full Clean Build | 45-60s 🐌 | 45-60s 🐌 | Same (only do occasionally) |

### Module Build Times (Approximate)

```
:domain               → 3s  (pure Kotlin, no Android)
:core:common         → 4s  (small Android library)
:core:ui             → 4s  (theme + shared UI)
:data                → 5s  (API + repositories)
:feature:pokemon-list     → 6s  (feature with UI)
:feature:pokemon-details  → 6s  (feature with UI)
:app                 → 8s  (wiring everything together)
```

## 📚 Additional Documentation

- **[ARCHITECTURE_DIAGRAM.md](ARCHITECTURE_DIAGRAM.md)**: Visual architecture diagrams
- **[CLEAN_ARCHITECTURE.md](CLEAN_ARCHITECTURE.md)**: Detailed guide to Clean Architecture implementation

## 🤝 Contributing

When contributing to this project:

1. **Work on feature branches**
2. **Run tests for affected modules** before committing
3. **Follow the existing module structure** for new features
4. **Update documentation** for significant changes
5. **Use the established patterns** (Repository, UseCase, etc.)

### Adding New Features

To add a new feature:

```bash
# 1. Create a new feature module
mkdir -p feature/your-feature/src/main/java/com/example/mypokemonapplication/feature/yourfeature

# 2. Add build.gradle.kts with feature-module configuration

# 3. Add module to settings.gradle.kts
include(":feature:your-feature")

# 4. Add dependency in :app module
implementation(project(":feature:your-feature"))
```

## 📄 License

[Your License Here]

## 👨‍💻 Author

[Your Name/Organization]

## 🙏 Acknowledgments

- **PokeAPI**: For providing the Pokemon data
- **Android Team**: For Jetpack Compose and modern Android tools
- **Community**: For Clean Architecture patterns and best practices


