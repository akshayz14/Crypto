# Coin

Coin is an Android application that shows a list of cryptocurrencies. This project demonstrates the use of modern Android development tools and techniques, including MVVM, Hilt, Room, Retrofit, Clean Architecture, StateFlow, Unit testing and the Repository pattern.

## Features

- View a list cryptocurrencies with their crypto symbol.
- Search and filter functionality
- Offline caching of data
- Dependency injection with Hilt
- Reactive UI with StateFlow
- Unit testing with Junit, espresso and mockito. 

## Architecture

The app is built using Clean Architecture principles to ensure a separation of concerns and to make the codebase scalable and maintainable. The architecture includes the following layers:

1. **Presentation Layer**: Contains UI components and ViewModels.
2. **Domain Layer**: Contains use cases and business logic.
3. **Data Layer**: Contains repositories and data sources (both local and remote).

## Libraries and Tools

- **MVVM**: Model-View-ViewModel architecture pattern to separate UI logic from business logic.
- **Hilt**: Dependency injection library to manage dependencies.
- **Room**: Persistence library to cache data locally.
- **Retrofit**: HTTP client for network operations.
- **StateFlow**: State management for reactive UI updates.
- **Repository Pattern**: Abstracts the data sources and provides a clean API for data access.
