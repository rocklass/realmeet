# RealMeet

RealMeet is an Android app that allows users to capture, share, and experience the excitement of real-time meetups. The app lets users receive notifications for group matchups, capture the moments, and easily share their experiences. It's built with Kotlin, Jetpack Compose, and modern Android libraries to ensure a smooth and efficient user experience.

## Features implemented

- **Home Page**: 
  - Displays essential information to the user.
  - Requests notification permission from the user.
  - Contains a button to simulate the reception of a push notification from the server.

- **Capture**: 
  - Enables users to capture their RealMeet moments.
  - Handles camera permission and provides camera functionality.

- **Share**: 
  - Allows users to share their captured RealMeet moments.

## Technologies Used

The following technologies are used to build the RealMeet app:

- **Kotlin**: Modern, expressive language for Android development.
- **Jetpack Compose**: UI toolkit for building native UIs in Kotlin.
- **Jetpack Compose Navigation**: Simplified navigation between screens in Jetpack Compose.
- **Kotlin Coroutines**: For asynchronous operations and background processing.
- **Kotlin Serialization**: For converting data into JSON format and vice versa.
- **CameraX**: Library for handling camera functionalities in Android.
- **Retrofit**: HTTP client for making API requests to the server.
- **Coil**: Image loading library for displaying images in the app.
- **JUnit**: Unit testing framework for testing the app logic.
- **Mockito**: Mocking framework for testing and stubbing dependencies.
- **Turbine**: For testing states emitted in a `StateFlow`.

## Architecture

The app is designed using **Clean Architecture** with **modularization**. It consists of the following components:

- **Core modules**: Shared utilities, network, and data handling code.
- **Feature modules**: Separate modules for each feature, ensuring scalability and maintainability, one module for each layer of the clean architecture:
1. **Domain Layer**: Contains business logic and use cases (pure Kotlin modules without dependency to the Android framework).
2. **Data Layer**: Handles data operations, such as network calls.
3. **UI Layer**: Responsible for the user interface, built using Jetpack Compose.

Additionally, I’ve implemented a small **design system** to create reusable UI components. This design system ensures consistency across the app’s interface and allows easy scaling of UI elements while maintaining uniformity.

## What is missing for the MVP?

The following features are yet to be implemented:

- **Location permission and sending location regularly**: 
  - The app does not request location permissions, nor does it send the user's location to the server.
  
- **Group matchmaking**: 
  - A feature to display the group matchmaking screen when the user opens the push notification.

- **Countdown for RealMeet capture**: 
  - A countdown timer to trigger the capture of the RealMeet once a group is assigned.

- **Customization of the RealMeet**: 
  - Users will be able to customize their RealMeet moments with a legend.
