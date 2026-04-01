# ShopKMP - Compose Multiplatform E-commerce App

ShopKMP is a modern, cross-platform e-commerce application built using **Kotlin Multiplatform (KMP)** and **Compose Multiplatform**. It shares logic and UI across **Android**, **iOS**, and **Desktop (JVM)**.

## Features
- **Product Catalog**: Browse a variety of products with detailed information.
- **Cart Management**: Add products to cart, update quantities, and manage items seamlessly.
- **Offline Support**: Uses **Room Database** for local persistence of cart and order data.
- **Image Loading**: Efficient image loading across platforms using **Coil 3**.
- **Clean Architecture**: Organized into Data, Domain, and Presentation layers for better maintainability.

## Project Structure

* [/composeApp](./composeApp/src) is for code that will be shared across your Compose Multiplatform applications.
  It contains several subfolders:
  - [commonMain](./composeApp/src/commonMain/kotlin) is for code that’s common for all targets.
  - Other folders are for Kotlin code that will be compiled for only the platform indicated in the folder name (androidMain, iosMain, jvmMain).

* [/iosApp](./iosApp/iosApp) contains the iOS entry point and SwiftUI code for the project.

## Tech Stack
- **UI**: Compose Multiplatform
- **Networking**: Ktor
- **Database**: Room
- **Image Loading**: Coil 3
- **Navigation**: Jetpack Navigation (Compose)

## Build and Run

### Android Application
To build and run the development version of the Android app, use the run configuration in your IDE or run:
```shell
./gradlew :composeApp:assembleDebug
```

### Desktop (JVM) Application
To build and run the desktop app:
```shell
./gradlew :composeApp:run
```

### iOS Application
To build and run the iOS app, use the run configuration in your IDE or open the `/iosApp` directory in Xcode.

---

Learn more about [Kotlin Multiplatform](https://www.jetbrains.com/help/kotlin-multiplatform-dev/get-started.html)…
