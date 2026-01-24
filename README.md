# eShopping

A simple Android shopping demo app built with Kotlin and Jetpack Compose. This project is a learning-focused implementation that demonstrates an MVVM architecture and reusable UI components for shopping apps. It includes basic product views, category browsing, search, cart functionality, and image handling (including a GIF holder).

## Tech stack
- Kotlin
- Jetpack Compose
- Firebase (Database, Authentication, Storage)
- Dagger Hilt (DI)
- Coil (image loading, including GIF support)
- MVVM architecture

## Features
- Basic product list and product detail views
- Mall view (aggregate listings)
- Categories and category filters
- Multiple search modes / different search options
- Add to cart and simple cart management
- Reusable, component-driven UI for use across the app
- GIF holder / GIF-capable image component (uses Coil)

## Project structure & architecture
The app follows a straightforward MVVM approach:
- View: Compose UI components and screens
- ViewModel: UI state and business logic
- Repository: Data access (Firebase)
- DI: Dagger Hilt for dependency injection
The code is intentionally not overly complex — this repo is part of my learning journey.

## Known issues / current problems
- Gradle sync / version issues: There are some incompatibilities between Gradle/plugin versions that currently prevent successful sync and launch in my environment. The code itself is implemented, but I cannot run the app locally until the Gradle sync is resolved.
- Firebase Storage access denied: Firebase Storage currently returns access-denied errors in my tests, so images are not loading. I highlighted the problem areas in the repository files where image loading/storage access is handled.
- Because of the above, image loading (including GIFs) may not display until Firebase Storage rules and/or credentials are fixed.

## Notes on images & GIFs
- The app uses Coil for image loading and supports GIF rendering via a dedicated GIF holder component.
- GIF placeholder: The GIF holder includes a configurable placeholder (a local drawable or static image) that is shown while GIFs are loading or if image loading fails (for example, when Firebase Storage access is denied). Update the placeholder resource or the GIF holder component if you want a different fallback UI.
- If Firebase Storage is inaccessible, images/GIFs will not show. Make sure Storage rules and your Firebase project configuration allow the app to read images.

## How you can help / next steps
- If you want me to, I can:
  - Update Gradle/plugin versions to a compatible set and create a PR.
  - Add instructions or scripts to automate the Gradle wrapper / plugin alignment.
  - Help troubleshoot the Firebase Storage permissions and test image loading.
  - Add or improve the default GIF placeholder asset and show how to configure it in the GIF holder component.
- Otherwise, fix the Gradle sync and Firebase Storage permissions locally, add your `google-services.json`, and the app should run.

## Contributing
This is a learning project — contributions and suggestions are welcome. If you spot issues or have improvements, open an issue or a PR.

## License
Use as you like. No strict license attached here — treat it as a personal/learning project.

---
I'm still learning, so the project is intentionally simple and meant for experimentation and incremental improvements.
## Demo

[![eShopping Demo](https://your-gif-link-here.gif)](https://your-live-demo-link-here)
> *adding gIF once done with dev*



```
