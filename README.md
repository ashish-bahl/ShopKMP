# ShopKMP

ShopKMP is a modern, cross-platform e-commerce application built using **Kotlin Multiplatform (KMP)** and **Compose Multiplatform**. It shares logic and UI across **Android**, **iOS**.

## Current Status

- Product catalog browsing is working.
- Cart flow is available.
- Order and checkout flow is in progress.
- Offline-first architecture is in progress.

## Tech Stack

- Kotlin Multiplatform
- Compose Multiplatform
- Ktor
- Room
- Koin
- Coil 3

## Architecture

The shared code in `composeApp` follows a simple Clean Architecture-inspired structure:

- `domain`: core models, repository contracts, and use cases
- `data`: repository implementations, local persistence, and remote API integration
- `presentation`: shared UI, state, navigation, and view models

## Project Structure

- [composeApp/src/commonMain/kotlin](./composeApp/src/commonMain/kotlin): shared app code
- [composeApp/src/androidMain/kotlin](./composeApp/src/androidMain/kotlin): Android-specific setup
- [composeApp/src/iosMain/kotlin](./composeApp/src/iosMain/kotlin): iOS-specific setup
- [/iosApp](./iosApp/iosApp): iOS entry app

## API Setup

This repository is intended to stay public, so the MockAPI project secret is not committed to version control.

Add this to `local.properties`:

```properties
mockApiProjectSecret=your_mockapi_project_secret
```

The app builds the product API URL from that local secret at build time.

## Build and Run

### Android

```sh
./gradlew :composeApp:assembleDebug
```

### iOS
To build and run the iOS app, use the run configuration in your IDE or open the `/iosApp` directory in Xcode and run the app from there.
<br/><br/>
