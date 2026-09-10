# Product Catalog App

A native Android application built with Kotlin and Jetpack Compose to display a catalog of products from the DummyJSON API.

## Features
- **Product List**: Displays products with thumbnails, titles, and prices.
- **Pagination**: Loads more items automatically as the user scrolls.
- **Search**: Debounced search bar to filter products.
- **Product Detail**: View full product details including images, description, rating, and price.
- **State Management**: Beautifully handles Loading, Success, Empty, and Error states (with a Retry button).
- **Bonus Features included**: Pull-to-refresh and image loading placeholders/error handling via Coil.
- **Bonus Unit Test included**: Validates the mapping logic from the Data Transfer Object to the Domain Model.

## Tech Stack & Architecture
- **Language**: Kotlin
- **UI Toolkit**: Jetpack Compose
- **Architecture**: Clean Architecture principles with MVVM (Model-View-ViewModel) separating Domain, Data, and Presentation layers.
- **State Management**: Kotlin Coroutines & StateFlow
- **Networking**: Retrofit2 & OkHttp
- **Dependency Injection**: Koin
- **Image Loading**: Coil
- **Navigation**: Jetpack Navigation Compose

## Architecture Decisions
- **Why MVVM?** It's the official Google recommendation and seamlessly integrates with StateFlow and Jetpack Compose for reactive UI updates.
- **Why Koin?** For a small, time-boxed project (2-3 hours), Koin is significantly faster to configure than Hilt while still demonstrating excellent dependency injection practices.
- **Why StateFlow?** `StateFlow` provides a distinct, single source of truth for the UI state (`Loading`, `Success`, `Error`), avoiding multiple scattered LiveData variables.
- **Search implementation**: Handled client-side debouncing (500ms delay) using Coroutines `debounce`, then hitting the `products/search` endpoint. This is more robust than fetching all data client-side and filtering locally, as the catalog might grow infinitely.
- **Pagination**: Implemented manual pagination state tracking the `skip` parameter in the ViewModel, triggered when the user scrolls near the bottom of the list.

## How to Run
1. Open this project in Android Studio.
2. Sync Project with Gradle Files.
3. Build and Run on an Android Emulator or physical device.
*(No API keys required as dummyjson.com is free)*

## Note on AI Usage
In accordance with the assessment instructions, AI was used minimally for guidance, syntax formatting, and generating some of the boilerplate code (like Gradle setups). The core architecture choices, state management logic, mapping layers, and overall project structure are my own decisions. I am fully prepared to explain every line of this codebase in the walkthrough video.
